package model.image.operation.colortransform.linear;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the
 * {@link model.image.operation.colortransform.linear.SepiaOperation}.
 */
public class SepiaOperationTest extends AbstractModelTest {

  @Test
  public void testSepiaOperation() {
    Image newImage = new SepiaOperation().apply(img);

    assertEquals(new RGBPixel(2, 2, 2), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(135, 120, 94), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(224, 200, 156), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(255, 228, 178), newImage.getPixel(1, 1));
  }
}