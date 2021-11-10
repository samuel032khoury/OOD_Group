package controller.utils;

/**
 * An interface for providing appropriate objected {@code T} based on an input/key word.
 *
 * @param <T> The type of object to manage.
 */
public interface IManager<T> {
  /**
   * Provide a {@code T} based on the input.
   *
   * @param input the key word to search for a needed object being provided
   * @return an object being provided
   * @throws IllegalStateException if the search failed, meaning the corresponding object cannot be
   *                               found
   */
  T provide(String input);
}
