package model.operation.color;

import model.operation.opertor.filter.IFilterOperator;
import model.operation.opertor.filter.SimpleFilterOperator;

public class FilterOperation extends AFilterOperation {
  public FilterOperation(IFilterOperator filter) {
    super(filter);
    supportedFilter.put(SimpleFilterOperator.Blur, OperationUtil::filtering);
    supportedFilter.put(SimpleFilterOperator.Sharpening, OperationUtil::filtering);
  }
}
