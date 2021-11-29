package model.image.operation.geometric;

import static org.junit.Assert.assertEquals;

import model.AbstractModelTest;
import model.image.Image;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link VerticalFlipOperation}.
 */
public class VerticalFlipOperationTest extends AbstractModelTest {

  @Test
  public void testOperation() {
    Image newImage = new VerticalFlipOperation().apply(img);

    assertEquals(img.getPixel(1, 0), newImage.getPixel(0, 0));
    assertEquals(img.getPixel(1, 1), newImage.getPixel(0, 1));
    assertEquals(img.getPixel(0, 0), newImage.getPixel(1, 0));
    assertEquals(img.getPixel(0, 1), newImage.getPixel(1, 1));
  }
}