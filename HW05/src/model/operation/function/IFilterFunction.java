package model.operation.function;

import java.awt.Color;

public interface IFilterFunction {
  public Color[][] apply(Color[][] original, double[][] kernel);
}
