package model;

import model.image.Image;

/**
 * Defines all the operations on the application state manager. An application state manager
 * implements the logic of storing and fetching images by alias from a storage cache. This cache can
 * be stored locally.
 */
public interface ApplicationStateManager {

  /**
   * Gets the image with the specified image name from the cached map of images in the state
   * manager.
   *
   * @param imageName the name of the image to look for
   * @throws IllegalArgumentException /if the image name is invalid/if the image name is not found
   */
  Image fetchImage(String imageName) throws IllegalArgumentException;

  /**
   * Adds the supplied image to the cached map of images currently stored in the state manager.
   *
   * @param imageName the name of the image
   * @param img       the image itself
   * @throws IllegalArgumentException if the image name is empty or the image supplied is null
   */
  void store(String imageName, Image img) throws IllegalArgumentException;
}
