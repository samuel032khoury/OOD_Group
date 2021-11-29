package model.image.operation.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link IntensityGrayscaleOperation}.
 */
public class IntensityGrayscaleOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new IntensityGrayscaleOperation().apply(img);

    assertEquals(new RGBPixel(2, 2, 2), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(100, 100, 100), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(118, 118, 118), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(144, 144, 144), newImage.getPixel(1, 1));
  }
}