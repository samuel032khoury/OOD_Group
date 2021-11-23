package utils;

import java.awt.event.ActionEvent;
import java.io.IOException;

import controller.controller.gui.ImageProcessControllerGUIImpl;
import model.library.ImageLibModel;
import view.gui.IGUIIView;

public class MockControllerGUI extends ImageProcessControllerGUIImpl {
  private final Appendable output;

  /**
   * Create a mock model for testing.
   *
   * @param model The {@link ImageLibModel} that this controller interacts with.
   */
  public MockControllerGUI(ImageLibModel model, Appendable output) {
    super(model);
    this.output = output;
  }

  @Override
  public void runEvent(String... commandArgs) {
    for (String arg : commandArgs) {
      try {
        this.output.append(arg);
      } catch (IOException e) {
        throw new RuntimeException("can't write");
      }


    }

  }

  public void performAction(ActionEvent event) {
    this.listener.actionPerformed(event);
  }
}
