package view.text;

import java.io.IOException;

import model.library.ImageLibModelImpl;
import model.library.ImageLibState;

/**
 * To represent an abstracted view for image process, it can access to a {@link ImageLibState},
 * getting information by command if needed and provide feedback to the output.
 */
public abstract class AImageProcessView implements IImageProcessView {
  protected static final String ANSI_RED = "\u001B[31m";
  protected static final String ANSI_RESET = "\u001B[0m";

  Appendable output;
  ImageLibState model;


  /**
   * A fully customizable constructor.
   *
   * @param output the appendable to write message.
   * @param model  the model to take in.
   * @throws IllegalArgumentException if output or model is null.
   */
  public AImageProcessView(Appendable output, ImageLibState model) throws IllegalArgumentException {
    if (output == null || model == null) {
      throw new IllegalArgumentException();
    }
    this.output = output;
    this.model = model;
  }

  /**
   * A constructor that can set the model to take in.
   *
   * @param model the model to take in.
   */
  public AImageProcessView(ImageLibState model) {
    this(System.out, model);
  }

  /**
   * A simple constructor.
   */
  public AImageProcessView() {
    this(System.out, new ImageLibModelImpl());
  }

  /**
   * To render a message {@code message} on the given {@link #output}.
   *
   * @param message the message being rendered
   * @throws IllegalStateException if the render process fails
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.output.append("> ").append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("View is unable to render messages!");
    }
  }

  /**
   * To render an error message {@code message} on the given {@link #output}.
   *
   * @param message the error being rendered
   * @throws IllegalStateException if the render process fails
   */
  @Override
  public void renderError(String message) throws IllegalStateException {
    try {
      this.output.append(ANSI_RED).append(message).append(ANSI_RESET).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("View is unable to render errors!");
    }
  }
}
