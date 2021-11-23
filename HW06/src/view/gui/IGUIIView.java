package view.gui;

import javax.swing.JFileChooser;

import view.text.IImageProcessView;

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

  /**
   * TODO.
   */
  void updateViewMeta(String newImageName);

  /**
   * TODO.
   */
  int getSaveStatus(JFileChooser fileExplorer);

  /**
   * TODO.
   */
  int getLoadStatus(JFileChooser fileExplorer);

  /**
   * TODO.
   */
  String getCurrImage();

  /**
   * TODO.
   */
  String dialogGetInput(String prompt, String title, String defaultName);

  /**
   * TODO.
   */
  void updatePreviewImage();

  /**
   * TODO.
   */
  void updateHistogramGraph();
}
