package controller;

import java.io.FileNotFoundException;

import model.imageFile.ImageFile;

public interface ILoader {
  ImageFile load(String filename) throws FileNotFoundException;
}
