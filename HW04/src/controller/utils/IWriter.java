package controller.utils;

import model.imageFile.ImageFile;

public interface IWriter {
  void write(ImageFile img, String filename) throws IllegalStateException;
}
