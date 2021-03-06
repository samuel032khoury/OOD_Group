package controller.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * An abstract manager that manage the provision of loader/writer, based on the suffix supplied.
 *
 * @param <T> The type of object to manage.
 */
public abstract class ASuffixManager<T> implements IManager<T> {
  protected final Map<String, Supplier<T>> availableSuffix = new HashMap<>();

  /**
   * Find the correct supplier for loader/writer based on the suffix.
   *
   * @param suffix the suffix to found.
   * @return a loader/writer according to the suffix
   * @throws IllegalStateException if the suffix cannot be found in the hashmap.
   */
  @Override
  public T provide(String suffix) throws IllegalStateException {
    if (suffix == null) {
      throw new IllegalArgumentException("Suffix is unspecified!");
    }
    T util = null;
    Supplier<T> sup = this.availableSuffix.getOrDefault(suffix, null);
    if (sup != null) {
      util = sup.get();
    }
    if (util == null) {
      throw new IllegalStateException("This file extension is not supported!");
    }
    return util;
  }
}