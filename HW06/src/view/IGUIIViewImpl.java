package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.AbstractButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.controller.ImageProcessControllerGUI;
import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibState;


public class IGUIIViewImpl extends JFrame implements IGUIIView, ActionListener, ListSelectionListener {

  private final ImageLibState imageLib;
  private final ImageProcessControllerGUI controller;
  private String currImageName;
  private ReadOnlyImageFile currImageFile;

  private final JPanel colorButtonPanel;
  private final JPanel visualButtonPanel;
  private final JPanel ioButtonPanel;
  private final List<JButton> allButton;
  private Map<Integer, Integer>[] histogram;

  private final DefaultListModel<String> dataForListOfImageNames;

  public IGUIIViewImpl(ImageLibState imageLib, Set<String> supportedCommandStringSet,
                       ImageProcessControllerGUI controller) {
    super();
    this.setTitle("Image Processing");
    this.setMinimumSize(new Dimension(1320, 760));
    this.setLayout(new GridLayout(1, 2));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    this.imageLib = imageLib;
    this.controller = controller;
    this.currImageName = "";
    this.currImageFile = null;

    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
    imagePanel.setPreferredSize(new Dimension(1000, 0));
    imagePanel.setLayout(new FlowLayout());
    imagePanel.setFocusable(true);

    JPanel libraryPanel = new JPanel();
    libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
    libraryPanel.setLayout(new BoxLayout(libraryPanel, BoxLayout.X_AXIS));
    libraryPanel.setPreferredSize(new Dimension(230, 0));
    this.dataForListOfImageNames = new DefaultListModel<>();
    JList<String> imageNamesJList = new JList<>(dataForListOfImageNames);
    imageNamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    imageNamesJList.addListSelectionListener(this);
    imageNamesJList.setFocusable(false);
    libraryPanel.add(new JScrollPane(imageNamesJList));
    libraryPanel.setAlignmentX(0);

    this.allButton = new ArrayList<>();
    this.colorButtonPanel = new JPanel();
    this.colorButtonPanel.setBorder(BorderFactory.createTitledBorder("Color Operation"));
    this.colorButtonPanel.setLayout(new BoxLayout(colorButtonPanel, BoxLayout.Y_AXIS));

    this.visualButtonPanel = new JPanel();
    this.visualButtonPanel.setBorder(BorderFactory.createTitledBorder("Visual Operation"));
    this.visualButtonPanel.setLayout(new BoxLayout(visualButtonPanel, BoxLayout.Y_AXIS));

    this.ioButtonPanel = new JPanel();
    this.ioButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
    this.ioButtonPanel.setLayout(new BoxLayout(ioButtonPanel, BoxLayout.X_AXIS));
    this.ioButtonPanel.setAlignmentX(0);

    this.createButtons(supportedCommandStringSet);
    this.configButtons(this.imageLib.getLibSize());

    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    operationPanel.add(libraryPanel);
    operationPanel.add(colorButtonPanel);
    operationPanel.add(visualButtonPanel);
    operationPanel.add(ioButtonPanel);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainPanel.add(imagePanel, BorderLayout.NORTH);
    mainPanel.add(operationPanel);


    this.add(mainPanel);
    this.setFocusable(true);
    this.setVisible(true);
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
      this.allButton.add(b);
      b.setFocusable(false);
      b.addActionListener(this);
      b.setActionCommand(command);

      if (command.equals("load") || command.equals("save")) {
        ioButtons.add(b);
      } else if (command.contains("component")) {
        componentButtons.add(b);
      } else if (("blur, sharpen, sepia, greyscale").contains(command)) {
        colorButtons.add(b);
      } else {
        visualButtons.add(b);
      }
    }
    ioButtons.sort(Comparator.comparing(AbstractButton::getText));
    componentButtons.sort(Comparator.comparing(AbstractButton::getText));
    colorButtons.sort(Comparator.comparing(AbstractButton::getText));
    visualButtons.sort(Comparator.comparing(AbstractButton::getText));
    colorButtons.addAll(componentButtons);

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
    boolean enableAllButton = libSize > 0;
    for (JButton b : this.allButton) {
      if (!b.getText().equals("load")) {
        b.setEnabled(enableAllButton);
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
    JOptionPane.showMessageDialog(null,
            "<html><body><p style='width: 400px;'>" + message + "</p></body></html>",
            "Operation Completed!",
            JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * To render a error.
   *
   * @param message The error to render.
   * @throws IllegalStateException if unable to render a error.
   */
  @Override
  public void renderError(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(null,
            "<html><body><p style='width: 200px;'>" + message + "</p></body></html>",
            "An error occurred!",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    requestFocus();
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
          int loadApproveStatus = fileExplorer.showOpenDialog(IGUIIViewImpl.this);

          if (loadApproveStatus == JFileChooser.APPROVE_OPTION) {
            File f = fileExplorer.getSelectedFile();
            String source = f.getAbsolutePath();
            newImageName = this.getInput("Load image");
            this.controller.getArgsRun(action, source, newImageName);
            updateView(newImageName);
          }
          break;
        case "save":
          final JFileChooser fileSaver = new JFileChooser(".");
          int saveApproveStatus = fileSaver.showSaveDialog(this);
          if (saveApproveStatus == JFileChooser.APPROVE_OPTION) {
            File f = fileSaver.getSelectedFile();
            String destination = f.getAbsolutePath();
            this.controller.getArgsRun(action, destination, currImageName);
          }
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
