package controller.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An abstract manager that manage the provision of loader/writer, based on the suffix supplied.
 * @param <T>
 */
public abstract class AManager<T> implements IManager<T> {
  Map<String, Supplier<T>> availableLoaders = new HashMap<>();

  /**
   * Find the correct supplier for loader/writer based on the suffix.
   * @param suffix the suffix to found.
   * @return a loader/writer according to the suffix
   * @throws IllegalStateException if the suffix cannot be found in the hashmap.
   */
  @Override
  public T provide(String suffix) throws IllegalStateException {
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