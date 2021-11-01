package controller.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AManager<T> implements IManager<T>{
  Map<String, Supplier<T>> availableLoaders = new HashMap<>();

  @Override
  public T provide(String suffix) throws IllegalStateException{
    T util = null;
    Supplier<T> sup = this.availableLoaders.getOrDefault(suffix, null);
    if (sup != null) {
      util = sup.get();
    }
    if (util == null) {
      throw new IllegalStateException("this file extension is not supported");
    }
    return util;
  }
}