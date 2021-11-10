package utils;

import java.awt.Color;
import java.io.IOException;

import model.imagefile.ImageFile;
import model.operation.IImageOperation;

/**
 * To represent a mock {@link ImageFile} for testing purpose.
 */
public class MockImage implements ImageFile {
  private final Appendable output;

  public MockImage(Appendable output) {
    this.output = output;
  }

  @Override
  public ImageFile applyOperation(IImageOperation operation) {
    try {
      output.append("did "+ operation.getClass());
    } catch (IOException e) {
      throw new RuntimeException("cant write");
    }
    return null;
  }

  @Override
  public ImageFile copy() {
    return this;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public boolean alpha() {
    return false;
  }

  @Override
  public int getMaxColorVal() {
    return 0;
  }

  @Override
  public Color getColorAt(int row, int col) {
    return null;
  }
}
