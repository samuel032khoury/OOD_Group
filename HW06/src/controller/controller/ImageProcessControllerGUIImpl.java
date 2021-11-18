package controller.controller;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.function.Supplier;

import controller.command.macro.ICommand;
import controller.utils.QuitExecution;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.IGUIIView;
import view.IGUIIViewImpl;

public class ImageProcessControllerGUIImpl extends ImageProcessControllerImplV2 implements ImageProcessControllerGUI {

  private final ImageLibModel model;
  private final Queue<String> commandQueue;
  private final IGUIIView view;

  public ImageProcessControllerGUIImpl(ImageLibModel model) {
    this.model = model;
    this.commandQueue = new ArrayDeque<>();
    this.view = new IGUIIViewImpl(model, super.cmdMap.keySet(), this);
  }

  /**
   * To run the controller, input/error handling depends on specific implementation.
   */
  @Override
  public void run() {
    if (commandQueue.isEmpty()) {
      return;
    }
    Supplier<ICommand> sup = cmdMap.get(commandQueue.poll());
    ICommand cmd = sup.get();

    try {
      cmd.execute(this.model, commandQueue, this.view);
    } catch (IllegalStateException | QuitExecution e) {
      this.view.renderError(e.getMessage());
      commandQueue.clear();
    }
  }

  public static void main(String[] args) {
    IImageProcessController controller = new ImageProcessControllerGUIImpl(new ImageLibModelImpl());
    controller.run();
  }

  @Override
  public void getArgsRun(String... commandArgs) {
    Collections.addAll(this.commandQueue, commandArgs);
    this.run();
  }
}
