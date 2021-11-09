package model.operation.color;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.function.IFilterFunction;
import model.operation.opertor.filter.IFilterOperator;

public class AFilterOperation extends ANoAlphaOperation {
  protected final IFilterOperator filter;
  protected Map<IFilterOperator, IFilterFunction> supportedFilter;

  public AFilterOperation(IFilterOperator filter) {
    this.filter = filter;
    supportedFilter =  new HashMap<>();
  }

  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] filtered = new Color[this.height][this.width];
    if(!this.supportedFilter.containsKey(filter)) {
      throw new IllegalStateException("No such an filter can be found!");
    }
    final IFilterFunction function = this.supportedFilter.get(filter);
    final double[][] kernel = filter.getKernel();

    return null;
  }
}
