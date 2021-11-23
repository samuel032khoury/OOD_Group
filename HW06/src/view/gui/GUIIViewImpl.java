package view.gui;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
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

import controller.controller.gui.ImageProcessControllerGUI;
import model.imagefile.ReadOnlyImageFile;
import view.gui.histogram.HistogramGraphPanel;
import view.gui.histogram.IHistogramSurveyor;
import view.gui.histogram.HistogramSurveyorImpl;

/**
 * To represent a graphic view for an image processing tool, which allows user to import/edit/save
 * images using clickable triggers. This view is also able to display preview images and histograms.
 */
public class GUIIViewImpl extends JFrame implements IGUIIView {

  private final List<JButton> allButton;
  private final ImageProcessControllerGUI controller;
  private ReadOnlyImageFile currImageFile;

  private final JLabel imageLabel;

  private final DefaultListModel<String> dataForListOfImageNames;
  private final JList<String> imageNamesJList;

  private final IHistogramSurveyor surveyor;
  private final JPanel histogramGraphPanel;

  /**
   * TODO.
   * @param controller TODO.
   * @param supportedCommandStringSet TODO.
   * @param actionListener TODO.
   * @param listSelectionListener TODO.
   */
  public GUIIViewImpl(ImageProcessControllerGUI controller, Set<String> supportedCommandStringSet,
                      ActionListener actionListener, ListSelectionListener listSelectionListener) {
    super();

    if (controller == null || supportedCommandStringSet == null || actionListener == null
            || listSelectionListener == null) {
      throw new IllegalArgumentException("Illegal arguments for constructor!");
    }

    // Meta data variable assignment
    this.controller = controller;
    this.allButton = new ArrayList<>();
    this.currImageFile = null;


    //ImagePanel initialization & configuration
    this.imageLabel = new JLabel();
    this.imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JPanel imagePanel = initImagePanel();


    //ControlPanel (SelectionList + Buttons) initialization & configuration
    this.dataForListOfImageNames = new DefaultListModel<>();
    this.imageNamesJList = new JList<>(dataForListOfImageNames);
    this.imageNamesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.imageNamesJList.addListSelectionListener(listSelectionListener);
    this.imageNamesJList.setFocusable(false);
    JPanel controlPanel = initControlPanel(
            this.initButtonOperationPanel(supportedCommandStringSet, actionListener));


    // InfoPanel (Histogram graph) initialization & configuration
    List<List<Integer>> histogramData = new ArrayList<>() {
      {
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
      }
    };
    this.histogramGraphPanel = new HistogramGraphPanel(histogramData);
    JPanel histogramPanel = initHistogramPanel();
    JPanel infoPanel = new JPanel();
    infoPanel.add(histogramPanel);
    this.surveyor = new HistogramSurveyorImpl(histogramData);


    // Collect all sub-panels to the main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainPanel.add(infoPanel);
    mainPanel.add(imagePanel);
    mainPanel.add(controlPanel);

    // JFrame configuration
    this.setTitle("Image Processing");
    this.setMinimumSize(new Dimension(1320, 760));
    this.setLayout(new GridLayout(1, 3));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.add(mainPanel);
    this.setFocusable(true);
    this.pack();
  }

