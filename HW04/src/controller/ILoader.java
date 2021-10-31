package controller;

import java.io.FileNotFoundException;

import model.ImageFile;

public interface ILoader {
  ImageFile load(String filename) throws FileNotFoundException;
}
