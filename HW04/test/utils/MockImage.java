package utils;

import java.awt.*;
import java.io.IOException;

import model.imageFile.ImageFile;
import model.operation.IChannelOperator;

public class MockImage implements ImageFile {
  private final Appendable output;

  public MockImage(Appendable output) {
    this.output = output;
  }

  @Override
  public ImageFile vertiFlip() {
    try {
      output.append("did vertiFlip");
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }
    return null;
  }

  @Override
  public ImageFile horizFlip() {
    try {
      output.append("did horizFlip");
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }
    return null;
  }

  @Override
  public ImageFile brighten(int value) {
    try {
      output.append("did brighten with value " + value);
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }
    return null;
  }

  @Override
  public ImageFile darken(int value) {
    try {
      output.append("did darken with value " + value);
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }
    return null;
  }

  @Override
  public ImageFile greyscale(IChannelOperator operator) throws IllegalArgumentException {
    try {
      output.append("did greyscale with operator " + operator);
    } catch (IOException e) {
      throw new RuntimeException("can't write");
    }
    return null;
  }

  @Override
  public ImageFile copyImage() {
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
