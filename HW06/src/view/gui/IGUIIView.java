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
   *
   * @param show true if to show, false if not.
   */
  void showView(boolean show);

  /**
   * Based on the name of the new image view panel currently displays, {@code newImageName}, update
   * view's meta information.
   *
   * @param newImageName the name of the image that the view panel is about to display
   */
  void updateViewMeta(String newImageName);

  /**
   * To get the save file status after saving a file with {@link JFileChooser}.
   *
   * @param fileExplorer the delegated {@link JFileChooser}
   * @return the return state of the file chooser on pop-down:
   * <ul>
   * <li>JFileChooser.CANCEL_OPTION
   * <li>JFileChooser.APPROVE_OPTION
   * <li>JFileChooser.ERROR_OPTION if an error occurs or the
   *                  dialog is dismissed
   * </ul>
   */
  int getSaveStatus(JFileChooser fileExplorer);

  /**
   * To get the open file status after selecting a file with {@link JFileChooser}.
   * @param fileExplorer  the delegated JFileChooser
   * @return the return state of the file chooser on pop-down:
   * <ul>
   * <li>JFileChooser.CANCEL_OPTION
   * <li>JFileChooser.APPROVE_OPTION
   * <li>JFileChooser.ERROR_OPTION if an error occurs or the
   *                  dialog is dismissed
   * </ul>
   */
  int getLoadStatus(JFileChooser fileExplorer);

  /**
   * Get the name of the image currently being worked on.
   *
   * @return the name of the image currently being worked on
   */
  String getCurrImage();

  /**
   * Post a dialog in a {@link javax.swing.JPanel} form and gets user's inputs.
   *
   * @param prompt      the prompt for the pop-up dialog
   * @param title       the title of the pop-up dialog
   * @param defaultName the default value put in the input box on the pop-up dialog
   * @return an input as a String received from the pop-up dialog
   */
  String dialogGetInput(String prompt, String title, String defaultName);

  /**
   * Update the preview image shown on the view.
   */
  void updatePreviewImage();

  /**
   * Update the histogram graph shown on the view.
   */
  void updateHistogramGraph();
}