  /**
   * To initialize a scrollable and resizable {@link JPanel} reserved for displaying preview
   * images.
   *
   * @return an initialized, scrollable and resizable {@link JPanel} reserved for displaying preview
   *         images.
   */
  private JPanel initImagePanel() {
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));
    imagePanel.setPreferredSize(new Dimension(800, 0));
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setFocusable(true);
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    imagePanel.add(imageScrollPane);
    return imagePanel;
  }

  /**
   * To initialize a {@link JPanel} containing all operation triggers (buttons). An operation is
   * defined and put in this panel only if it shows up in the provided {@code
   * supportedCommandStringSet}.
   *
   * @param supportedCommandStringSet the command set supported by the {@link
   *                                  ImageProcessControllerGUI} that interacts with this
   *                                  {@link IGUIIView}
   * @param actionListener            the {@link ActionListener} that hears from operation triggers
   * @return an initialized {@link JPanel} containing all operation triggers (buttons).
   */
  private JPanel initButtonOperationPanel(
          Set<String> supportedCommandStringSet, ActionListener actionListener) {
    // Lists for button classification
    List<JButton> colorButtons = new ArrayList<>();
    List<JButton> componentButtons = new ArrayList<>();
    List<JButton> visualButtons = new ArrayList<>();
    List<JButton> ioButtons = new ArrayList<>();

    // Button classification
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

  /**
   * To initialize a {@link JPanel} reserved for placing selection lists and scrollable panel
   * containing operation buttons.
   *
   * @param operationPanel the {@link JPanel} that contains all the operation triggers (buttons).
   * @return an initialized {@link JPanel} reserved for placing selection lists and scrollable panel
   *         that contains operation triggers (buttons).
   */
  private JPanel initControlPanel(JPanel operationPanel) {
    // to create a library panel
    JPanel libraryPanel = new JPanel();
    libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
    libraryPanel.setLayout(new BoxLayout(libraryPanel, BoxLayout.X_AXIS));
    libraryPanel.setPreferredSize(new Dimension(230, 230));
    libraryPanel.setMinimumSize(new Dimension(230, 230));
    libraryPanel.add(new JScrollPane(this.imageNamesJList));
    libraryPanel.setAlignmentY(0);

    // to create a scrollable (button) operation panel
    JScrollPane operationScrollablePanel = new JScrollPane(operationPanel);
    updateButtonAvailability(this.controller.libIsEmpty());
    operationScrollablePanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    operationScrollablePanel.setMinimumSize(new Dimension(230, 0));

    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    operationScrollablePanel.setAlignmentY(0);
    controlPanel.add(libraryPanel);
    controlPanel.add(operationScrollablePanel);
    return controlPanel;
  }

  /**
   * To initialize a size-fixed {@link JPanel} reserved for displaying histogram.
   *
   * @return an initialized size-fixed {@link JPanel} reserved for displaying histogram.
   */
  private JPanel initHistogramPanel() {
    JPanel histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(300, 300));
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.X_AXIS));
    histogramPanel.setFocusable(false);
    histogramPanel.add(this.histogramGraphPanel);
    return histogramPanel;
  }

  /**
   * Update buttons availability based on the size of the {@link model.library.ImageLibModel}. If
   * the {@code ImageLibModel} is empty, grey out all buttons except {@code load} button, otherwise
   * enable all buttons.
   */
  private void updateButtonAvailability(boolean libIsEmpty) {
    for (JButton b : this.allButton) {
      if (!b.getText().equals("load")) {
        b.setEnabled(!libIsEmpty);
      }
    }
  }

  /**
   * Based on the name of the new image view panel currently displays, {@code newImageName}, update
   * view's meta information that listed below.
   * <ul>
   * <li>the selection list contents based on all imported images
   * <li>the selection highlight shown up in the selection list
   * <li>the reference of currently working on {@link ReadOnlyImageFile}
   * </ul>
   *
   * @param newImageName the name of the image that the view panel is about to display
   */
  @Override
  public void updateViewMeta(String newImageName) {
    updateButtonAvailability(this.controller.libIsEmpty());
    if (!dataForListOfImageNames.contains(newImageName)) {
      this.dataForListOfImageNames.addElement(newImageName);
    }
    int currItemIndex = this.dataForListOfImageNames.indexOf(newImageName);
    this.imageNamesJList.getSelectionModel().setSelectionInterval(currItemIndex, currItemIndex);
    this.currImageFile = this.controller.requestImage(newImageName);
  }

  /**
   * To get the save file status after saving a file with {@link JFileChooser}.
   *
   * @param fileExplorer the delegated {@link JFileChooser}
   * @return the return state of the file chooser on pop-down:
   * <ul>
   * <li>JFileChooser.CANCEL_OPTION
   * <li>JFileChooser.APPROVE_OPTION
   * <li>JFileChooser.ERROR_OPTION if an error occurs or the
   *                  dialog is dismissed
   * </ul>
   */
  @Override
  public int getSaveStatus(JFileChooser fileExplorer) {
    return fileExplorer.showSaveDialog(this);
  }

  /**
   * To get the open file status after selecting a file with {@link JFileChooser}.
   * @param fileExplorer  the delegated JFileChooser
   * @return the return state of the file chooser on pop-down:
   * <ul>
   * <li>JFileChooser.CANCEL_OPTION
   * <li>JFileChooser.APPROVE_OPTION
   * <li>JFileChooser.ERROR_OPTION if an error occurs or the
   *                  dialog is dismissed
   * </ul>
   */
  @Override
  public int getLoadStatus(JFileChooser fileExplorer) {
    return fileExplorer.showOpenDialog(this);
  }

  /**
   * Get the name of the image currently being worked on.
   *
   * @return the name of the image currently being worked on
   */
  @Override
  public String getCurrImage() {
    String currSelection = this.imageNamesJList.getSelectedValue();
    this.currImageFile = this.controller.requestImage(currSelection);
    return currSelection;
  }

  /**
   *  Post a dialog in a {@link JPanel} form and gets user's inputs.
   *
   * @param prompt      the prompt for the pop-up dialog
   * @param title       the title of the pop-up dialog
   * @param defaultName the default value put in the input box on the pop-up dialog
   * @return an input as a String received from the pop-up dialog
   */
  @Override
  public String dialogGetInput(String prompt, String title, String defaultName) {
    return JOptionPane.showInputDialog(this, prompt, title,
            JOptionPane.PLAIN_MESSAGE, null, null, defaultName).toString();
  }

  /**
   * Update the preview image shown on the view.
   */
  @Override
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

  /**
   * Update the histogram graph shown on the view.
   */
  @Override
  public void updateHistogramGraph() {
    this.surveyor.updateHistogramData(this.currImageFile);
    this.histogramGraphPanel.repaint();
  }


  /**
   * To render a message for successful operation in a pop-up window.
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
   * To render an error in a pop-up window.
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

  /**
   * To switch the visibility of the View window.
   *
   * @param show true if to show, false if not.
   */
  @Override
  public void showView(boolean show) {
    this.setVisible(show);
  }
}
