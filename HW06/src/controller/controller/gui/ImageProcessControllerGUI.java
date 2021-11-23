package controller.controller.gui;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent an image process controller specifically for GUI which can responds user's actions
 * on the graphical interface.
 */
public interface ImageProcessControllerGUI {

  /**
   * Run the controller with the provided operation arguments.
   *
   * @param commandArgs a sequence of arguments for running an operation
   */
  void runEvent(String... commandArgs);

  /**
   * To get a {@link ReadOnlyImageFile} that linked with an identifiable image name.
   *
   * @param imageName the name of the requested {@link ReadOnlyImageFile} stored in the model
   * @return a {@link ReadOnlyImageFile} that is identifiable by the provided {@code imageName} in
   *         the model
   */
  ReadOnlyImageFile requestImage(String imageName);

  /**
   * Determine if the model interacts with the controller is brand new (has no {@link
   * model.imagefile.ImageFile} entry).
   *
   * @return true if the model is empty
   */
  boolean libIsEmpty();
}
