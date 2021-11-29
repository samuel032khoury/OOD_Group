package model.image.pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link RGBPixel}.
 */
public class RGBPixelTest {

  private Pixel pixel1;
  private Pixel pixelBlack;
  private Pixel pixelWhite;

  @Before
  public void setUp() {
    pixel1 = new RGBPixel(100, 3, 212);
    pixelBlack = new RGBPixel(0, 0, 0);
    pixelWhite = new RGBPixel(255, 255, 255);
  }

  /**
   * Helps test constructor exceptions. Attempts to create an RGB pixel with the supplied arguments,
   * and returns true if exception is thrown (false otherwise).
   *
   * @param red   the red channel value
   * @param green the green channel value
   * @param blue  the blue channel value
   * @return true if IllegalArgumentException is thrown inside, false otherwise
   */
  private boolean constructorExceptionHelper(int red, int green, int blue) {
    try {
      new RGBPixel(red, green, blue);
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  /**
   * Tests that the constructor throws an IllegalArgumentException if the channel arguments are out
   * of bounds. Note that we do not need to test for null arguments, as java would not simply allow
   * for null to be passed in place of {@code int}.
   */
  @Test
  public void testConstructorInvalidValues() {
    // red channel too small
    assertTrue(constructorExceptionHelper(-1, 150, 150));
    assertTrue(constructorExceptionHelper(-60, 0, 255));

    // green channel too small
    assertTrue(constructorExceptionHelper(140, -4, 40));
    assertTrue(constructorExceptionHelper(130, -800, 160));

    // blue channel too small
    assertTrue(constructorExceptionHelper(120, 37, -2));
    assertTrue(constructorExceptionHelper(10, 150, -3));

    // red channel too large
    assertTrue(constructorExceptionHelper(256, 150, 150));
    assertTrue(constructorExceptionHelper(800, 0, 255));

    // green channel too large
    assertTrue(constructorExceptionHelper(140, 257, 40));
    assertTrue(constructorExceptionHelper(130, 400, 160));

    // blue channel too large
    assertTrue(constructorExceptionHelper(120, 37, 300));
    assertTrue(constructorExceptionHelper(10, 150, 256));

    // mixed
    assertTrue(constructorExceptionHelper(-1, -1, -1));
    assertTrue(constructorExceptionHelper(256, 256, 256));
    assertTrue(constructorExceptionHelper(-50, 40, 350));
    assertTrue(constructorExceptionHelper(-100, -100, 300));
  }

  // this test, in combination with setUp, tests not only getters, but also
  // that the constructor works properly
  @Test
  public void gettersTest() {
    assertEquals(100, pixel1.getRed());
    assertEquals(3, pixel1.getGreen());
    assertEquals(212, pixel1.getBlue());

    assertEquals(0, pixelBlack.getRed());
    assertEquals(0, pixelBlack.getGreen());
    assertEquals(0, pixelBlack.getBlue());

    assertEquals(255, pixelWhite.getRed());
    assertEquals(255, pixelWhite.getGreen());
    assertEquals(255, pixelWhite.getBlue());
  }

  @Test
  public void copy() {
    Pixel pixel1Copy = pixel1.copy();

    assertEquals(pixel1Copy, pixel1); // different objects, but should equal

    assertEquals(100, pixel1Copy.getRed());
    assertEquals(3, pixel1Copy.getGreen());
    assertEquals(212, pixel1Copy.getBlue());

    // setting one should not modify another
    assertNotEquals(pixel1.setRed(240), pixel1Copy.getRed());
    assertNotEquals(pixel1.setGreen(50), pixel1Copy.getGreen());
    assertNotEquals(pixel1.setBlue(3), pixel1Copy.getBlue());

    Pixel pixelWhiteCopy = pixelWhite.copy();

    assertEquals(pixelWhiteCopy, pixelWhite); // different objects, but should equal

    assertEquals(255, pixelWhiteCopy.getRed());
    assertEquals(255, pixelWhiteCopy.getGreen());
    assertEquals(255, pixelWhiteCopy.getBlue());

    // setting one should not modify another
    assertNotEquals(pixelWhite.setRed(100), pixelWhiteCopy.getRed());
    assertNotEquals(pixelWhite.setGreen(50), pixelWhiteCopy.getGreen());
    assertNotEquals(pixelWhite.setBlue(3), pixelWhiteCopy.getBlue());
  }

  @Test
  public void setRed() {
    pixel1.setRed(100); // valid set
    assertEquals(100, pixel1.getRed());

    pixel1.setRed(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixel1.getRed());

    pixel1.setRed(33); // another valid set
    assertEquals(33, pixel1.getRed());

    pixel1.setRed(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixel1.getRed());

    pixel1.setRed(0); // another valid set
    assertEquals(0, pixel1.getRed());

    pixel1.setRed(255); // another valid set
    assertEquals(255, pixel1.getRed());

    // another pixel test

    pixelWhite.setRed(100); // valid set
    assertEquals(100, pixelWhite.getRed());

    pixelWhite.setRed(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixelWhite.getRed());

    pixelWhite.setRed(33); // another valid set
    assertEquals(33, pixelWhite.getRed());

    pixelWhite.setRed(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixelWhite.getRed());

    pixelWhite.setRed(0); // another valid set
    assertEquals(0, pixelWhite.getRed());

    pixelWhite.setRed(255); // another valid set
    assertEquals(255, pixelWhite.getRed());
  }

  @Test
  public void setGreen() {
    pixel1.setGreen(100); // valid set
    assertEquals(100, pixel1.getGreen());

    pixel1.setGreen(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixel1.getGreen());

    pixel1.setGreen(33); // another valid set
    assertEquals(33, pixel1.getGreen());

    pixel1.setGreen(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixel1.getGreen());

    pixel1.setGreen(0); // another valid set
    assertEquals(0, pixel1.getGreen());

    pixel1.setGreen(255); // another valid set
    assertEquals(255, pixel1.getGreen());

    // another pixel test

    pixelWhite.setGreen(100); // valid set
    assertEquals(100, pixelWhite.getGreen());

    pixelWhite.setGreen(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixelWhite.getGreen());

    pixelWhite.setGreen(33); // another valid set
    assertEquals(33, pixelWhite.getGreen());

    pixelWhite.setGreen(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixelWhite.getGreen());

    pixelWhite.setGreen(0); // another valid set
    assertEquals(0, pixelWhite.getGreen());

    pixelWhite.setGreen(255); // another valid set
    assertEquals(255, pixelWhite.getGreen());
  }

  @Test
  public void setBlue() {
    pixel1.setBlue(100); // valid set
    assertEquals(100, pixel1.getBlue());

    pixel1.setBlue(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixel1.getBlue());

    pixel1.setBlue(33); // another valid set
    assertEquals(33, pixel1.getBlue());

    pixel1.setBlue(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixel1.getBlue());

    pixel1.setBlue(0); // another valid set
    assertEquals(0, pixel1.getBlue());

    pixel1.setBlue(255); // another valid set
    assertEquals(255, pixel1.getBlue());

    // another pixel test

    pixelWhite.setBlue(100); // valid set
    assertEquals(100, pixelWhite.getBlue());

    pixelWhite.setBlue(-50); // should set it to MIN_VALUE (0)
    assertEquals(0, pixelWhite.getBlue());

    pixelWhite.setBlue(33); // another valid set
    assertEquals(33, pixelWhite.getBlue());

    pixelWhite.setBlue(350); // should set it to MAX_VALUE (255)
    assertEquals(255, pixelWhite.getBlue());

    pixelWhite.setBlue(0); // another valid set
    assertEquals(0, pixelWhite.getBlue());

    pixelWhite.setBlue(255); // another valid set
    assertEquals(255, pixelWhite.getBlue());
  }

  @Test
  public void testEquality() {
    assertNotEquals(pixel1, pixelWhite);
    assertNotEquals(pixel1, pixelBlack);
    assertNotEquals(pixelWhite, pixel1);

    assertEquals(pixel1, pixel1);
    assertEquals(pixelWhite, pixelWhite);
    assertEquals(pixelBlack, pixelBlack);

    assertEquals(new RGBPixel(100, 3, 212), pixel1);
    assertEquals(new RGBPixel(0, 0, 0), pixelBlack);
    assertEquals(new RGBPixel(255, 255, 255), pixelWhite);

    assertEquals(pixel1, new RGBPixel(100, 3, 212));
    assertEquals(pixelBlack, new RGBPixel(0, 0, 0));
    assertEquals(pixelWhite, new RGBPixel(255, 255, 255));

    assertNotEquals(pixel1, null);
    assertNotEquals(pixelBlack, null);
    assertNotEquals(null, pixelBlack);
    assertNotEquals(null, pixelWhite);

    // different types should return false
    assertNotEquals(pixel1, 1);
    assertNotEquals("hello", pixelWhite);
  }

  @Test
  public void testHashCode() {
    assertNotEquals(pixel1.hashCode(), pixelWhite.hashCode());
    assertEquals(pixelWhite.hashCode(), pixelWhite.hashCode());
    assertEquals(pixel1.hashCode(), new RGBPixel(100, 3, 212).hashCode());
    assertEquals(pixelBlack.hashCode(), new RGBPixel(0, 0, 0).hashCode());
  }
}