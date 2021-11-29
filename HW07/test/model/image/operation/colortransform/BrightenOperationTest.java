package model.image.operation.colortransform;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link BrightenOperation}.
 */
public class BrightenOperationTest extends AbstractModelTest {

  @Test
  public void testBrightenOperation() {
    Image newImage = new BrightenOperation(40).apply(img);

    assertEquals(new RGBPixel(41, 42, 43), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(140, 140, 140), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(88, 255, 90), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(240, 255, 40), newImage.getPixel(1, 1));
  }

  @Test
  public void testDarkenOperation() {
    Image newImage = new BrightenOperation(-30).apply(img);

    assertEquals(new RGBPixel(0, 0, 0), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(70, 70, 70), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(18, 225, 20), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(170, 201, 0), newImage.getPixel(1, 1));
  }
}