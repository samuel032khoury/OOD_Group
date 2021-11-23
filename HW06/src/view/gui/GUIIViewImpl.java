package view.gui;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import javax.swing.event.ListSelectionListener;

import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibState;

public class GUIIViewImpl extends JFrame implements IGUIIView {

  private final ImageLibState imageLib;

  private final List<JButton> allButton;

  private ReadOnlyImageFile currImageFile;

  private final JLabel imageLabel;
  private final JPanel histogramGraphPanel;
  private final List<List<Integer>> histogramData;

  private final JList<String> imageNamesJList;
  private final DefaultListModel<String> dataForListOfImageNames;


  public GUIIViewImpl(ImageLibState imageLib, Set<String> supportedCommandStringSet,
                      ActionListener actionListener, ListSelectionListener listSelectionListener) {
    super();

    // JFrame configuration
    this.setTitle("Image Processing");
    this.setMinimumSize(new Dimension(1320, 760));
    this.setLayout(new GridLayout(1, 3));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    // Meta data variable assignment
    this.imageLib = imageLib;
    this.allButton = new ArrayList<>();
    this.currImageFile = null;

    //ImagePanel initialization & configuration
    this.imageLabel = new JLabel();
    this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JPanel imagePanel = createImagePanel();

    //ControlPanel initialization & configuration
    this.dataForListOfImageNames = new DefaultListModel<>();
    this.imageNamesJList = new JList<>(dataForListOfImageNames);
    this.imageNamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.imageNamesJList.addListSelectionListener(listSelectionListener);
    this.imageNamesJList.setFocusable(false);
    JPanel controlPanel = createControlPanel(this.createOperationPanel(supportedCommandStringSet, actionListener));

    // InfoPanel (with histogram graph) initialization & configuration
    this.histogramData = new ArrayList<>() {{
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
    }};
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 300));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.X_AXIS));
    histogramPanel.setFocusable(false);
    this.histogramGraphPanel = new HistogramGraphPanel(this.histogramData);
    histogramPanel.add(this.histogramGraphPanel);
    JPanel infoPanel = new JPanel();
    infoPanel.add(histogramPanel);

    // Collecting all sub-panels to the main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainPanel.add(infoPanel);
    mainPanel.add(imagePanel);
    mainPanel.add(controlPanel);

    this.add(mainPanel);
    this.setFocusable(true);
    this.pack();
  }

  private JPanel createControlPanel(JPanel operationPanel) {
    // to create a library panel
    JPanel libraryPanel = new JPanel();
    libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
    libraryPanel.setLayout(new BoxLayout(libraryPanel, BoxLayout.X_AXIS));
    libraryPanel.setPreferredSize(new Dimension(230, 230));
    libraryPanel.setMinimumSize(new Dimension(230, 230));
    libraryPanel.add(new JScrollPane(this.imageNamesJList));
    libraryPanel.setAlignmentY(0);


    // to create a scrollable operation panel
    JScrollPane operationScrollablePanel = new JScrollPane(operationPanel);
    updateButtonAvailability(this.imageLib.getLibSize());
    operationScrollablePanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    operationScrollablePanel.setMinimumSize(new Dimension(230, 0));

    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    operationScrollablePanel.setAlignmentY(0);
    controlPanel.add(libraryPanel);
    controlPanel.add(operationScrollablePanel);
    return controlPanel;
  }

  private JPanel createImagePanel() {
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
    imagePanel.setPreferredSize(new Dimension(800, 0));
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setFocusable(true);
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    imagePanel.add(imageScrollPane);
    return imagePanel;
  }


  private JPanel createOperationPanel(Set<String> supportedCommandStringSet, ActionListener actionListener) {
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
      b.addActionListener(actionListener);
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
  public void updateViewMeta(String newImageName) {
    updateButtonAvailability(this.imageLib.getLibSize());
    if (!dataForListOfImageNames.contains(newImageName)) {
      this.dataForListOfImageNames.addElement(newImageName);
    }
    int currItemIndex = this.dataForListOfImageNames.indexOf(newImageName);
    this.imageNamesJList.getSelectionModel().setSelectionInterval(currItemIndex, currItemIndex);
    this.currImageFile = this.imageLib.peek(newImageName);
  }

  @Override
  public int getSaveStatus(JFileChooser fileExplorer) {
    return fileExplorer.showSaveDialog(this);
  }

  @Override
  public int getLoadStatus(JFileChooser fileExplorer) {
    return fileExplorer.showOpenDialog(this);
  }

  @Override
  public String getCurrImage() {
    String currSelection = this.imageNamesJList.getSelectedValue();
    this.currImageFile = this.imageLib.peek(currSelection);
    return currSelection;
  }

  @Override
  public String dialogGetInput(String prompt, String title, String defaultName) {
    return JOptionPane.showInputDialog(this, prompt, title,
                JOptionPane.PLAIN_MESSAGE, null, null, defaultName).toString();
  }

  public void updatePreviewImage() {
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

  public void updateHistogramGraph() {
    this.updateHistogramList();
    this.histogramGraphPanel.repaint();
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
      this.histogramData.set(i, List.of(redList, greenList, blueList, intensityList).get(i));
    }
  }


  /**
   * To render a message.
   *
   * @param message The message to render.
   * @throws IllegalStateException if unable to render a message.
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this,
            "<html><body><p style='width: 400px;'>" + message + "</p></body></html>",
            "Operation Completed!", JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * To render a error.
   *
   * @param message The error to render.
   * @throws IllegalStateException if unable to render a error.
   */
  @Override
  public void renderError(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this,
            "<html><body><p style='width: 200px;'>" + message + "</p></body></html>",
            "An error occurred!", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showView(boolean show) {
    this.setVisible(show);
  }
}
