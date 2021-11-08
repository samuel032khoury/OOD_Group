package model.operation.visual;

import java.awt.*;

import model.operation.ANoAlphaOperation;

public class CopyImageOperation extends ANoAlphaOperation {
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] copied = new Color[this.height][width];
    for(int i = 0; i < this.height; i ++) {
      for (int j = 0; j < this.width; j++) {
        copied[i][j] = new Color(pixels[i][j].getRGB());
      }
    }
    return copied;
  }
}
