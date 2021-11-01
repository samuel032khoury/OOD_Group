package controller.utils;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.imageFile.ImageFile;
import model.imageFile.ImageFileNoAlpha;

public class PPMLoader implements ILoader {
  public ImageFile loadFile(String pathName) throws IllegalStateException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(pathName));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Unable to find PPM file named \"" + pathName + "\"! Please " +
              "check the name or the path of the file is accurate and try again!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    try {
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      Color[][] image = new Color[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          Color color = new Color(r, g, b);
          image[i][j] = color;
        }
      }
      return new ImageFileNoAlpha(image, maxValue);
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The image cannot be imported because it's broken!");
    }
  }
}
