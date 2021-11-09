package model.operation.color;

import java.awt.*;

import model.operation.ANoAlphaOperation;
import model.operation.opertor.filter.IFilterOperator;
import model.operation.opertor.filter.SimpleFilterOperator;

public class FilterOperation extends AFilterOperation {
  public FilterOperation(IFilterOperator filter) {
    super(filter);
    supportedFilter.put(SimpleFilterOperator.Blur, OperationUtil::filtering);
    supportedFilter.put(SimpleFilterOperator.Sharpening, OperationUtil::filtering);
  }
}
