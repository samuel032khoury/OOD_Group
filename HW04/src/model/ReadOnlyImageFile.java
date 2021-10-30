package model;

public interface ReadOnlyImageFile {
  int getHeight();
  int getWidth();
  ReadOnlyImageFile copyReadOnlyImageFile();
}
