package controller;

import model.ImageFile;

public interface IWriter {
  void write(ImageFile img, String filename);
}
