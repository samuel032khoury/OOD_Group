package controller.controller;

import java.io.InputStreamReader;

import controller.command.color.FilterCommand;
import controller.command.color.GreyCommand;
import controller.command.library.LoadCommand;
import controller.command.color.TintingCommand;
import controller.command.library.SaveCommand;
import controller.utils.LoadSuffixManagerV2;
import controller.utils.WriteSuffixManagerV2;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import model.operation.opertor.colortrans.TiltingOperator;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;
import model.operation.opertor.filter.SimpleFilterOperator;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

/**
 * To represent an image process controller that has a command set to deal with user inputs, and
 * update the model according to the valid command. The new controller supported more
 * functionalities: loading of pictures of png, bmp, jpg, jpeg supported applying filers: blur,
 * sharpen, grayscale, and sepia
 */
public class ImageProcessControllerImplV2 extends ImageProcessControllerImpl {

  /**
   * A convenient constructor for building an image process controller (V2) with a default {@link
   * ImageLibModelImpl}.
   */
  public ImageProcessControllerImplV2() {
    this(new ImageLibModelImpl());
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process controller
   * (V2). Use default {@code system.in} and {@code system.out} for input/view destination.
   *
   * @param model the customized library model for controller to use
   * @throws IllegalArgumentException when the provided {@code model} is null
   */
  public ImageProcessControllerImplV2(ImageLibModel model) throws IllegalArgumentException {
    this(model, new InputStreamReader(System.in));
  }

  /**
   * An {@link ImageLibModelImpl}-customizable constructor for building an image process controller
   * (V2), with a specified {@link Readable} object.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @throws IllegalArgumentException when the provided {@code model} or {@code input} is null
   */
  public ImageProcessControllerImplV2(ImageLibModel model, Readable input)
          throws IllegalArgumentException {
    this(model, input, new SimpleImageProcessViewImpl(model));
  }

  /**
   * A fully customizable constructor for building an image process controller (V2) with new
   * functionalities.
   *
   * @param model the customized library model for controller to use
   * @param input a specified {@link Readable} object
   * @param view  a target {@link IImageProcessView} for view purpose
   * @throws IllegalArgumentException when any of the provided arguments is null
   */
  public ImageProcessControllerImplV2(ImageLibModel model, Readable input, IImageProcessView view)
          throws IllegalArgumentException {
    super(model, input, view);
    this.cmdMap.put("blur", () -> new FilterCommand(SimpleFilterOperator.Blur));
    this.cmdMap.put("sharpen", () -> new FilterCommand(SimpleFilterOperator.Sharpening));
    this.cmdMap.put("greyscale", () -> new GreyCommand(SimpleArithmeticGreyscaleOperator.Luma));
    this.cmdMap.put("sepia", () -> new TintingCommand(TiltingOperator.Sepia));
    this.cmdMap.put("save", () -> new SaveCommand(new WriteSuffixManagerV2()));
    this.cmdMap.put("load", () -> new LoadCommand(new LoadSuffixManagerV2()));
  }
}
