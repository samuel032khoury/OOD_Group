package view;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import controller.handler.ImageProcessCommandHandler;
import controller.transfer.ImageTransferType;
import controller.transfer.ImageTransferer;
import model.ApplicationStateManager;
import model.ApplicationStateManagerImpl;
import model.image.Image;
import model.image.pixel.Pixel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is a GUI for handling commands to process images. It accepts a list of
 * {@link ImageProcessCommandHandler} objects, which are used to delegate properly formatted
 * commands to their relevant handler. To extend the interface, simply add more command objects to
 * its constructor.
 */
public class ImageProcessorGui
    extends JFrame implements ImageProcessorView, ListSelectionListener, ActionListener {
  // commands that go over images
  private final List<ImageProcessCommandHandler> imageCommandHandlers;
  // image controller
  private final ImageProcessorController controller;
  // application state manager
  private final ApplicationStateManager imageManager;

  // Some static variables to be used throughout the GUI
  private static final String LOAD_ACTION = "load";
  private static final String SAVE_ACTION = "save";
  private static final String CURRENT_IMAGE_NAME = "current";
  private static final String NOT_FOUND_URL = "https://www.flexx.co/assets/camaleon_cms/"
          + "image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png";
  // JFrame components
  private JList<String> handlersDropdown;
  private JScrollPane currentImg;
  private JTextField extraArgsBox;

  /**
   * Creates an image processor GUI from the given list of commands.
   */
  public ImageProcessorGui(List<ImageProcessCommandHandler> commandHandlers,
                           Map<ImageTransferType, ImageTransferer> imageLoaders) {
    super();

    // Command handlers do not include
    // currently supported commands
    this.imageCommandHandlers = commandHandlers
        .stream()
        .filter(h -> !List.of("load", "save").contains(h.getCommandName()))
        .collect(Collectors.toList());

    this.imageManager = new ApplicationStateManagerImpl();
    this.controller = new ImageProcessorControllerImpl(commandHandlers, imageLoaders, imageManager);
  }

  @Override
  public void run() {
    try {
      initGui();
    } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }

    // Display GUI
    pack();
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case LOAD_ACTION:
        loadFile();
        break;
      case SAVE_ACTION:
        saveFile();
        break;
      default:
        // TODO: Handle unsupported action
        break;
    }
    reloadImage();
    pack();
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    int newIndex = handlersDropdown.getSelectedIndex();

    ImageProcessCommandHandler handler = imageCommandHandlers.get(newIndex);
    if (!e.getValueIsAdjusting()) {
      handleCommand(handler.getCommandName());
      reloadImage();
      pack();
    }
  }

  // Delegates based on command name to controller with current-image
  private void handleCommand(String commandName) {
    List<String> allArgs = new ArrayList<>();
    allArgs.add(commandName);
    allArgs.add(CURRENT_IMAGE_NAME);
    allArgs.add(CURRENT_IMAGE_NAME);
    try {
      controller.handle(allArgs);
    } catch (IllegalArgumentException e) {
      List<String> extraArgs = Arrays.asList(extraArgsBox.getText().split(","));
      allArgs.addAll(extraArgs);
      try {
        controller.handle(allArgs);
      } catch (IllegalArgumentException e2) {
        // TODO: Raise modal
      }
    }
  }

  // Opens the file loading screen, loads the file name
  // Delegates loading the actual file to the controller
  // Sets the current file to the current file name
  private void loadFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG & GIF Images", "jpg", "gif");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(ImageProcessorGui.this);
    switch (retvalue) {
      case JFileChooser.APPROVE_OPTION:
        File f = fchooser.getSelectedFile();
        // Opens the image with the controller using the current file name as its alias
        controller.handle(List.of(LOAD_ACTION, f.getAbsolutePath(), CURRENT_IMAGE_NAME));
        break;
      case JFileChooser.CANCEL_OPTION:
        break;
      case JFileChooser.ERROR_OPTION:
        // TODO: Surface error modal
        throw new IllegalStateException("Failed to load file");
      default:
        // TODO: Handle default
        break;
    }
  }

  private void saveFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(ImageProcessorGui.this);

    switch (retvalue) {
      case JFileChooser.APPROVE_OPTION:
        File f = fchooser.getSelectedFile();
        // Opens the image with the controller using the current file name as its alias
        controller.handle(List.of(SAVE_ACTION, f.getAbsolutePath(), CURRENT_IMAGE_NAME));
        break;
      case JFileChooser.CANCEL_OPTION:
        break;
      case JFileChooser.ERROR_OPTION:
        // TODO: Surface error modal
        throw new IllegalStateException("Failed to save file");
      default:
        // TODO: Handle default
        break;
    }
  }

  // Initializes the GUI based on the available command handlers and image loaders
  private void initGui() throws ReflectiveOperationException, UnsupportedLookAndFeelException {
    setTitle("Alex & Thomas' Image Processor");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout()); // temporary, should be group layout later

    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    // Add drop down for command handlers
    addHandlersDropdowns();

    // Add load and save
    addLoadAndSaveSection();

    // Add an image that currently displays the image in current-image
    reloadImage();
  }

  private void addLoadAndSaveSection() {
    // Dialogue boxes
    JPanel ioPanel = new JPanel();
    ioPanel.setBorder(BorderFactory.createTitledBorder("Load and Save"));
    ioPanel.setLayout(new BoxLayout(ioPanel, BoxLayout.PAGE_AXIS));
    add(ioPanel);

    //file open
    JPanel openPanel = new JPanel();
    openPanel.setLayout(new FlowLayout());
    ioPanel.add(openPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand(LOAD_ACTION);
    fileOpenButton.addActionListener(this);
    openPanel.add(fileOpenButton);
    JLabel fileOpenDisplay = new JLabel("File path will appear here");
    openPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    ioPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand(SAVE_ACTION);
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    JLabel fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
  }

  private void addHandlersDropdowns() {
    DefaultListModel<String> commandOptions = new DefaultListModel<>();

    for (ImageProcessCommandHandler handler : imageCommandHandlers) {
      commandOptions.addElement(handler.getCommandName());
    }

    handlersDropdown = new JList<>(commandOptions);
    handlersDropdown.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    handlersDropdown.addListSelectionListener(this);
    add(handlersDropdown);

    // Add input box for extra argument
    extraArgsBox = new JTextField("Add extra cmd args here");
    add(extraArgsBox);
  }

  private void reloadImage() {
    Optional.ofNullable(currentImg).ifPresent(this::remove);

    ImageIcon currentImage = safelyFetchImage(CURRENT_IMAGE_NAME)
        .map(ImageProcessorGui::convertToAwtImage)
        .orElseGet(ImageProcessorGui::getBlankImage);

    currentImg = new JScrollPane(new JLabel(currentImage));

    JScrollPane scrollPane = currentImg;
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    add(scrollPane);
  }

  private Optional<Image> safelyFetchImage(String name) {
    try {
      return Optional.ofNullable(imageManager.fetchImage(name));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  // Converts our image to an AWT image
  private static ImageIcon convertToAwtImage(Image img) {
    BufferedImage bufferedImg = new BufferedImage(
        img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB
    );
    WritableRaster raster = bufferedImg.getRaster();

    List<List<Pixel>> pixels = img.getImageData();
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        Pixel p = pixels.get(i).get(j);
        int[] rgb = {p.getRed(), p.getGreen(), p.getBlue()};
        raster.setPixel(j, i, rgb);
      }
    }

    return new ImageIcon(bufferedImg);
  }

  // Retrieves a blank image icon
  private static ImageIcon getBlankImage() {
    try {
      return new ImageIcon(new URL(NOT_FOUND_URL));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
