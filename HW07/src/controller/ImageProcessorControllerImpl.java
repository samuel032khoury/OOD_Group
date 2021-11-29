package controller;

import controller.handler.ImageProcessCommandHandler;
import controller.transfer.ImageTransferType;
import controller.transfer.ImageTransferer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.ApplicationStateManager;
import model.ApplicationStateManagerImpl;
import model.image.Image;

/**
 * Implements the controller which operates and performs the operations on images. The primary goal
 * of this controller is to maintain and moderate the current state of the application. This
 * controller processes the commands supplied by the user and delegates their operation to the
 * specified operation handlers.
 */
public class ImageProcessorControllerImpl implements ImageProcessorController {

  // the allowed command handlers
  private final List<ImageProcessCommandHandler> commandHandlers;
  // the map of image loaders to operate on specific types
  private final Map<ImageTransferType, ImageTransferer> imageLoaders;
  // the application state manager
  private final ApplicationStateManager applicationStateManager;

  /**
   * Creates a new controller with the specified command handlers and default application state
   * manager.
   *
   * @param commandHandlers a set of command handlers that this controller should handle
   * @param imageLoaders    a map of image loaders for specific file types
   */
  public ImageProcessorControllerImpl(List<ImageProcessCommandHandler> commandHandlers,
      Map<ImageTransferType, ImageTransferer> imageLoaders) {
    this(commandHandlers, imageLoaders, new ApplicationStateManagerImpl());
  }

  /**
   * Creates a new controller with the specified command handlers, image loaders, and an application
   * state manager.
   *
   * @param commandHandlers         a set of command handlers that this controller should handle
   * @param imageLoaders            a map of image loaders for specific file types
   * @param applicationStateManager the application state manager
   */
  public ImageProcessorControllerImpl(List<ImageProcessCommandHandler> commandHandlers,
      Map<ImageTransferType, ImageTransferer> imageLoaders,
      ApplicationStateManager applicationStateManager) {
    this.commandHandlers = commandHandlers;
    this.imageLoaders = imageLoaders;
    this.applicationStateManager = applicationStateManager;
  }

  @Override
  public void handle(List<String> command) throws IllegalArgumentException {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException(
          "Can not handle an image processing command with no arguments.");
    }

    String commandName = command.get(0);
    List<String> otherArgs = command.subList(1, command.size());

    for (ImageProcessCommandHandler handler : commandHandlers) {
      if (handler.getCommandName().equalsIgnoreCase(commandName)) {
        handler.process(otherArgs, this::fetchImage,
            this::cacheImage);
        return;
      }
    }

    throw new IllegalArgumentException(String.format("Can not handle command %s", commandName));
  }

  /**
   * Attempts to the fetch the image from the application state manager, and if none is in the state
   * manager, attempts to load it from the file system.
   *
   * @param imageName the image name/file path to retrieve/load from
   * @return the fetched image if successful
   * @throws IllegalArgumentException if the image with such name is not in the cache and could not
   *                                  be loaded from the filesystem
   */
  public Image fetchImage(String imageName) throws IllegalArgumentException {
    try {
      return applicationStateManager.fetchImage(imageName);
    } catch (IllegalArgumentException e) {
      // if application thrown, this image is not in cache, so we try to load it instead
      return loadFile(imageName);
    }
  }

  /**
   * Attempts to save the image, and delegates the storage to the application state manager. If
   * possible, the image will be saved locally and cached in the memory; if the storage is not
   * possible, the file will be cached under an alias.
   *
   * @param imageName the name of the image
   * @param img       the image itself
   * @throws IllegalArgumentException if the image supplied is null
   */
  public void cacheImage(String imageName, Image img) throws IllegalArgumentException {
    if (img == null) {
      throw new IllegalArgumentException("Image must not be null");
    }

    // this will automatically choose to save a file if a user choose a file name
    // instead of an image name for local storage, and vice versa
    storeFileIfPossible(imageName, img);
    applicationStateManager.store(imageName, img);
  }

  /**
   * Loads the image from the given file, either as .ppm or regular.
   *
   * @param filePath The file path to load the file from
   * @return An image loaded form the path
   * @throws IllegalArgumentException If there is no image transferrer for this type of file path,
   *                                  or if there is an issue loading the file
   */
  private Image loadFile(String filePath) throws IllegalArgumentException {
    ImageTransferType transferType = ImageTransferType.fromFilePath(filePath);
    ImageTransferer loader = Optional.ofNullable(imageLoaders.get(transferType))
        .orElseThrow(() ->
            new IllegalArgumentException(
                String.format("No image loader found for image type %s", transferType)
            )
        );

    try {
      return loader.load(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Issue loading file %s using %s transferrer",
              filePath,
              transferType)
      );
    }
  }

  /**
   * Exports the image to the given file path, either as .ppm or regular.
   *
   * @param filePath The file path to export the file to
   * @param img      The image to export
   * @throws IllegalArgumentException If there is no image transferrer for this type of file path,
   *                                  or if there is an issue exporting the file
   */
  private void storeFileIfPossible(String filePath, Image img) {
    ImageTransferType transferType;
    try {
      transferType = ImageTransferType.fromFilePath(filePath);
    } catch (IllegalArgumentException ignored) {
      return;
    }

    ImageTransferer transferrer = imageLoaders.get(transferType);
    if (transferrer != null) {
      try {
        transferrer.export(img, filePath);
      } catch (IOException e) {
        throw new IllegalArgumentException(
            String.format("Issue exporting file %s using %s transferer",
                filePath,
                transferType)
        );
      }
    }
  }
}
