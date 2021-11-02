package controller.command;

import java.util.ArrayDeque;
import java.util.Queue;

import model.library.ImageLibModel;
import view.IImageProcessView;

public abstract class ACommand implements ICommand {
  public abstract void execute(ImageLibModel model, Queue<String> currCommand,
                               IImageProcessView view) throws IllegalStateException;

  // to check if arguments passed to command is a splitter & instead, which implies command may
  // have fewer arguments than needed and thus throw an IllegalStateException.
  protected String getValidArgs(Queue<String> currCommand, String currAction)
          throws IllegalStateException {
    if (this.splitterOrNull(currCommand)) {
      currCommand.clear();
      throw new IllegalStateException("Insufficient argument to " + currAction + ", try again!");
    }
    return currCommand.remove();
  }

  protected void expectNoMoreArgs(Queue<String> currCommand, String currAction)
          throws IllegalStateException {
    if (!this.splitterOrNull(currCommand)) {
      currCommand.clear();
      throw new IllegalStateException("Invalid Syntax: Too much arguments provided for "
              + currAction + ", try again!");
    }
  }

  private boolean splitterOrNull(Queue<String> currCommand) {
    return currCommand.peek() == null || currCommand.peek().equals("&");
  }
}