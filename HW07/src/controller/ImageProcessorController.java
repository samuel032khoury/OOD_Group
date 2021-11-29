package controller;

import java.util.List;

/**
 * An interface which defines the methods of an image processor controller.
 */
public interface ImageProcessorController {

  /**
   * Delegates the given arguments to the proper command handler and adds the resultant image to the
   * list of the images in the controller storage.
   *
   * @param command The command split by spaces, the first of which is the command name.
   * @throws IllegalArgumentException If the given arguments do not match the expected format.
   */
  void handle(List<String> command) throws IllegalArgumentException;
}
