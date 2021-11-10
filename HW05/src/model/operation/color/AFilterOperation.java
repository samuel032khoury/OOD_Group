package model.operation.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.function.IFilterFunction;
import model.operation.opertor.filter.IFilterOperator;

//TODO
public class AFilterOperation extends ANoAlphaOperation {
  protected final IFilterOperator filter;
  protected final Map<IFilterOperator, IFilterFunction> supportedFilter;

  //TODO
  public AFilterOperation(IFilterOperator filter) {
    this.filter = filter;
    supportedFilter = new HashMap<>();
  }

  //TODO
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] filtered = new Color[this.height][this.width];
    if (!this.supportedFilter.containsKey(filter)) {
      throw new IllegalStateException("No such an filter can be found!");
    }
    final IFilterFunction function = this.supportedFilter.get(filter);
    //The shape of the kernel has been restricted when it's constructed in enum class
    final double[][] kernel = filter.getKernel();
    return function.apply(pixels, kernel);
  }
}
