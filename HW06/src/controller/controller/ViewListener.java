package controller.controller;

import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;

/**
 * To represent an image process controller specifically for GUI which can responds user's actions.
 */
public interface ViewListener {
  void runEvent(String... commandArgs);
  ReadOnlyImageFile requestPictureEvent(String pictureName);
}
