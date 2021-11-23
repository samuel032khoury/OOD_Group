package controller.controller.gui;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent an image process controller specifically for GUI which can responds user's actions.
 */
public interface ImageProcessControllerGUI {

  void runEvent(String... commandArgs);

  void updateCurrImageName(String currImageName);
}
