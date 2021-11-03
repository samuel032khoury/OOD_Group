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
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

// TODO
public class ImageProcessControllerImpl implements IImageProcessController {
  private final ImageLibModel model;
  private final Readable input;
  private final IImageProcessView view;
  private final Map<String, Supplier<ICommand>> cmdMap;

  /**
   * A quick constructor that build a new controller using a new model.
   */
  public ImageProcessControllerImpl() {
    this(new ImageLibModelImpl());
  }

  /**
   * A constructor that new controller using a given model.
   * Uses system.in and system.out for input.
   * @param model the model for the controller to take in.
   */
  public ImageProcessControllerImpl(ImageLibModel model) {
    this(model, new InputStreamReader(System.in));
  }

  /**
   * A constructor that new controller using a given model and a given input.
   * Uses system.out for output.
   * @param model the model for the controller to take in.
   * @param input the readable for the controller to read inputs.
   */
  public ImageProcessControllerImpl(ImageLibModel model, Readable input) {
    this(model, input, new SimpleImageProcessViewImpl(model));
  }

  /**
   * A constructor that new controller using a given model and a given input and given output.
   * @param model the model for the controller to take in.
   * @param input the readable for the controller to read inputs.
   * @param view the appendable for the controller to write outputs.
   */
  public ImageProcessControllerImpl(ImageLibModel model, Readable input, IImageProcessView view) {
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
   * The core of the controller.
   * It will need to handle user input, deal with errors, process the model via the model
   * and send necessary information through view.
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
          }
        } else {
          this.view.renderError("Command not found!");
          commandQueue.clear();
        }
      }
    }
  }
}
