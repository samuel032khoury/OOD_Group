package controller.arguments;

import java.util.List;

/**
 * An interface representing a command line flag argument to an image processor main class.
 *
 * @param <T> The type of the object that will be parsed from this CLI + additional arguments.
 */
public interface ImageProcessorArgument<T> {

  /**
   * Getter for this enum's flag. Will be accepted if prefixed with `-`, `--`, or nothing.
   *
   * @return The enum's flag
   */
  ImageProcessorArgumentType getFlag();

  /**
   * Converts the additional arguments to a usable object.
   *
   * @param additionalArguments The additional arguments for this CLI arguments.
   * @return An object that is represented by this CLI argument(s) set.
   * @throws IllegalArgumentException if something went wrong during argument conversion
   */
  T convertArguments(List<String> additionalArguments) throws IllegalArgumentException;
}
