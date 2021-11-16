package controller.controller;

import java.util.Set;

/**
 * To represent an image process controller which can receive the commands from users and perform
 * image process to the target image.
 */
public interface IImageProcessController {
  /**
   * To run the controller, input/error handling depends on specific implementation.
   */
  void run();
}
