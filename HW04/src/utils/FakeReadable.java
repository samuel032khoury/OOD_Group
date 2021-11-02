package utils;

import java.io.IOException;
import java.nio.CharBuffer;

public class FakeReadable implements Readable {
  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
