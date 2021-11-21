package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
  private final List<List<Integer>> histogram;

  private final List<JButton> allButton;
  private final JLabel imageLabel;
  private final JPanel histogramGraph;

  private final JList<String> imageNamesJList;
  private final DefaultListModel<String> dataForListOfImageNames;

  private String currImageName;
  private ReadOnlyImageFile currImageFile;

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
    this.allButton = new ArrayList<>();

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

    JScrollPane operationScrollablePanel = new JScrollPane(createOperationPanel(supportedCommandStringSet));
    updateButtonAvailability(this.imageLib.getLibSize());
    operationScrollablePanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    operationScrollablePanel.setMinimumSize(new Dimension(230, 0));
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    libraryPanel.setAlignmentY(0);
    operationScrollablePanel.setAlignmentY(0);
    controlPanel.add(libraryPanel);
    controlPanel.add(operationScrollablePanel);

    // Histogram Related
    this.histogram = new ArrayList<>() {{
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
    }};
//    this.initHistogramList();

    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 300));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.X_AXIS));
    histogramPanel.setFocusable(false);

    this.histogramGraph = new JPanel();
    histogramPanel.add(histogramGraph);

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


  private JPanel createOperationPanel(Set<String> supportedCommandStringSet) {
    List<JButton> colorButtons = new ArrayList<>();
    List<JButton> componentButtons = new ArrayList<>();
    List<JButton> visualButtons = new ArrayList<>();
    List<JButton> ioButtons = new ArrayList<>();

    for (String command : supportedCommandStringSet) {
      if ("(QUIT)(size)".contains(command)) {
        continue;
      }

      JButton b = new JButton(command);
      this.allButton.add(b);
      b.setFocusable(false);
      b.addActionListener(this);
      b.setActionCommand(command);

      if (("(load)(save)").contains(command)) {
        ioButtons.add(b);
      } else if (command.contains("component")) {
        componentButtons.add(b);
      } else if (("(blur)(sharpen)(sepia)(greyscale)").contains(command)) {
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

    JPanel colorButtonPanel = new JPanel();
    colorButtonPanel.setBorder(BorderFactory.createTitledBorder("Color Operation"));
    colorButtonPanel.setLayout(new BoxLayout(colorButtonPanel, BoxLayout.Y_AXIS));

    JPanel visualButtonPanel = new JPanel();
    visualButtonPanel.setBorder(BorderFactory.createTitledBorder("Visual Operation"));
    visualButtonPanel.setLayout(new BoxLayout(visualButtonPanel, BoxLayout.Y_AXIS));

    JPanel ioButtonPanel = new JPanel();
    ioButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
    ioButtonPanel.setLayout(new BoxLayout(ioButtonPanel, BoxLayout.X_AXIS));
    ioButtonPanel.setAlignmentX(0);

    for (JButton cb : colorButtons) {
      colorButtonPanel.add(cb);
    }
    for (JButton vb : visualButtons) {
      visualButtonPanel.add(vb);
    }
    for (JButton iob : ioButtons) {
      ioButtonPanel.add(iob);
    }

    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.Y_AXIS));
    operationPanel.add(colorButtonPanel);
    operationPanel.add(visualButtonPanel);
    operationPanel.add(ioButtonPanel);

    return operationPanel;
  }

  private void updateButtonAvailability(int libSize) {
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


  private void updateView(String newImageName) {
    updateButtonAvailability(this.imageLib.getLibSize());
    if (!dataForListOfImageNames.contains(newImageName)) {
      this.dataForListOfImageNames.addElement(newImageName);
    }
    int currItemIndex = this.dataForListOfImageNames.indexOf(newImageName);
    this.imageNamesJList.getSelectionModel().setSelectionInterval(currItemIndex, currItemIndex);
  }

  private void updateVisual() {
    this.updatePreview();
    this.updateHistogramGraph();
  }

  private void updatePreview() {
    int currWidth = this.currImageFile.getWidth();
    int currHeight = this.currImageFile.getHeight();
    BufferedImage currPreview = new BufferedImage(currWidth,
            currHeight, BufferedImage.TYPE_4BYTE_ABGR);
    for (int i = 0; i < currWidth; i++) {
      for (int j = 0; j < currHeight; j++) {
        currPreview.setRGB(i, j, currImageFile.getColorAt(j, i).getRGB());
      }
    }
    this.imageLabel.setIcon(new ImageIcon(currPreview));
  }

  private void updateHistogramGraph() {
    this.updateHistogramList();
    this.drawHistogram(this.histogramGraph, this.histogram);
  }

   // update the histogram (reference) given using the image file given.
  private void updateHistogramList() {
    int currWidth = this.currImageFile.getWidth();
    int currHeight = this.currImageFile.getHeight();

    List<Integer> redList = new ArrayList<>();
    List<Integer> greenList = new ArrayList<>();
    List<Integer> blueList = new ArrayList<>();
    List<Integer> intensityList = new ArrayList<>();

    for (List<Integer> list : List.of(redList, greenList, blueList, intensityList)) {
      for (int currVal = 0; currVal < 256; currVal++) {
        list.add(0);
      }
    }

    for (int i = 0; i < currWidth; i++) {
      for (int j = 0; j < currHeight; j++) {
        Color color = this.currImageFile.getColorAt(j, i);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int intensity = (red + green + blue) / 3;

        redList.set(red, redList.get(red) + 1);
        greenList.set(green, greenList.get(green) + 1);
        blueList.set(blue, blueList.get(blue) + 1);
        intensityList.set(intensity, intensityList.get(intensity) + 1);
      }
    }

    for (int i = 0; i < 4; i++) {
      this.histogram.set(i, List.of(redList, greenList, blueList, intensityList).get(i));
    }
  }

  private int getMaxPixel(List<List<Integer>> histogram) {
    int maxR = 0;
    int maxG = 0;
    int maxB = 0;
    int maxI = 0;
    List<Integer> redList = histogram.get(0);
    List<Integer> greenList = histogram.get(1);
    List<Integer> blueList = histogram.get(2);
    List<Integer> intensityList = histogram.get(3);

    for (int i = 0; i < histogram.get(0).size(); i++) {
      if (redList.get(i) > maxR) {
        maxR = redList.get(i);
      }

      if (greenList.get(i) > maxG) {
        maxG = greenList.get(i);
      }

      if (blueList.get(i) > maxB) {
        maxB = blueList.get(i);
      }

      if (intensityList.get(i) > maxI) {
        maxI = intensityList.get(i);
      }
    }

    return Math.max(Math.max(maxB, maxG), Math.max(maxI, maxR));
  }

  private void drawHistogram(JPanel panel, List<List<Integer>> histogram) {
    int width = panel.getWidth();
    int height = panel.getHeight();
    int maxPixels = this.getMaxPixel(histogram);
    Graphics g = panel.getGraphics();
    g.clearRect(0, 0, width, height);
    int verticalOffset = 10;
    int horizontalOffset = 10;
    double xSeparation = (width - 2.0 * horizontalOffset) / (histogram.get(0).size() - 1.0);
    double ySeparation = (height - 2.0 * verticalOffset) / maxPixels;

    int[] pX = new int[256];
    for (int i = 0; i < 256; i++) {
      pX[i] = (int) (horizontalOffset + i * xSeparation);
    }
    int[] pYR = new int[256];
    int[] pYG = new int[256];
    int[] pYB = new int[256];
    int[] pYI = new int[256];
    for (int i = 0; i < 256; i++) {
      int numOfPixelR = histogram.get(0).get(i);
      int numOfPixelG = histogram.get(1).get(i);
      int numOfPixelB = histogram.get(2).get(i);
      int numOfPixelI = histogram.get(3).get(i);

      pYR[i] = (int) (verticalOffset + (maxPixels - numOfPixelR) * ySeparation);
      pYG[i] = (int) (verticalOffset + (maxPixels - numOfPixelG) * ySeparation);
      pYB[i] = (int) (verticalOffset + (maxPixels - numOfPixelB) * ySeparation);
      pYI[i] = (int) (verticalOffset + (maxPixels - numOfPixelI) * ySeparation);
    }
    g.setColor(new Color(255, 0, 0, 125));
    g.drawPolyline(pX, pYR, pX.length);
    g.setColor(new Color(0, 255, 0, 125));
    g.drawPolyline(pX, pYG, pX.length);
    g.setColor(new Color(0, 0, 255, 125));
    g.drawPolyline(pX, pYB, pX.length);
    g.setColor(new Color(0, 0, 0, 200));
    g.drawPolyline(pX, pYI, pX.length);
  }


  private String getInput(String prompt, String title, String defaultName)
          throws IllegalArgumentException {
    String valid = JOptionPane.showInputDialog(null, prompt, title,
            JOptionPane.PLAIN_MESSAGE, null, null, defaultName).toString();
    if (valid == null) {
      throw new IllegalArgumentException("Operation is interrupted!");
    }
    if (valid.strip().equals("")) {
      this.renderError("Input cannot be empty!");
      throw new IllegalArgumentException("Input is empty!");
    }
    return valid.strip();
  }

  private String getInput(String title, String defaultName) {
    return this.getInput("Please enter the name for the new Image:", title, defaultName);
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
            newImageName = this.getInput("Load image", f.getName().split("\\.")[0]);
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
                    "Please enter the magnitude for the brightness adjustment:", title, ""));
            newImageName = this.getInput(title, this.currImageName + "-" + action + adjustmentMagnitude);
            controller.getArgsRun(action,
                    String.valueOf(adjustmentMagnitude), this.currImageName, newImageName);
            updateView(newImageName);
          } catch (NumberFormatException exception) {
            this.renderError("Please input an integer for brightness adjustment!");
          }
          break;
        default:
          newImageName = this.getInput(title, this.currImageName + "-" + action);
          controller.getArgsRun(action, currImageName, newImageName);
          updateView(newImageName);
          break;
      }
    } catch (IllegalArgumentException ignored) {
    }
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
