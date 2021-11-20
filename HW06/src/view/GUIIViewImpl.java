package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
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

public class GUIIViewImpl extends JFrame implements IGUIIView, ActionListener, ListSelectionListener {

  private final ImageLibState imageLib;
  private final ImageProcessControllerGUI controller;
  private String currImageName;
  private ReadOnlyImageFile currImageFile;
  private final List<List<Integer>> histogram;

  private final List<JButton> allButton;
  private final JPanel colorButtonPanel;
  private final JPanel visualButtonPanel;
  private final JPanel ioButtonPanel;
  private final JLabel imageLabel;

  private final JList<String> imageNamesJList;
  private final DefaultListModel<String> dataForListOfImageNames;


  public GUIIViewImpl(ImageLibState imageLib, Set<String> supportedCommandStringSet,
                      ImageProcessControllerGUI controller) {
    super();
    this.setTitle("Image Processing");
    this.setMinimumSize(new Dimension(1320, 760));
    this.setLayout(new GridLayout(1, 3));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    this.imageLib = imageLib;
    this.controller = controller;
    this.currImageName = "";
    this.currImageFile = null;

    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
    imagePanel.setPreferredSize(new Dimension(800, 0));
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setFocusable(true);

    this.imageLabel = new JLabel();
    this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    imagePanel.add(imageScrollPane);

    JPanel libraryPanel = new JPanel();
    libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
    libraryPanel.setLayout(new BoxLayout(libraryPanel, BoxLayout.X_AXIS));
    libraryPanel.setPreferredSize(new Dimension(230, 230));
    libraryPanel.setMinimumSize(new Dimension(230, 230));
    this.dataForListOfImageNames = new DefaultListModel<>();
    this.imageNamesJList = new JList<>(dataForListOfImageNames);
    this.imageNamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.imageNamesJList.addListSelectionListener(this);
    this.imageNamesJList.setFocusable(false);
    libraryPanel.add(new JScrollPane(this.imageNamesJList));

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
    operationPanel.add(colorButtonPanel);
    operationPanel.add(visualButtonPanel);
    operationPanel.add(ioButtonPanel);
    JScrollPane operationScrollablePanel = new JScrollPane(operationPanel);
    operationScrollablePanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    operationScrollablePanel.setMinimumSize(new Dimension(200, 0));
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    libraryPanel.setAlignmentY(0);
    operationScrollablePanel.setAlignmentY(0);
    controlPanel.add(libraryPanel);
    controlPanel.add(operationScrollablePanel);

    // Histogram Related
    List<Integer> rChannel = new ArrayList<>();
    List<Integer> gChannel = new ArrayList<>();
    List<Integer> bChannel = new ArrayList<>();
    List<Integer> iChannel = new ArrayList<>();
    this.init8BitChannelList(rChannel, gChannel, bChannel, iChannel);
    this.histogram = new ArrayList<>();
    histogram.addAll(List.of(rChannel, gChannel, bChannel, iChannel));

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 300));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.X_AXIS));
    histogramPanel.setFocusable(false);

    JPanel infoPanel = new JPanel();
    infoPanel.add(histogramPanel);
    infoPanel.setAlignmentX(0);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainPanel.add(infoPanel);
    mainPanel.add(imagePanel);
    mainPanel.add(controlPanel);

    this.add(mainPanel);
    this.setFocusable(true);
    this.setVisible(true);
    this.pack();
  }

  @SafeVarargs
  private void init8BitChannelList(List<Integer>... lists) {
    int maxColorVal = 256;
    for (List<Integer> list : lists) {
      for (int currVal = 0; currVal < maxColorVal; currVal++) {
        list.add(0);
      }
    }
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
    //TODO: DELETE THIS AFTER ALL IMPLEMENTATION
    JButton testButton = new JButton("Test");
    testButton.setActionCommand("test");
    testButton.setFocusable(false);
    testButton.addActionListener(this);
    this.ioButtonPanel.add(testButton);
    //
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
          fileExplorer.setDialogTitle("Load an image into the library...");
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
                  "bmp/jpg/ppm/png", "BMP", "JPG", "JPEG", "PNG", "PPM");
          fileExplorer.setFileFilter(filter);
          int loadApproveStatus = fileExplorer.showOpenDialog(GUIIViewImpl.this);

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
          fileSaver.setDialogTitle("Save image " + currImageName + "...");
          fileSaver.setSelectedFile(new File("untitled.png"));
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
        //TODO: DELETE THIS AFTER ALL IMPLEMENTATION
        case "test":
          System.out.println(currImageName);
          break;
        //
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
    if (!dataForListOfImageNames.contains(newImageName)) {
      this.dataForListOfImageNames.addElement(newImageName);
    }
    int currItemIndex = this.dataForListOfImageNames.indexOf(newImageName);
    this.imageNamesJList.getSelectionModel().setSelectionInterval(currItemIndex, currItemIndex);
    this.updateVisual();
  }

  private void updateVisual() {
  }

  private void updatePreview() {
    imageLabel.setIcon(new ImageIcon("Jellyfish.jpg"));
  }

  private void updateHistogram() {

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
    if (!e.getValueIsAdjusting()) {
      this.currImageName = imageNamesJList.getSelectedValue();
      this.currImageFile = imageLib.peek(this.currImageName);
      this.updateVisual();
    }
  }
}
