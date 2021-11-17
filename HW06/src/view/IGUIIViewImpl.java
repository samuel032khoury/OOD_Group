package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.controller.ImageProcessControllerGUI;
import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibState;

public class IGUIIViewImpl extends JFrame implements IGUIIView, ActionListener, ListSelectionListener {

  private ImageLibState imageLib;
  private ImageProcessControllerGUI controller;
  private String currImageName;
  private ReadOnlyImageFile currImageFile;

  private JPanel mainPanel;
  private JPanel colorButtonPanel;
  private JPanel visualButtonPanel;
  private JPanel ioButtonPanel;
  private JPanel selectionListPanel;
  private List<JButton> allButton;
  private Map<Integer, Integer>[] histogram;

  private DefaultListModel<String> dataForListOfImageNames;
  private JList<String> imageNamesJList;

  public IGUIIViewImpl(ImageLibState imageLib, Set<String> supportedCommandStringSet, ImageProcessControllerGUI controller) {
    super();
    setVisible(true);
    setTitle("Image Processing");
    setSize(1520, 760);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    this.imageLib = imageLib;
    this.controller = controller;
    this.currImageName = "";
    this.currImageFile = null;


    this.mainPanel = new JPanel();
    this.colorButtonPanel = new JPanel();
    colorButtonPanel.setBorder(BorderFactory.createTitledBorder("Color Operation"));
    this.visualButtonPanel = new JPanel();
    visualButtonPanel.setBorder(BorderFactory.createTitledBorder("Visual Operation"));
    this.ioButtonPanel = new JPanel();
    ioButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

    allButton = new ArrayList<>();

    createButtons(supportedCommandStringSet);
    configButtons(this.imageLib.getLibSize());


    this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    this.colorButtonPanel.setLayout(new BoxLayout(colorButtonPanel, BoxLayout.Y_AXIS));
    this.visualButtonPanel.setLayout(new BoxLayout(visualButtonPanel, BoxLayout.Y_AXIS));
    this.ioButtonPanel.setLayout(new BoxLayout(ioButtonPanel, BoxLayout.X_AXIS));
    this.ioButtonPanel.setAlignmentX(0);

    this.selectionListPanel = new JPanel();
    this.selectionListPanel.setBorder(BorderFactory.createTitledBorder("Library"));
    this.selectionListPanel.setLayout(new BoxLayout(this.selectionListPanel, BoxLayout.X_AXIS));
    this.selectionListPanel.setPreferredSize(new Dimension(230, 760));

    this.dataForListOfImageNames = new DefaultListModel<>();
    this.imageNamesJList = new JList<>(dataForListOfImageNames);
    this.imageNamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.imageNamesJList.addListSelectionListener(this);
    this.selectionListPanel.add(new JScrollPane(imageNamesJList));
    this.selectionListPanel.setAlignmentX(0);

    mainPanel.add(selectionListPanel, BorderLayout.WEST);
    mainPanel.add(colorButtonPanel);
    mainPanel.add(visualButtonPanel);
    mainPanel.add(ioButtonPanel);


    this.add(this.mainPanel, BorderLayout.EAST);
    this.requestFocus();
    this.setFocusable(true);
  }

