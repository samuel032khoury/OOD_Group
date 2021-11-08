package controller.controller;

import controller.command.color.GreyCommand;
import controller.command.library.LoadCommandV2;
import controller.command.library.SaveCommandV2;
import controller.command.color.TintingCommand;
import model.library.ImageLibModel;
import model.operation.opertor.colortrans.TiltingOperator;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;
import view.IImageProcessView;

/**
 * To represent an image process controller that has a command set to deal with user inputs, and
 * update the model according to the valid command.
 * The new controller supported more functions:
 * loading of pictures of png, bmp, jpg, jpeg
 * supported applying filers: blur, sharpen, grayscale, and sepia
 */
public class ImageProcessControllerImplV2 extends ImageProcessControllerImpl{

  /**
   * Simple constructor that initialize a new controller.
   */
  public ImageProcessControllerImplV2() {
    super();
    addNewFunction();
  }


  public ImageProcessControllerImplV2(ImageLibModel model) throws IllegalArgumentException {
    super(model);
    addNewFunction();
  }

  public ImageProcessControllerImplV2(ImageLibModel model, Readable input) throws IllegalArgumentException {
    super(model, input);
    addNewFunction();
  }

  public ImageProcessControllerImplV2(ImageLibModel model, Readable input, IImageProcessView view) throws IllegalArgumentException {
    super(model, input, view);
    addNewFunction();
  }

  protected void addNewFunction(){
    this.cmdMap.put("blur", null);
    this.cmdMap.put("sharpen", null);
    this.cmdMap.put("greyscale", () -> new GreyCommand(SimpleArithmeticGreyscaleOperator.Luma));
    this.cmdMap.put("sepia", () -> new TintingCommand(TiltingOperator.Sepia));
    this.cmdMap.put("save", SaveCommandV2::new);
    this.cmdMap.put("load", LoadCommandV2::new);
  }
}
