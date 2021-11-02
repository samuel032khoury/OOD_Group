package controller.command;

import java.util.Queue;

import model.imageFile.ReadOnlyImageFile;
import model.library.ImageLibModel;
import view.IImageProcessView;

public abstract class ACommand implements ICommand {
  @Override
  public abstract void execute(ImageLibModel model, Queue<String> commandQueue,
                               IImageProcessView view) throws IllegalStateException;

  protected String getConnection(ReadOnlyImageFile newImageName) {
    return newImageName == null? " is named " : " has overwritten ";
  }

  protected abstract String currCommand();

  // to check if arguments passed to command is a splitter & instead, which implies command may
  // have fewer arguments than needed and thus throw an IllegalStateException.
  protected String getValidArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Insufficient argument for " + currCommand().toLowerCase()
              + ", try again!");
    }
    return commandQueue.remove();
  }

  protected void expectNoMoreArgs(Queue<String> commandQueue)
          throws IllegalStateException {
    if (!this.splitterOrNull(commandQueue)) {
      throw new IllegalStateException("Invalid Syntax: Too much arguments provided for "
              + currCommand().toLowerCase() + ", try again!");
    }
  }

  private boolean splitterOrNull(Queue<String> commandQueue) {
    return commandQueue.peek() == null || commandQueue.peek().equals("&");
  }
}