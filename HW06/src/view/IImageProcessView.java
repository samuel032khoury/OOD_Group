package view;

/**
 * An interface for a view that will be used to render message or render error.
 */
public interface IImageProcessView {
  /**
   * To render a message.
   *
   * @param message The message to render.
   * @throws IllegalStateException if unable to render a message.
   */
  void renderMessage(String message) throws IllegalStateException;

  /**
   * To render a error.
   *
   * @param message The error to render.
   * @throws IllegalStateException if unable to render a error.
   */
  void renderError(String message) throws IllegalStateException;
}
