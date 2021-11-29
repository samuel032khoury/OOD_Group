package model.image.operation.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link BlueGrayscaleOperation}.
 */
public class BlueGrayscaleOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new BlueGrayscaleOperation().apply(img);

    assertEquals(new RGBPixel(3, 3, 3), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(50, 50, 50), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(0, 0, 0), newImage.getPixel(1, 1));
  }
}