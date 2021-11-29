package controller.arguments;

import java.util.List;

/**
 * Represents the `text` command line argument, which controls the view to use if present.
 */
public class TextArgument implements ImageProcessorArgument<Void> {
  @Override
  public ImageProcessorArgumentType getFlag() {
    return ImageProcessorArgumentType.TEXT;
  }

  @Override
  public Void convertArguments(List<String> additionalArguments) throws IllegalArgumentException {
    return null;
  }
}
