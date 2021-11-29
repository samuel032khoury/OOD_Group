package controller.arguments;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Implements the `file` command line argument.
 */
public class ScriptFileArgument implements ImageProcessorArgument<Readable> {

  @Override
  public ImageProcessorArgumentType getFlag() {
    return ImageProcessorArgumentType.SCRIPT_FILE;
  }

  @Override
  public Readable convertArguments(List<String> additionalArguments)
      throws IllegalArgumentException {
    String filePath = additionalArguments.get(0);

    try {
      return new BufferedReader(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(
          String.format("Failed to read file for %s: %s not found", getFlag(), filePath)
      );
    }
  }
}
