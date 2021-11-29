package model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.AbstractModelTest;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the tests that pertain to RGBImage.
 */
public class RGBImageTest extends AbstractModelTest {

  @Test(expected = IllegalArgumentException.class)
  public void testCreateRGBImageNullData() {
    new RGBImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateRGBImageEmptyHeightData() {
    new RGBImage(new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateRGBImageEmptyWidthData() {
    new RGBImage(List.of(new ArrayList<>()));
  }

  @Test
  public void copy() {
    Image newImage = img.copy();

    assertEquals(newImage.getImageData(), img.getImageData());

    // we set the pixel so that it doesn't equal the original one (1, 2, 3)
    newImage.setPixel(0, 0, new RGBPixel(255, 255, 255));
    assertNotEquals(newImage.getImageData(), img.getImageData());
  }

  @Test
  public void getImageData() {
    // this test, combined with the setUp and getPixel tests, verifies that the image is constructed
    // correctly when supplied valid arguments
    List<List<Pixel>> data = Arrays.asList(
        Arrays.asList(new RGBPixel(1, 2, 3), new RGBPixel(100, 100, 100)),
        Arrays.asList(new RGBPixel(48, 255, 50), new RGBPixel(200, 231, 0))
    );

    assertEquals(data, img.getImageData());
  }

  /**
   * This test verifies that a user would not be able to directly modify data field of an image
   * using either {@code Image.getPixel()} or {@code Image.getImageData()} functions.
   */
  @Test
  public void cannotSetDataDirectly() {
    List<List<Pixel>> imgData = img.getImageData();

    // we make a copy of the image data
    List<List<Pixel>> imgDataCopy = new ArrayList<>();
    for (List<Pixel> row : imgData) {
      List<Pixel> rowCopy = new ArrayList<>();
      for (Pixel pixel : row) {
        rowCopy.add(pixel.copy());
      }

      imgDataCopy.add(rowCopy);
    }

    imgData = new ArrayList<>(); // try to reset the source data directly

    assertNotEquals(imgDataCopy, imgData); // the reset data should not equal
    assertEquals(imgDataCopy, img.getImageData()); // the img.data should still be the same

    // now we attempt to do the same thing, but with a specific pixel

    Pixel pixel = img.getPixel(0, 0);
    Pixel pixelCopy = pixel.copy();

    // different value, we know that the actual pixel value is (1, 2, 3) in the test setup
    pixel = new RGBPixel(255, 255, 255);

    assertNotEquals(pixel, pixelCopy);
    assertEquals(pixelCopy, img.getPixel(0, 0));
  }

  @Test
  public void getWidth() {
    assertEquals(2, img.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(2, img.getHeight());
  }

  @Test
  public void getPixel() {
    assertEquals(new RGBPixel(1, 2, 3), img.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), img.getPixel(0, 1));
    assertEquals(new RGBPixel(48, 255, 50), img.getPixel(1, 0));
    assertEquals(new RGBPixel(200, 231, 0), img.getPixel(1, 1));
  }

  /**
   * A helper method designed to test an IndexOutOfBounds exception that should be thrown when
   * attempting to get a pixel with location that is out of bounds.
   *
   * @param row the row position for the invalid pixel
   * @param col the col position for the invalid pixel
   * @return true if the exception was thrown when trying to get the pixel, false otherwise
   */
  private boolean testGetPixelExceptionHelper(int row, int col) {
    try {
      img.getPixel(row, col);
      return false;
    } catch (IndexOutOfBoundsException e) {
      return true;
    }
  }

  /**
   * Tests that an exception will be thrown when trying to get an invalid pixel location.
   */
  @Test
  public void getInvalidPixel() {
    // check negatives first
    assertTrue(testGetPixelExceptionHelper(-1, 0));
    assertTrue(testGetPixelExceptionHelper(-100, 1));
    assertTrue(testGetPixelExceptionHelper(0, -1));
    assertTrue(testGetPixelExceptionHelper(1, -100));
    assertTrue(testGetPixelExceptionHelper(-1, -1));
    assertTrue(testGetPixelExceptionHelper(-50, -55));

    // check indices too large
    assertTrue(testGetPixelExceptionHelper(0, 2)); // col equals the size
    assertTrue(testGetPixelExceptionHelper(1, 254));
    assertTrue(testGetPixelExceptionHelper(2, 0)); // row equals the size
    assertTrue(testGetPixelExceptionHelper(243, 1));
    assertTrue(testGetPixelExceptionHelper(100, 4));
    assertTrue(testGetPixelExceptionHelper(5, 110));
  }

  @Test
  public void setPixel() {
    assertEquals(new RGBPixel(1, 2, 3), img.getPixel(0, 0));
    img.setPixel(0, 0, new RGBPixel(10, 10, 10));
    assertEquals(new RGBPixel(10, 10, 10), img.getPixel(0, 0));

    assertEquals(new RGBPixel(100, 100, 100), img.getPixel(0, 1));
    img.setPixel(0, 1, new RGBPixel(100, 0, 55));
    assertEquals(new RGBPixel(100, 0, 55), img.getPixel(0, 1));

    assertEquals(new RGBPixel(48, 255, 50), img.getPixel(1, 0));
    img.setPixel(1, 0, new RGBPixel(80, 255, 31));
    assertEquals(new RGBPixel(80, 255, 31), img.getPixel(1, 0));

    assertEquals(new RGBPixel(200, 231, 0), img.getPixel(1, 1));
    img.setPixel(1, 1, new RGBPixel(255, 254, 253));
    assertEquals(new RGBPixel(255, 254, 253), img.getPixel(1, 1));
  }

  /**
   * A helper method designed to test an IndexOutOfBounds exception that should be thrown when
   * attempting to set a pixel with location that is out of bounds.
   *
   * @param row the row position for the invalid pixel
   * @param col the col position for the invalid pixel
   * @return true if the exception was thrown when trying to set the pixel, false otherwise
   */
  private boolean testSetPixelExceptionHelper(int row, int col) {
    try {
      img.setPixel(row, col, new RGBPixel(0, 0, 0));
      return false;
    } catch (IndexOutOfBoundsException e) {
      return true;
    }
  }

  /**
   * Tests that an exception will be thrown when trying to set to an invalid pixel location.
   */
  @Test
  public void setInvalidPixel() {
    // check negatives first
    assertTrue(testSetPixelExceptionHelper(-1, 0));
    assertTrue(testSetPixelExceptionHelper(-100, 1));
    assertTrue(testSetPixelExceptionHelper(0, -1));
    assertTrue(testSetPixelExceptionHelper(1, -100));
    assertTrue(testSetPixelExceptionHelper(-1, -1));
    assertTrue(testSetPixelExceptionHelper(-50, -55));

    // check indices too large
    assertTrue(testSetPixelExceptionHelper(0, 2)); // col equals the size
    assertTrue(testSetPixelExceptionHelper(1, 254));
    assertTrue(testSetPixelExceptionHelper(2, 0)); // row equals the size
    assertTrue(testSetPixelExceptionHelper(243, 1));
    assertTrue(testSetPixelExceptionHelper(100, 4));
    assertTrue(testSetPixelExceptionHelper(5, 110));
  }
}