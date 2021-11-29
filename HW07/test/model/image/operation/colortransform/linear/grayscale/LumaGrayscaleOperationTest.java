package model.image.operation.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link LumaGrayscaleOperation}.
 */
public class LumaGrayscaleOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new LumaGrayscaleOperation().apply(img);

    assertEquals(new RGBPixel(2, 2, 2), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(196, 196, 196), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(208, 208, 208), newImage.getPixel(1, 1));
  }
}