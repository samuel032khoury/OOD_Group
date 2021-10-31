package controller.command;

import java.util.Scanner;

import model.library.ImageLibModel;
import view.IView;

public interface ICommand {
  void execute(ImageLibModel model, Scanner scanner, IView view) throws IllegalStateException;
}


