package controller.controller;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.function.Supplier;

import controller.command.macro.ICommand;
import controller.utils.QuitExecution;
import model.library.ImageLibModel;
import view.IGUIIView;
import view.ViewListener;

public class ImageProcessControllerGUIImpl extends ImageProcessControllerImplV2 implements
        ImageProcessControllerGUI {

  private final ImageLibModel model;
  private final Queue<String> commandQueue;
  private final IGUIIView view;
  private String currImageName;


  /**
   * To construct a {@link ImageProcessControllerGUIImpl} that interacts with a {@link
   * ImageLibModel}, by the commands provided/updated from the {@link #commandQueue}, and visualize
   * the graphics on a {@link IGUIIView}.
   *
   * @param model The {@link ImageLibModel} that this controller interacts with.
   */
  public ImageProcessControllerGUIImpl(ImageLibModel model) {
    this.model = model;
    this.commandQueue = new ArrayDeque<>();
    this.view = new view.GUIIViewImpl(model, super.cmdMap.keySet(), this);
  }

  /**
   * To run the controller, input/error handling depends on specific implementation.
   */
  @Override
  public void run() {
    this.view.showView(true);
    if (commandQueue.isEmpty()) {
      return;
    }
    try {
      Supplier<ICommand> sup = cmdMap.get(commandQueue.poll());
      ICommand cmd = sup.get();
      cmd.execute(this.model, commandQueue, this.view);
    } catch (IllegalStateException | QuitExecution e) {
      this.view.renderError(e.getMessage());
      commandQueue.clear();
    } catch (NullPointerException e) {
      this.view.renderError("No such an operation can be performed!");
      commandQueue.clear();
    }
  }

  @Override
  public void getArgsRun(String... commandArgs) {
    Collections.addAll(this.commandQueue, commandArgs);
    this.run();
  }
}
