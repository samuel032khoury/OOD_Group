package model.operation.color;

import model.operation.opertor.filter.IFilterOperator;
import model.operation.opertor.filter.SimpleFilterOperator;

/**
 * A concrete class of AFilterOperation, documented several available filters.
 * This will be operated on ImageFile to get a filtered version of images.
 */
//TODO
public class FilterOperation extends AFilterOperation {

  //TODO
  public FilterOperation(IFilterOperator filter) {
    super(filter);
    supportedFilter.put(SimpleFilterOperator.Blur, OperationUtil::filtering);
    supportedFilter.put(SimpleFilterOperator.Sharpening, OperationUtil::filtering);
  }
}
