package view;


import java.io.IOException;

import model.library.ImageLibState;

public class ImageProcessViewImpl implements IImageProcessView {
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_RESET = "\u001B[0m";


  Appendable output;
  ImageLibState model;

  public ImageProcessViewImpl(Appendable output, ImageLibState model) {
    this.output = output;
    this.model = model;
  }

  @Override
  public void renderMessage(String message) {
    try {
      this.output.append(message).append("\n");
    } catch(IOException e) {
      throw new RuntimeException("Can't write");
    }

  }

  @Override
  public void renderError(String message) {
    try {
      this.output.append(ANSI_RED).append(message).append(ANSI_RESET).append("\n");
    } catch(IOException e) {
      throw new RuntimeException("Can't write");
    }
  }
}
