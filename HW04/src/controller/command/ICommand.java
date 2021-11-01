package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

public interface ICommand {
  void execute(ImageLibModel model, Queue<String> currCommand, IImageProcessView view)
          throws IllegalStateException;
}


