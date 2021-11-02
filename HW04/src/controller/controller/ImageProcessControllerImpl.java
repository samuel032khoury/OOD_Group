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
import controller.command.SaveCommand;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

public class ImageProcessControllerImpl implements IImageProcessController {
  private final ImageLibModel model;
  private final Readable input;
  private final IImageProcessView view;
  private final Map<String, Supplier<ICommand>> cmdMap;

  public ImageProcessControllerImpl() {
    this(new ImageLibModelImpl());
  }

  public ImageProcessControllerImpl(ImageLibModel model) {
    this(model, new InputStreamReader(System.in));
  }

  public ImageProcessControllerImpl(ImageLibModel model, Readable input) {
    this(model, input, new SimpleImageProcessViewImpl(model));
  }

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
    }};
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(input);
    while (scanner.hasNextLine()) {
      Queue<String> commandQueue =
              new ArrayDeque<>(Arrays.asList(scanner.nextLine().split("\\s+")));
      boolean waitPrompt = true;

      if (!commandQueue.isEmpty()) {
        switch (commandQueue.peek()) {
          case "QUIT":
            view.renderMessage("Program is quit.");
            return;
          case "SIZE":
            if (commandQueue.size() > 1) {
              view.renderError("SIZE expect no arguments while provide at least one, try again!");
            } else {
              view.renderMessage("There are " + model.getLibSize() + " images in the library!");
            }
            continue;
          case "":
            continue;
          default:
            break;
        }
        if (commandQueue.peek().startsWith("#")) {
          continue;
        }
      }

      while (!commandQueue.isEmpty()) {
        ICommand cmd = null;
        Supplier<ICommand> sup = cmdMap.getOrDefault(commandQueue.poll(), null);

        if (sup != null) {
          cmd = sup.get();
        }

        if (cmd != null) {
          if (waitPrompt) {
            view.renderMessage("Processing, please wait!");
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
