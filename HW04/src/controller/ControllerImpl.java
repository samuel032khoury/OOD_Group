package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import model.ImageLibModel;
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
    this.cmdMap = new HashMap<String, Supplier<ICommand>>();
    this.cmdMap.put("flip", FilpCommand::new);
    this.cmdMap.put("greyscale", GreyCommand::new);
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
        cmd.execute(this.model, scanner);
      } else {
        this.view.renderMessage("command not found");
      }
    }
  }
}
