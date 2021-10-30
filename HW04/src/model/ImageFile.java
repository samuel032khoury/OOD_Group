package model;

public interface ImageFile extends ReadOnlyImageFile{
  public ImageFile vertiFlip();
  public ImageFile horizFlip() ;
  public ImageFile brighten(int value);
  public ImageFile darken(int value);
  public ImageFile greyscale(PixelChannel pixelChannel);
  public ReadOnlyImageFile copy();
}
