package controller.utils;

import model.imageFile.ImageFile;
import model.imageFile.ReadOnlyImageFile;

public interface IWriter {
  void write(ReadOnlyImageFile img, String fileName) throws IllegalStateException;
}