  private void createButtons(Set<String> supportedCommandStringSet) {
    List<JButton> colorButtons = new ArrayList<>();
    List<JButton> componentButtons = new ArrayList<>();
    List<JButton> visualButtons = new ArrayList<>();
    List<JButton> ioButtons = new ArrayList<>();
    for (String command : supportedCommandStringSet) {
      if ("QUIT, size".contains(command)) {
        continue;
      }

      JButton b = new JButton(command);
      allButton.add(b);
      b.setFocusable(false);
      b.addActionListener(this);
      b.setActionCommand(command);
      if (command.equals("load")) {
        ioButtons.add(b);
      } else if (command.equals("save")) {
        ioButtons.add(b);
      } else if (command.contains("component")) {
        colorButtons.add(b);
      } else if (("blur, sharpen, sepia, greyscale").contains(command)) {
        componentButtons.add(b);
      } else {
        visualButtons.add(b);
      }
    }
    colorButtons.sort((JButton b1, JButton b2) -> b1.getText().compareTo(b2.getText()));
    componentButtons.sort((JButton b1, JButton b2) -> b1.getText().compareTo(b2.getText()));
    colorButtons.addAll(componentButtons);
    visualButtons.sort((JButton b1, JButton b2) -> b1.getText().compareTo(b2.getText()));
    ioButtons.sort((JButton b1, JButton b2) -> b1.getText().compareTo(b2.getText()));

    for (JButton cb : colorButtons) {
      this.colorButtonPanel.add(cb);
    }
    for (JButton vb : visualButtons) {
      this.visualButtonPanel.add(vb);
    }
    for (JButton iob : ioButtons) {
      this.ioButtonPanel.add(iob);
    }
  }

  private void configButtons(int libSize) {
    if (libSize > 0) {
      for (JButton b : this.allButton) {
        b.setEnabled(true);
      }
    } else {
      for (JButton b : this.allButton) {
        if (!b.getText().equals("load")) {
          b.setEnabled(false);
        }
      }
    }
  }

  @Override
  public void renderImage() {
  }

  /**
   * To render a message.
   *
   * @param message The message to render.
   * @throws IllegalStateException if unable to render a message.
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(null, message, "Operation Completed!", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * To render a error.
   *
   * @param message The error to render.
   * @throws IllegalStateException if unable to render a error.
   */
  @Override
  public void renderError(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(null, message, "An error occurred!", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
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
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
                  "bmp/jpg/ppm/png", "BMP", "JPG", "JPEG", "PNG", "PPM");
          fileExplorer.setFileFilter(filter);
          int approveStatus = fileExplorer.showOpenDialog(IGUIIViewImpl.this);

          if (approveStatus == JFileChooser.APPROVE_OPTION) {
            File f = fileExplorer.getSelectedFile();
            String source = f.getAbsolutePath();
            newImageName = this.getInput("Load image");
            controller.getArgsRun(action, source, newImageName);
            updateView(newImageName);
            JOptionPane.showMessageDialog(null, "Size" + imageLib.getLibSize());
          }
          break;
        case "save":
          break;
        case "brighten":
        case "darken":
          try {
            Integer adjustmentMagnitude = Integer.parseInt(this.getInput(
                    "Please enter the magnitude for the brightness adjustment:", title));
            newImageName = this.getInput(title);
            controller.getArgsRun(action,
                    String.valueOf(adjustmentMagnitude), currImageName, newImageName);
            updateView(newImageName);
          } catch (NumberFormatException exception) {
            this.renderError("Please input an integer for brightness adjustment!");
          }
          break;
        default:
          newImageName = this.getInput(title);
          controller.getArgsRun(action, currImageName, newImageName);
          updateView(newImageName);
          JOptionPane.showMessageDialog(null, "Size" + imageLib.getLibSize());
      }
    } catch (IllegalArgumentException ignored) {
    }
  }

  private void updateView(String newImageName) {
    configButtons(this.imageLib.getLibSize());
    this.currImageName = newImageName;
    this.currImageFile = imageLib.peek(this.currImageName);
    if (!dataForListOfImageNames.contains(newImageName)) {
      this.dataForListOfImageNames.addElement(newImageName);
    }
  }

  private String getInput(String prompt, String title) throws IllegalArgumentException {
    String valid = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
    if (valid == null) {
      throw new IllegalArgumentException("Operation is interrupted!");
    }
    if (valid.strip().equals("")) {
      this.renderError("Input cannot be empty!");
      throw new IllegalArgumentException("Input is empty!");
    }
    return valid.strip();
  }

  private String getInput(String title) {
    return this.getInput("Please enter the name for the new Image:", title);
  }

  /**
   * Called whenever the value of the selection changes.
   *
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
