package view.text;

import model.library.ImageLibState;

/**
 * A view that has access to a {@link ImageLibState} model. It interacts with the model by receiving
 * messages or errors sent from it and rendering it to the output.
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
