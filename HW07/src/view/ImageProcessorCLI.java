package view;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import controller.handler.ImageProcessCommandHandler;
import controller.transfer.ImageTransferType;
import controller.transfer.ImageTransferer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 * This class is a Command Line Interface for handling commands to process images. It accepts a list
 * of {@link ImageProcessCommandHandler} objects, which are used to delegate properly formatted
 * commands to their relevant handler. To extend the interface, simply add more command objects to
 * its constructor.
 */
public class ImageProcessorCLI implements ImageProcessorView {

  // Final variables for controlling default CLI functionality
  private static final String WELCOME_MESSAGE = "Welcome to our image processor tool!";
  private static final Set<String> QUIT_STRINGS = Set.of("q", "quit");
  private static final Set<String> HELP_STRINGS = Set.of("h", "help");

  // Default values in case constructor receives null
  private static final Readable DEFAULT_READER = new BufferedReader(
      new InputStreamReader(System.in));
  private static final Appendable DEFAULT_APPENDABLE = System.out;

  private final Scanner inputScanner;
  private final Appendable output;
  private final List<ImageProcessCommandHandler> commandHandlers; // currently, supported commands
  private final Map<ImageTransferType, ImageTransferer> imageLoaders; // image type loaders
  private final ImageProcessorController controller; // application state manager

  /**
   * Convenience constructor for using default input and output.
   *
   * @param commandHandlers The command handlers to make available
   * @param imageLoaders    The image loaders to make available
   */
  public ImageProcessorCLI(List<ImageProcessCommandHandler> commandHandlers,
      Map<ImageTransferType, ImageTransferer> imageLoaders) {
    this(commandHandlers, imageLoaders, DEFAULT_READER, DEFAULT_APPENDABLE);
  }

  /**
   * Creates a CLI object with a given set of supported command handlers.
   *
   * @param commandHandlers The set of handlers for given commands.
   * @param imageLoaders    the map of image loaders -- classes that map specific file types to the
   *                        related file type transferers
   * @param input           a readable object specifying the input stream
   * @param output          an appendable object specifying the output stream
   */
  public ImageProcessorCLI(List<ImageProcessCommandHandler> commandHandlers,
      Map<ImageTransferType, ImageTransferer> imageLoaders,
      Readable input,
      Appendable output) {
    this(commandHandlers,
        imageLoaders,
        input,
        output,
        new ImageProcessorControllerImpl(commandHandlers, imageLoaders)
    );
  }

  /**
   * Convenience constructor to make mocking the controller for testing easier.
   *
   * @param commandHandlers The set of handlers for given commands.
   * @param imageLoaders    the map of image loaders -- classes that map specific file types to the
   *                        related file type transferers
   * @param input           a readable object specifying the input stream
   * @param output          an appendable object specifying the output stream
   * @param controller      The controller to be usd by this CLI
   */
  public ImageProcessorCLI(List<ImageProcessCommandHandler> commandHandlers,
      Map<ImageTransferType, ImageTransferer> imageLoaders,
      Readable input,
      Appendable output,
      ImageProcessorController controller) {
    this.commandHandlers = Optional.ofNullable(commandHandlers).orElseGet(List::of);
    this.imageLoaders = Optional.ofNullable(imageLoaders).orElseGet(Map::of);
    this.inputScanner = new Scanner(Optional.ofNullable(input).orElse(DEFAULT_READER));
    this.output = Optional.ofNullable(output).orElse(DEFAULT_APPENDABLE);
    this.controller = Optional.ofNullable(controller)
        .orElse(new ImageProcessorControllerImpl(commandHandlers, imageLoaders));
  }

  /**
   * Runs the CLI tool by getting and handling user input. Enter 'q' to quit.
   */
  @Override
  public void run() {
    printNewLine(WELCOME_MESSAGE);
    printNewLine("");
    printHelpMessage();

    String nextCommand = getNextCommand();

    while (!QUIT_STRINGS.contains(nextCommand.toLowerCase())) {
      if (HELP_STRINGS.contains(nextCommand.toLowerCase())) {
        printHelpMessage();
      } else {
        List<String> command = new ArrayList<>(Arrays.asList(nextCommand.split(" ")));

        try {
          controller.handle(command);
        } catch (IllegalArgumentException e) {
          printError(command, e);
        }
      }

      nextCommand = getNextCommand();
    }
  }

  /**
   * Gets the next command from the input scanner.
   *
   * @return the next command from the input scanner
   */
  private String getNextCommand() {
    printNewLine("Enter a command followed by 'enter'...");
    return inputScanner.nextLine();
  }

  // Outputs a generated command helper message to the output channel.
  // Visible for testing
  void printHelpMessage() {
    StringBuilder builder = new StringBuilder("Supported Commands:");

    // This is sorted for consistent testing
    commandHandlers
        .stream()
        .sorted(Comparator.comparing(ImageProcessCommandHandler::getCommandName))
        .forEach(handler ->
            builder
                .append("\n- ")
                .append(handler.getCommandName())
                .append(": ")
                .append(handler.getHelpText()));

    builder.append("\n\nSupported File Types:\n");

    // This is sorted for consistent testing
    imageLoaders
        .values()
        .stream()
        .sorted(Comparator.comparing(ImageTransferer::getFileTypes))
        .forEach(transferer -> builder.append(transferer.getFileTypes()).append("\n"));

    printNewLine(builder.toString());
  }

  /**
   * Writes a "failed to handle command X" error message to the dedicated output channel.
   *
   * @param command the command the controller failed to handle
   * @param e       the exception
   */
  private void printError(List<String> command, IllegalArgumentException e) {
    printNewLine(String.format("Failed to handle %s: %s", command, e.getMessage()));
    printHelpMessage();
  }

  /**
   * Outputs a message with a new line character in the end to the dedicated output channel.
   *
   * @param msg the message to output
   */
  private void printNewLine(String msg) {
    try {
      output.append(msg);
      output.append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
