package controller.command;

import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

public abstract class ACommand implements ICommand{
  public abstract void execute(ImageLibModel model, Queue<String> currCommand,
                               IImageProcessView view) throws IllegalStateException;

  protected String getValidArgs(Queue<String> currCommand) {
    if(currCommand.peek().equals("&")) {
      throw new IllegalStateException("Insufficient argument, try again!");
    }
    return currCommand.remove();
  }
}