package controller;

import java.util.Scanner;

import model.ImageLibModel;

public interface ICommand {
  void execute(ImageLibModel model, Scanner scanner) throws Exception;
}


