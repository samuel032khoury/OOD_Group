package controller.controller;

import controller.command.GreyCommand;
import controller.command.LoadCommandV2;
import controller.command.SaveCommandV2;
import model.library.ImageLibModel;
import model.operation.ColorTransOperator;
import model.operation.SimpleArithmeticChannelOperator;
import view.IImageProcessView;

/**
 * To represent an image process controller that has a command set to deal with user inputs, and
 * update the model according to the valid command.
 * The new controller supported more functions:
 * loading of pictures of png, bmp, jpg, jpeg
 * supported applying filers: blur, sharpen, grayscale, and sepia
 */
public class ImageProcessControllerV2 extends ImageProcessControllerImpl{

  /**
   * Simple constructor that initialize a new controller.
   */
  public ImageProcessControllerV2() {
    super();
    addNewFunction();
  }


  public ImageProcessControllerV2(ImageLibModel model) throws IllegalArgumentException {
    super(model);
    addNewFunction();
  }

  public ImageProcessControllerV2(ImageLibModel model, Readable input) throws IllegalArgumentException {
    super(model, input);
    addNewFunction();
  }

  public ImageProcessControllerV2(ImageLibModel model, Readable input, IImageProcessView view) throws IllegalArgumentException {
    super(model, input, view);
    addNewFunction();
  }

  protected void addNewFunction(){
    this.cmdMap.put("blur", null);
    this.cmdMap.put("sharpen", null);
    this.cmdMap.put("greyscale", () -> new GreyCommand(SimpleArithmeticChannelOperator.Luma));
    this.cmdMap.put("sepia", () -> new GreyCommand(ColorTransOperator.Sepia));
    this.cmdMap.put("save", SaveCommandV2::new);
    this.cmdMap.put("load", LoadCommandV2::new);
  }
}
