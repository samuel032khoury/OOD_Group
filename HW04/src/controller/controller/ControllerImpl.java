package controller.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

import controller.command.BrightenCommand;
import controller.command.FlipCommand;
import controller.command.GreyCommand;
import controller.command.ICommand;
import controller.command.LoadCommand;
import model.library.ImageLibModel;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;
import view.IView;

public class ControllerImpl implements IControllerModel {
  ImageLibModel model;
  Readable input;
  IView view;
  Map<String, Supplier<ICommand>> cmdMap;

  public ControllerImpl(ImageLibModel model, Readable input, IView view) {
    this.model = model;
    this.input = input;
    this.view = view;
    this.cmdMap = new HashMap<>();
    this.cmdMap.put("vertical-flip", () -> new FlipCommand(true));
    this.cmdMap.put("horizontal-flip", () -> new FlipCommand(false));
    this.cmdMap.put("blue-component", () -> new GreyCommand(SingleChannelOperator.Blue));
    this.cmdMap.put("red-component", () -> new GreyCommand(SingleChannelOperator.Red));
    this.cmdMap.put("green-component", () -> new GreyCommand(SingleChannelOperator.Green));
    this.cmdMap.put("luma-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Luma));
    this.cmdMap.put("value-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Value));
    this.cmdMap.put("intensity-component", () -> new GreyCommand(SimpleArithmeticChannelOperator.Intensity));
    this.cmdMap.put("brighten", BrightenCommand::new);
    this.cmdMap.put("load", LoadCommand::new);
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(input);
    while (scanner.hasNext()) {
      String order = scanner.next();
      ICommand cmd = null;
      Supplier<ICommand> sup = null;
      sup = cmdMap.getOrDefault(order, null);
      if (sup != null) {
        cmd = sup.get();
      }

      if (cmd != null) {
        try {
          cmd.execute(this.model, scanner, this.view);
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("Input is not enough!");
        }

      } else {
        this.view.renderError("command not found");
      }
    }
  }
}
