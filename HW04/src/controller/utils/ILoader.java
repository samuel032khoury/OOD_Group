package controller.utils;

import model.imageFile.ImageFile;

public interface ILoader {
  ImageFile loadFile(String fileName) throws IllegalStateException;
}
