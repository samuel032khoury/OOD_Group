package model;

import java.util.LinkedHashMap;
import java.util.Map;
import model.image.Image;

/**
 * Stores and operates on the current state of the application. In the current version of the
 * application, the state managers operates on multiple images.
 */
public class ApplicationStateManagerImpl implements ApplicationStateManager {

  // stores the images currently in operation (acts as a "cache" storage for this application)
  private final Map<String, Image> images;

  /**
   * Creates a new application state manager.
   */
  public ApplicationStateManagerImpl() {
    this.images = new LinkedHashMap<>();
  }

  @Override
  public Image fetchImage(String imageName) throws IllegalArgumentException {
    if (!images.containsKey(imageName)) {
      throw new IllegalArgumentException(
          "The image with the supplied key does not exist in the application state.");
    }

    return images.get(imageName).copy();
  }

  @Override
  public void store(String imageName, Image img) throws IllegalArgumentException {
    if (imageName.isEmpty() || img == null) {
      throw new IllegalArgumentException("Image name and image itself must not be empty");
    }

    images.put(imageName, img);
  }
}
