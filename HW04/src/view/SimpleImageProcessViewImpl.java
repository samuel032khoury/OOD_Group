package view;

import model.library.ImageLibState;

public class SimpleImageProcessViewImpl extends AImageProcessView {
  public SimpleImageProcessViewImpl(Appendable output, ImageLibState model) {
    super(output, model);
  }

  public SimpleImageProcessViewImpl(ImageLibState model) {
    super(model);
  }
}
