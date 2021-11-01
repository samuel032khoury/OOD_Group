package controller.utils;

import java.io.FileNotFoundException;

import model.imageFile.ImageFile;

public interface ILoader {
  ImageFile loadFile(String fileName) throws IllegalStateException;
}
