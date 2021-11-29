package controller.arguments;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

/**
 * Implements the `outputRedirect` command line argument.
 */
public class OutputRedirectArgument implements ImageProcessorArgument<Appendable> {

  @Override
  public ImageProcessorArgumentType getFlag() {
    return ImageProcessorArgumentType.REDIRECT_OUTPUT;
  }

  @Override
  public Appendable convertArguments(List<String> additionalArguments) {
    String filePath = additionalArguments.get(0);
    try {
      return new PrintStream(filePath);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(
          String.format("Failed to write file for %s: %s not found", getFlag(), filePath)
      );
    }
  }
}
