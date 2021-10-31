package controller.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.imageFile.ImageFile;

public class PPMWriter implements IWriter {

  @Override
  public void write(ImageFile img, String filename) {
    BufferedWriter myWriter = null;
    StringBuilder out = new StringBuilder();

    int height = img.getHeight();
    int width = img.getWidth();

    try {
      myWriter = new BufferedWriter(new FileWriter(filename));
      myWriter.write("P3\n");
      myWriter.write("# ppm - RGB\n");
      myWriter.write(String.format("%d %d\n", width, height));
      myWriter.write("PlaceHolderForMax\n");

    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }

    int maxColor = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color color = img.getColorAt(i, j);
        try {
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();
          assert myWriter != null;
          myWriter.write(String.valueOf(red));
          myWriter.write(" ");
          myWriter.write(String.valueOf(green));
          myWriter.write(" ");
          myWriter.write(String.valueOf(blue));
          myWriter.write(" ");
          maxColor = Math.max(Math.max(maxColor, red), Math.max(green, blue));
        } catch (IOException e) {
          throw new RuntimeException("Can't write");
        }
      }
      try {
        myWriter.write("\n");
      } catch (IOException e) {
        throw new RuntimeException("Can't write");
      }

    }

    try {
      assert myWriter != null;
      myWriter.close();
    } catch (IOException e) {
      throw new RuntimeException("Can't write");
    }


    String tmpFileName = "tmp.ppm";

    BufferedReader br = null;
    BufferedWriter bw = null;
    try {
      br = new BufferedReader(new FileReader(filename));
      bw = new BufferedWriter(new FileWriter(tmpFileName));
      String line;
      while ((line = br.readLine()) != null) {
        if (line.contains("PlaceHolderForMax"))
          line = line.replace("PlaceHolderForMax", "" + maxColor);
        bw.write(line + "\n");
      }
    } catch (Exception e) {

    } finally {
      try {
        if (br != null)
          br.close();
      } catch (IOException e) {
        //
      }
      try {
        if (bw != null)
          bw.close();
      } catch (IOException e) {
        //
      }
    }
  }
}
