package controller.arguments;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum used to identify supported command line arguments for this processor.
 */
public enum ImageProcessorArgumentType {
  REDIRECT_INPUT("redirectInput", 1),
  REDIRECT_OUTPUT("redirectOutput", 1),
  TEXT("text", 0),
  SCRIPT_FILE("file", 1);

  private final String flagName;
  private final int expectedArguments;

  /**
   * Creates an argument type for the given flag name and the number of expected arguments.
   *
   * @param flagName          The flag name for this enum
   * @param expectedArguments The number of expected arguments
   */
  ImageProcessorArgumentType(String flagName, int expectedArguments) {
    this.flagName = flagName;
    this.expectedArguments = expectedArguments;
  }

  @Override
  public String toString() {
    return flagName;
  }

  /**
   * Getter for the flag name.
   *
   * @return The flag name
   */
  public String getFlagName() {
    return flagName;
  }

  /**
   * Getter for the number of expected arguments.
   *
   * @return The expected arguments
   */
  public int getExpectedArguments() {
    return expectedArguments;
  }

  /**
   * Return an argument type matching the given flag, prefixed by ``, `-`, or `--`.
   *
   * @param currentFlag The flag as a string
   * @return The matching argument type, or empty if it doesn't match any.
   */
  public static Optional<ImageProcessorArgumentType> fromArg(String currentFlag) {
    return Arrays
        .stream(ImageProcessorArgumentType.values())
        .filter(type -> matches(type.getFlagName(), currentFlag))
        .findFirst();
  }

  /**
   * Checks if the two flags are equal.
   *
   * @param actualFlag  the actual flag to compare against
   * @param currentFlag the current flag to compare
   * @return true if the flags are identical, false otherwise
   */
  private static boolean matches(String actualFlag, String currentFlag) {
    if (currentFlag.startsWith("--")) {
      currentFlag = currentFlag.replaceFirst("--", "");
    } else if (currentFlag.startsWith("-")) {
      currentFlag = currentFlag.replaceFirst("-", "");
    }

    return currentFlag.equalsIgnoreCase(actualFlag);
  }
}
