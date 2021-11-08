package controller.controller;

import controller.command.GreyCommand;
import controller.command.LoadCommandV2;
import controller.command.SaveCommandV2;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
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
   * A convenient constructor for building an image process controller with a default {@link
   * ImageLibModelImpl}.
   */
  public ImageProcessControllerV2() {
    super();
    addNewFunction();
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process
   * controller. Use default {@code system.in} and {@code system.out} for input/view destination.
   *
   * @param model the customized library model for controller to use
   * @throws IllegalArgumentException when the provided {@code model} is null
   */
  public ImageProcessControllerV2(ImageLibModel model) throws IllegalArgumentException {
    super(model);
    addNewFunction();
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process controller,
   * with a specified {@link Readable} object.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @throws IllegalArgumentException when the provided {@code model} or {@code input} is null
   */
  public ImageProcessControllerV2(ImageLibModel model, Readable input) throws IllegalArgumentException {
    super(model, input);
    addNewFunction();
  }

  /**
   * A fully customizable constructor for building an image process controller.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @param view a target {@link IImageProcessView} for view purpose
   * @throws IllegalArgumentException when any of the provided arguments is null
   */
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
