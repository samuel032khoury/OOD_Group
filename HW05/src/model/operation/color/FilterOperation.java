package model.operation.color;


import model.imagefile.ImageFile;
import model.operation.OperationUtil;
import model.operation.opertor.filter.IFilterOperator;
import model.operation.opertor.filter.SimpleFilterOperator;

/**
 * An implementation of {@link AFilterOperation}, with several available filters added in the {@link
 * #supportedFilter}, which can be applied to an {@link ImageFile} to get a filtered version of
 * images.
 */
public class FilterOperation extends AFilterOperation {

  /**
   * To construct a {@link FilterOperation}, which adds available filters to the {@link
   * #supportedFilter}.
   *
   * @param filter the demanded operator for {@link #process} to apply on a {@link ImageFile}.
   */
  public FilterOperation(IFilterOperator filter) {
    super(filter);
    supportedFilter.put(SimpleFilterOperator.Blur, OperationUtil::filtering);
    supportedFilter.put(SimpleFilterOperator.Sharpening, OperationUtil::filtering);
  }
}
