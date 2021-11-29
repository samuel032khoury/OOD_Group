package controller.transfer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.image.Image;
import model.image.RGBImage;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;

/**
 * A class for transferring PPM files to and from a local file system.
 */
public class PpmImageTransferer implements ImageTransferer {

  /**
   * Reads .ppm data from the supplied filename into a 2D array of pixels, casts it to an image of
   * RGB pixels.
   *
   * @param filePath the name of the file to read from
   * @return the image loaded from the supplied file path
   * @throws IllegalArgumentException if the file supplied is not a .ppm extension file
   * @throws IOException              if there occurred an error during file read (for instance, a
   *                                  file was not found, or it is not a valid .ppm file)
   */
  @Override
  public Image load(String filePath) throws IllegalArgumentException, IOException {
    if (!filePath.endsWith(".ppm")) {
      throw new IllegalArgumentException(
          "File must be of .ppm extension, given " + filePath + " instead");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Invalid file name, file not found");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    if (!sc.next().equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt(); // TODO: not sure how this works -- do we scale this down or what?

    List<List<Pixel>> pixelData = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        row.add(new RGBPixel(r, g, b));
      }
      pixelData.add(row);
    }

    /*
      Since PPM is an RGB-only file format, we can safely return an RGBImage here without worrying
      about possible extensibility.
     */
    return new RGBImage(pixelData);
  }

  /**
   * Writes the given image in PPM format.
   *
   * @param image    The image to write as PPM
   * @param filePath the path to the file to export to
   */
  @Override
  public void export(Image image, String filePath) throws IOException {
    StringBuilder builder = new StringBuilder("P3\n");
    List<List<Pixel>> data = image.getImageData();

    int height = data.size();
    int width = data.get(0).size();
    int maxValue = 255; // TODO: What is this?

    builder.append(width).append(" ").append(height).append("\n").append(maxValue);

    // Using this https://stackoverflow.com/questions/41992006/creating-a-ppm-image-to-be-written-to-a-file-java
    for (List<Pixel> row : data) {
      builder.append("\n");
      for (int i = 0; i < row.size(); i++) {
        Pixel p = row.get(i);
        builder.append(" ").append(p.getRed())
            .append("  ").append(p.getBlue())
            .append("  ").append(p.getGreen());
        builder.append(i == (row.size() - 1) ? " " : "   ");
      }
    }

    FileWriter writer = new FileWriter(filePath);
    writer.write(builder.toString());
    writer.close();
  }

  @Override
  public String getFileTypes() {
    return "- PPM";
  }
}
