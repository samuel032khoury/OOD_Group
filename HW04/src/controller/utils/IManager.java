package controller.utils;

/**
 * An interface for Managing loaders/writers, are needed to provide the correct writer/loader.
 *
 * @param <T> The type of object to manage.
 */
public interface IManager<T> {
  T provide(String suffix);
}
