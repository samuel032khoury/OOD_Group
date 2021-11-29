package controller.transfer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.imageio.ImageIO;
import model.image.Image;
import model.image.RGBImage;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;

/**
 * A class for transferring common file types to and from a local file system.
 */
public class CommonImageTransferer implements ImageTransferer {

  @Override
  public Image load(String filePath) throws IOException {
    BufferedImage image = ImageIO.read(new File(filePath));
    List<List<Pixel>> data = new ArrayList<>();

    for (int i = 0; i < image.getHeight(); i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < image.getWidth(); j++) {
        // These i and j are flipped in the Color class
        Color color = new Color(image.getRGB(j, i));
        row.add(new RGBPixel(color.getRed(), color.getGreen(), color.getBlue()));
      }
      data.add(row);
    }

    return new RGBImage(data);
  }

  @Override
  public void export(Image img, String filePath) throws IOException {
    File output = new File(filePath);

    List<List<Pixel>> data = img.getImageData();
    int height = data.size();
    int width = data.get(0).size();

    BufferedImage outputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel p = data.get(i).get(j);
        Color c = new Color(p.getRed(), p.getGreen(), p.getBlue());
        outputImg.setRGB(j, i, c.getRGB());
      }
    }

    String formatName = parseFormatName(filePath);
    ImageIO.write(outputImg, formatName, output);
  }

  @Override
  public String getFileTypes() {
    StringJoiner joiner = new StringJoiner("\n- ", "- ", "");
    getSupportedFormatNames()
        .stream()
        .map(String::toUpperCase)
        .distinct()
        .sorted()
        .forEach(joiner::add);
    return joiner.toString();
  }

  // Visible for testing
  static String parseFormatName(String filePath) throws IllegalArgumentException {
    List<String> supportedFormats = getSupportedFormatNames();
    return supportedFormats
        .stream()
        .filter(filePath::endsWith)
        .findFirst()
        .orElseThrow(
            () -> new IllegalArgumentException(String.format(
                "Failed to parse format name from file path %s, available formats: %s",
                filePath,
                supportedFormats)
            )
        );
  }

  private static List<String> getSupportedFormatNames() {
    return new ArrayList<>(Arrays.asList(ImageIO.getReaderFormatNames()));
  }
}
