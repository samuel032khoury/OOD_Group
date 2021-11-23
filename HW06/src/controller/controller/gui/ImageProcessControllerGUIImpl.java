package controller.controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.function.Supplier;

import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.command.macro.ICommand;
import controller.controller.text.ImageProcessControllerImplV2;
import controller.utils.QuitExecution;

import model.library.ImageLibModel;
import view.gui.IGUIIView;
import view.gui.GUIIViewImpl;

/**
 * To represent a controller that interacts with a graphic user interface, {@link #view}, and a
 * {@link ImageLibModel}. This controller update the {@link #model} according to the user's action
 * on the {@link #view} and render back the updated graphics on it.
 */
public class ImageProcessControllerGUIImpl extends ImageProcessControllerImplV2 implements
        ImageProcessControllerGUI {

  private final ImageLibModel model;
  private final Queue<String> commandQueue;
  private final IGUIIView view;
  private String currImageName;
  protected ActionListener listener;

  /**
   * To construct a {@link ImageProcessControllerGUIImpl} that interacts with a {@link
   * ImageLibModel}, by the commands provided/updated from the {@link #commandQueue}, and visualize
   * the graphics on a {@link IGUIIView}.
   *
   * @param model The {@link ImageLibModel} that this controller interacts with.
   */
  public ImageProcessControllerGUIImpl(ImageLibModel model) {
    this.model = model;
    this.commandQueue = new ArrayDeque<>();
    this.listener = new ImageProcessActionListener();
    this.view = new GUIIViewImpl(model, super.cmdMap.keySet(),
            this.listener, new ImageLibrarySelectionListener());
  }

  /**
   * TODO.
   */
  private class ImageProcessActionListener implements ActionListener {

    /**
     * TODO.
     * @param e TODO
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      final String newImageName;
      String action = e.getActionCommand();
      String title = action + " the image " + currImageName;
      try {
        switch (action) {
          case "load":
            final JFileChooser fileExplorer = new JFileChooser(".");
            fileExplorer.setDialogTitle("Load an image into the library...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "bmp/jpg/ppm/png", "BMP", "JPG", "JPEG", "PNG", "PPM");
            fileExplorer.setFileFilter(filter);
            int loadApproveStatus = view.getLoadStatus(fileExplorer);
            if (loadApproveStatus == JFileChooser.APPROVE_OPTION) {
              File f = fileExplorer.getSelectedFile();
              String source = f.getAbsolutePath();
              newImageName = this.getInput("Load image", f.getName().split("\\.")[0]);
              runEvent(action, source, newImageName);
              view.updateViewMeta(newImageName);
            }
            break;
          case "save":
            final JFileChooser fileSaver = new JFileChooser(".");
            fileSaver.setDialogTitle("Save image " + currImageName + "...");
            fileSaver.setSelectedFile(new File("untitled.png"));
            int saveApproveStatus = view.getSaveStatus(fileSaver);
            if (saveApproveStatus == JFileChooser.APPROVE_OPTION) {
              File f = fileSaver.getSelectedFile();
              String destination = f.getAbsolutePath();
              runEvent(action, destination, currImageName);
            }
            break;
          case "brighten":
          case "darken":
            try {
              Integer adjustmentMagnitude = Integer.parseInt(this.getInput(
                      "Please enter the magnitude for the brightness adjustment:", title, ""));
              newImageName = this.getInput(title, currImageName + "-" + action
                      + adjustmentMagnitude);
              runEvent(action,
                      String.valueOf(adjustmentMagnitude), currImageName, newImageName);
              view.updateViewMeta(newImageName);
            } catch (NumberFormatException exception) {
              view.renderError("Please input an integer for brightness adjustment!");
            }
            break;
          default:
            newImageName = this.getInput(title, currImageName + "-" + action);
            runEvent(action, currImageName, newImageName);
            view.updateViewMeta(newImageName);
            break;
        }
      } catch (IllegalArgumentException ignored) {
      }
    }

    /**
     * TODO.
     * @param prompt   TODO
     * @param title   TODO
     * @param defaultName   TODO
     * @return   TODO
     * @throws IllegalArgumentException   TODO
     */
    private String getInput(String prompt, String title, String defaultName)
            throws IllegalArgumentException {
      try {
        String valid = view.dialogGetInput(prompt, title, defaultName);
        if (valid.strip().equals("")) {
          view.renderError("Input cannot be empty!");
          throw new IllegalArgumentException("Input is empty!");
        }
        return valid.strip();
      } catch (NullPointerException e) {
        throw new IllegalArgumentException("Operation is interrupted!");
      }
    }

    /**
     * TODO.
     * @param title   TODO
     * @param defaultName   TODO
     * @return   TODO
     */
    private String getInput(String title, String defaultName) {
      return this.getInput("Please enter the name for the new Image:", title, defaultName);
    }
  }

  /**
   * TODO.
   */
  private class ImageLibrarySelectionListener implements ListSelectionListener {
    /**
     * TODO.
     * @param e TODO
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (!e.getValueIsAdjusting()) {
        currImageName = view.getCurrImage();
        view.updatePreviewImage();
        view.updateHistogramGraph();
      }
    }
  }

  /**
   * To perform an operation, by the arguments provided by the {@link #commandQueue}. It updates the
   * {@link #model} and let view print error message whenever the command arguments
   * provided is not valid (unsupported behavior/ unmatched arguments for a supported command).
   *
   * <p>Throwing messages for successful operation is dispatched to concrete class that implements
   * {@link model.operation.IImageOperation} for specific feedback.
   *
   * <p>Updating {@link GUIIViewImpl}'s {@code JPanels} is not in the scope of this method's
   * functionality, as it's more reasonable for {@code JPanel} to refresh after a list-selection
   * event is triggered, not after a run is completed.
   *
   * @see ImageLibrarySelectionListener#valueChanged
   */
  @Override
  public void run() {
    this.view.showView(true);
    if (commandQueue.isEmpty()) {
      return;
    }
    try {
      Supplier<ICommand> sup = cmdMap.get(commandQueue.poll());
      ICommand cmd = sup.get();
      cmd.execute(this.model, commandQueue, this.view);
    } catch (IllegalStateException | QuitExecution e) {
      this.view.renderError(e.getMessage());
      commandQueue.clear();
    } catch (NullPointerException e) {
      this.view.renderError("No such an operation can be performed!");
      commandQueue.clear();
    }
  }

  /**
   * Run the controller with the provided operation arguments.
   *
   * @param commandArgs a sequence of arguments for running an operation
   */
  @Override
  public void runEvent(String... commandArgs) {
    Collections.addAll(this.commandQueue, commandArgs);
    this.run();
  }
}
