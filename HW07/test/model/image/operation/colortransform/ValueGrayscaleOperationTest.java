package model.image.operation.colortransform;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link ValueGrayscaleOperation}.
 */
public class ValueGrayscaleOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new ValueGrayscaleOperation().apply(img);

    assertEquals(new RGBPixel(3, 3, 3), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(255, 255, 255), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(231, 231, 231), newImage.getPixel(1, 1));
  }
}