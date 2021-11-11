package model.operation.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.operation.ANoAlphaOperation;
import model.operation.function.IFilterFunction;
import model.operation.opertor.filter.IFilterOperator;

/**
 * An abstract class that has the shared method to process the filtering operations. A map has keys
 * of {@link IFilterFunction} is provided to retrieve appropriate {@link IFilterFunction} applied to
 * the image.
 */
public abstract class AFilterOperation extends ANoAlphaOperation {
  protected final IFilterOperator filter;
  protected final Map<IFilterOperator, IFilterFunction> supportedFilter;

  /**
   * To construct a {@link AFilterOperation} with the {@link IFilterOperator} that saves the filter
   * kernel (as a matrix).
   *
   * @param filter the {@link IFilterOperator} the filter kernel (as a matrix).
   */
  public AFilterOperation(IFilterOperator filter) {
    this.filter = filter;
    supportedFilter = new HashMap<>();
  }

  /**
   * Perform filtering operations on the given {@code pixels}.
   * the rule of filtering is provided by the {@link IFilterFunction},
   * delivered from the {@link #supportedFilter} by searching for the
   * {@link IFilterOperator}
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image being processed
   * @return a filtered 2-D {@code Array} of {@link Color} representing an image, by the
   *         filtering rule provided by {@link IFilterFunction}.
   */
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
