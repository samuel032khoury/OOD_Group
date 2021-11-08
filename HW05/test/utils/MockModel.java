package utils;

import java.io.IOException;

import model.imagefile.ImageFile;
import model.imagefile.ReadOnlyImageFile;
import model.library.ImageLibModel;

/**
 * To represent a mock {@link ImageLibModel} for testing purpose.
 */
public class MockModel<T extends ImageFile<T>> implements ImageLibModel<T> {
  private final Appendable output;

  public MockModel(Appendable output) {
    this.output = output;
  }

  @Override
  public void loadImage(String imageName, ImageFile imageFile) {
    try {
      output.append("loaded " + imageName);
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }

  }

  @Override
  public T get(String imageName) throws IllegalStateException {
    return null;
  }

  @Override
  public int getLibSize() {
    return 0;
  }

  @Override
  public ReadOnlyImageFile peek(String imageName) {
    try {
      output.append("peek " + imageName);
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }

    return null;
  }
}
