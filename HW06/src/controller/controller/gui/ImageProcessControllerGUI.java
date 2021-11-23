package controller.controller.gui;

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
}
