package controller.transfer;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This enum defines the types available for image transfers, as well as supplies a few utility
 * methods that facilitate loading an image from the specified file path. Currently, supports PPM
 * and all common file types included in  {@link javax.imageio.ImageIO#getReaderFormatNames()}.
 */
public enum ImageTransferType {
  PPM(filePath -> filePath.endsWith(".ppm")),
  COMMON(filePath -> filePath.contains("."));

  private final Predicate<String> matcher; // defines the pattern for this file extension

  /**
   * Creates a new instance of an image transfer type.
   *
   * @param matcher the matcher predicate to define this file type
   */
  ImageTransferType(Predicate<String> matcher) {
    this.matcher = matcher;
  }

  /**
   * Determines if this file type matches the supplied file path.
   *
   * @param filePath the path to the file to run against the predicate matcher
   * @return true if the filePath matches this file type, false otherwise
   */
  public boolean matches(String filePath) {
    return matcher.test(filePath);
  }

  /**
   * Gets the appropriate ImageTransferType image loader for the supplied file path.
   *
   * @param filePath the path to a file that we would like to load
   * @return the appropriate file loader for this file path
   * @throws IllegalArgumentException if there is no loader found for this file type
   */
  public static ImageTransferType fromFilePath(String filePath) throws IllegalArgumentException {
    return Stream.of(ImageTransferType.values())
        .filter(type -> type.matches(filePath))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(
                String.format("No image loader found for file path %s", filePath))
        );
  }
}
