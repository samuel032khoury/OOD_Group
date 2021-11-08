package controller.controller;

import controller.command.LoadCommand;
import controller.command.LoadCommandV2;
import controller.command.SaveCommand;
import controller.command.SaveCommandV2;
import controller.command.SizeCommand;
import model.library.ImageLibModel;
import view.IImageProcessView;

public class ImageProcessControllerV2 extends ImageProcessControllerImpl{
  public ImageProcessControllerV2() {
    super();
  }

  public ImageProcessControllerV2(ImageLibModel model) throws IllegalArgumentException {
    super(model);
  }

  public ImageProcessControllerV2(ImageLibModel model, Readable input) throws IllegalArgumentException {
    super(model, input);
  }

  public ImageProcessControllerV2(ImageLibModel model, Readable input, IImageProcessView view) throws IllegalArgumentException {
    super(model, input, view);
  }

  protected void addNewFunction(){
    this.cmdMap.put("blur", null);
    this.cmdMap.put("sharpen", null);
    this.cmdMap.put("greyscale", null);
    this.cmdMap.put("sepia", null);
    this.cmdMap.put("save", SaveCommandV2::new);
    this.cmdMap.put("load", LoadCommandV2::new);
  }
}
