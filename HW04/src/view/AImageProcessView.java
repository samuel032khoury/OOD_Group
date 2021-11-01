package view;

import java.io.IOException;

import model.library.ImageLibState;

public abstract class AImageProcessView implements IImageProcessView {
  protected static final String ANSI_RED = "\u001B[31m";
  protected static final String ANSI_RESET = "\u001B[0m";

  Appendable output;
  ImageLibState model;

  public AImageProcessView(Appendable output, ImageLibState model) throws IllegalArgumentException{
    if(output == null || model == null) {
      throw new IllegalArgumentException();
    }
    this.output = output;
    this.model = model;
  }

  public AImageProcessView(ImageLibState model) {
    if(model == null) {
      throw new IllegalArgumentException();
    }
    this.output = System.out;
    this.model = model;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.output.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("View is unable to render messages!");
    }
  }

  @Override
  public void renderError(String message) throws IllegalStateException {
    try {
      this.output.append(ANSI_RED).append(message).append(ANSI_RESET).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("View is unable to render errors!");
    }
  }
}
