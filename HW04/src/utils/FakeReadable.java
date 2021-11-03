package utils;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * A readable class that has its methods always throw IOException for testing purpose.
 */
public class FakeReadable implements Readable {

  /**
   * Throws IOException whenever this method is called for testing purpose.
   *
   * @param cb the buffer to read characters into
   * @return The number of char values added to the buffer, or -1 if this source of characters is at
   *               its end
   * @throws IOException Whenever this method is called
   */
  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
