package controller.controller;

import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Supplier;

import controller.command.ICommand;
import controller.command.FlipCommand;
import controller.command.AdjustBrightnessCommand;
import controller.command.GreyCommand;
import controller.command.LoadCommand;
import controller.command.QuitCommand;
import controller.command.SaveCommand;
import controller.command.SizeCommand;
import controller.utils.QuitExecution;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

/**
 * To represent an image process controller that has a command set to deal with user inputs, and
 * update the model according to the valid command.
 */
public class ImageProcessControllerImpl implements IImageProcessController {
  protected final ImageLibModel model;
  protected final Readable input;
  protected final IImageProcessView view;
  protected final Map<String, Supplier<ICommand>> cmdMap;

  /**
   * A convenient constructor for building an image process controller with a default {@link
   * ImageLibModelImpl}.
   */
  public ImageProcessControllerImpl() {
    this(new ImageLibModelImpl());
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process
   * controller. Use default {@code system.in} and {@code system.out} for input/view destination.
   *
   * @param model the customized library model for controller to use
   * @throws IllegalArgumentException when the provided {@code model} is null
   */
  public ImageProcessControllerImpl(ImageLibModel model) throws IllegalArgumentException {
    this(model, new InputStreamReader(System.in));
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process controller,
   * with a specified {@link Readable} object.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @throws IllegalArgumentException when the provided {@code model} or {@code input} is null
   */
  public ImageProcessControllerImpl(ImageLibModel model, Readable input)
          throws IllegalArgumentException {
    this(model, input, new SimpleImageProcessViewImpl(model));
  }

  /**
   * A fully customizable constructor for building an image process controller.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @param view a target {@link IImageProcessView} for view purpose
   * @throws IllegalArgumentException when any of the provided arguments is null
   */
  public ImageProcessControllerImpl(ImageLibModel model, Readable input, IImageProcessView view)
          throws IllegalArgumentException {
    if (model == null || input == null || view == null) {
      throw new IllegalArgumentException("Illegal inputs for generating a new image process "
              + "controller");
    }
    this.model = model;
    this.input = input;
    this.view = view;
    this.cmdMap = new HashMap<>() {{
        put("vertical-flip", () -> new FlipCommand(true));
        put("horizontal-flip", () -> new FlipCommand(false));
        put("brighten", () -> new AdjustBrightnessCommand(true));
        put("darken", () -> new AdjustBrightnessCommand(false));
        put("blue-component", () -> new GreyCommand(SingleChannelOperator.Blue));
        put("red-component", () -> new GreyCommand(SingleChannelOperator.Red));
        put("green-component", () -> new GreyCommand(SingleChannelOperator.Green));
        put("luma-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Luma));
        put("value-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Value));
        put("intensity-component",
            () -> new GreyCommand(SimpleArithmeticChannelOperator.Intensity));
        put("load", LoadCommand::new);
        put("save", SaveCommand::new);
        put("size", SizeCommand::new);
        put("QUIT", QuitCommand::new);
      }};
  }

  /**
   * This method runs the program, expecting user's input from the {@link Readable}, updates the
   * {@link ImageLibModel} model and let view print feedback message according to the received
   * valid command. Whenever the command is not valid (unsupported behavior/ unmatched arguments
   * for a supported command), it tells view to print informative error message and let user try
   * again.
   */
  @Override
  public void run() {
    Scanner scanner = new Scanner(input);
    while (scanner.hasNextLine()) {
      Queue<String> commandQueue =
              new ArrayDeque<>(Arrays.asList(scanner.nextLine().split("\\s+")));
      boolean waitPrompt = true;

      if (!commandQueue.isEmpty()
              && (commandQueue.peek().equals("") || commandQueue.peek().startsWith("#"))) {
        continue;
      }


      while (!commandQueue.isEmpty()) {
        ICommand cmd = null;
        Supplier<ICommand> sup = cmdMap.getOrDefault(commandQueue.poll(), null);

        if (sup != null) {
          cmd = sup.get();
        }

        if (cmd != null) {
          if (waitPrompt) {
            view.renderMessage("Processing command, please wait!");
            waitPrompt = false;
          }
          try {
            cmd.execute(this.model, commandQueue, this.view);
            commandQueue.poll();
          } catch (IllegalStateException e) {
            this.view.renderError(e.getMessage());
            commandQueue.clear();
          } catch (QuitExecution e) {
            return;
          }
        } else {
          this.view.renderError("Command not found!");
          commandQueue.clear();
        }
      }
    }
  }
}
