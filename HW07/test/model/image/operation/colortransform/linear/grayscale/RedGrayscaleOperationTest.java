package model.image.operation.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link RedGrayscaleOperation}.
 */
public class RedGrayscaleOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new RedGrayscaleOperation().apply(img);

    assertEquals(new RGBPixel(1, 1, 1), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(48, 48, 48), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(200, 200, 200), newImage.getPixel(1, 1));
  }
}