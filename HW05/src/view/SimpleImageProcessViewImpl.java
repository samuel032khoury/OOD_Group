package view;

import model.library.ImageLibState;

/**
 * A simple view class that extends that abstract class.
 */
public class SimpleImageProcessViewImpl extends AImageProcessView {
  public SimpleImageProcessViewImpl(Appendable output, ImageLibState model) {
    super(output, model);
  }

  public SimpleImageProcessViewImpl(ImageLibState model) {
    super(model);
  }

  public SimpleImageProcessViewImpl() {
    super();
  }
}
