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

  void updateViewMeta(String newImageName);

  int getSaveStatus(JFileChooser fileExplorer);

  int getLoadStatus(JFileChooser fileExplorer);

  String getCurrImage();

  String dialogGetInput(String prompt, String title, String defaultName);

  void updatePreviewImage();

  void updateHistogramGraph();
}
