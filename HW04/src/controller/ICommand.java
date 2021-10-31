package controller;

import java.util.Scanner;

import model.library.ImageLibModel;

public interface ICommand {
  void execute(ImageLibModel model, Scanner scanner);
}


