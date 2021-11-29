package model.image.operation.colortransform.ranged;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.function.Supplier;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Tests the {@link RangedImageOperation} class.
 */
public class RangedImageOperationTest {

  private static final double[][] SAMPLE_FILTER = new double[][]{
      new double[]{.0625, .125, .0625},
      new double[]{.125, .25, .125},
      new double[]{.0625, .125, .0625}
  };

  private static final double[][] BAD_FILTER = new double[][]{
      new double[]{.0625, .125, .0625},
      new double[]{.125, 1, .125},
      new double[]{.0625, .125, .0625}
  };
  private static final List<Supplier<RangedImageOperation>> BAD_SUPPLIERS = List.of(
      // Empty height
      () -> new RangedImageOperation(new double[][]{}),
      // Empty width, valid height
      () -> new RangedImageOperation(new double[][]{new double[]{.5, .5}}),
      // 2x3
      () -> new RangedImageOperation(
          new double[][]{new double[]{.125, .25, .125}, new double[]{.125, .25, .125}}),
      // 3x2
      () -> new RangedImageOperation(
          new double[][]{
              new double[]{.125, .25},
              new double[]{.125, .25},
              new double[]{0.0, .25}})
  );

  private static final List<List<Pixel>> SAMPLE_PIXELS = List.of(
      List.of(
          new RGBPixel(1, 1, 1),
          new RGBPixel(1, 1, 1),
          new RGBPixel(1, 1, 1),
          new RGBPixel(1, 1, 1),
          new RGBPixel(1, 1, 1)
      )
  );

  @Test
  public void itShouldGetWeight() {
    assertEquals(1.0,
        RangedImageOperation.getTotalWeight(SAMPLE_FILTER),
        0.00001);
    assertNotEquals(1.0, RangedImageOperation.getTotalWeight(BAD_FILTER), 0.00001);
  }

  @Test
  public void itShouldValidateFilterInCunstructor() {
    for (Supplier<RangedImageOperation> supplier : BAD_SUPPLIERS) {
      try {
        supplier.get();
        fail("Should have failed on bad constructor");
      } catch (IllegalArgumentException e) {
        assertTrue(e.getMessage().startsWith("Ranged filters cannot"));
      }
    }
  }
}
