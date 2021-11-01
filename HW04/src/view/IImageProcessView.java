package view;

public interface IImageProcessView {
  void renderMessage(String message) throws IllegalStateException;

  void renderError(String message) throws IllegalStateException;
}
