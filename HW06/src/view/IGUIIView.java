package view;

/**
 * To represent a {@link IImageProcessView} specifically for GUI to render preview images and
 * messages/errors.
 */
public interface IGUIIView extends IImageProcessView {
  /**
   * To switch the visibility of the View window.
   * @param show true if to show, false if not.
   */
  void showView(boolean show);
}
