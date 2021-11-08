package model.operation.color;

import java.awt.*;

import model.operation.ANoAlphaOperation;

public class FilterOperation extends ANoAlphaOperation {
  @Override
  protected Color[][] process(Color[][] pixels) {
    return new Color[0][];
  }
}
