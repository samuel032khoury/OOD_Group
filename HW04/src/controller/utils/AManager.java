package controller.utils;

import java.util.Map;
import java.util.function.Supplier;

public class AManager<T> implements IManager<T>{
  Map<String, Supplier<T>> availableLoaders;

  @Override
  public T provide(String suffix) {
    T util = null;
    Supplier<T> sup = this.availableLoaders.getOrDefault(suffix, null);
    if (sup != null) {
      util = sup.get();
    }
    if (util == null) {
      throw new UnsupportedOperationException("this file extension is not supported");
    }
    return util;
  }

}
