package utils;

import java.io.IOException;

/**
 * An appendable class that has its methods always throw IOException for testing purpose.
 */
public class FakeAppendable implements Appendable {

  /**
   * Throws IOException whenever this method is called for testing purpose.
   *
   * @param csq The character sequence to append.  If {@code csq} is {@code null}, then the four
   *            characters {@code "null"} are appended to this Appendable.
   * @return A reference to this {@code Appendable}
   * @throws IOException Whenever this method is called
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  /**
   * Throws IOException whenever this method is called for testing purpose.
   *
   * @param csq   The character sequence from which a subsequence will be appended.  If {@code csq}
   *              is {@code null}, then characters will be appended as if {@code csq} contained the
   *              four characters {@code "null"}.
   * @param start The index of the first character in the subsequence
   * @param end   The index of the character following the last character in the subsequence
   * @return A reference to this {@code Appendable}
   * @throws IndexOutOfBoundsException If {@code start} or {@code end} are negative, {@code start}
   *                                   is greater than {@code end}, or {@code end} is greater than
   *                                   {@code csq.length()}
   * @throws IOException               Whenever this method is called
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  /**
   * Throws IOException whenever this method is called for testing purpose.
   *
   * @param c The character to append
   * @return A reference to this {@code Appendable}
   * @throws IOException Whenever this method is called
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
