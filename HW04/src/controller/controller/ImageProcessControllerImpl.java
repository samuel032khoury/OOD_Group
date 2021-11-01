package controller.controller;

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
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;
import view.IImageProcessView;

public class ImageProcessControllerImpl implements IImageProcessController {
  protected ImageLibModel model;
  protected Readable input;
  protected IImageProcessView view;
  protected Map<String, Supplier<ICommand>> cmdMap;

  public ImageProcessControllerImpl(ImageLibModel model, Readable input, IImageProcessView view) {
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
      put("intensity-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Intensity));
      put("load", LoadCommand::new);
      put("save", SaveCommand::new);
    }};
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(input);
    while (scanner.hasNextLine()) {
      Queue<String> currCommand = new ArrayDeque<>(Arrays.asList(scanner.nextLine().split(" ")));
      boolean prompt = true;
      if (currCommand.peek() != null) {
        if (currCommand.peek().equals("QUIT")) {
          view.renderMessage("Program is quit.");
          return;
        }
        if (currCommand.peek().equals("")) {
          continue;
        }
      }

      while (!currCommand.isEmpty()) {
        ICommand cmd = null;
        Supplier<ICommand> sup;
        sup = cmdMap.getOrDefault(currCommand.poll(), null);
        if (sup != null) {
          cmd = sup.get();
        }

        if (cmd != null) {
          if (prompt) {
            view.renderMessage("Processing, please wait!");
            prompt = false;
          }
          try {
            cmd.execute(this.model, currCommand, this.view);
            currCommand.poll();
          } catch (IllegalStateException e) {
            this.view.renderError(e.getMessage());
            while (!currCommand.isEmpty()) {
              if (currCommand.poll().equals("&")) {
                break;
              }
            }
          }
        } else {
          this.view.renderError("command not found!");
        }
      }
    }
  }
}
