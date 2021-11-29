package controller.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link CommonImageTransferer}.
 */
public class CommonImageTransfererTest {

  @Ignore
  @Test
  public void load() {
    fail();
  }

  @Ignore
  @Test
  public void export() {
    fail();
  }

  @Test
  public void itShouldParseFormatName() {
    // Supported ones
    assertEquals("jpg", CommonImageTransferer.parseFormatName("test.jpg"));
    assertEquals("png", CommonImageTransferer.parseFormatName("test.png"));
    assertEquals("gif", CommonImageTransferer.parseFormatName("test.gif"));
    assertEquals("jpeg", CommonImageTransferer.parseFormatName("test.jpeg"));
    assertEquals("bmp", CommonImageTransferer.parseFormatName("test.bmp"));
    assertEquals("tiff", CommonImageTransferer.parseFormatName("test.tiff"));
    assertEquals("tif", CommonImageTransferer.parseFormatName("test.tif"));

    try {
      CommonImageTransferer.parseFormatName("test.random");
      fail("Should have thrown parsing test.random");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Failed to parse format name from file path "));
    }

    try {
      CommonImageTransferer.parseFormatName("test.ppm");
      fail("Should have thrown parsing test.ppm");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Failed to parse format name from file path "));
    }

    try {
      CommonImageTransferer.parseFormatName("test");
      fail("Should have thrown parsing test");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Failed to parse format name from file path "));
    }
  }

  @Test
  public void itShouldListFileTypes() {
    CommonImageTransferer newTransferer = new CommonImageTransferer();
    String expected = "- BMP\n" +
        "- GIF\n" +
        "- JPEG\n" +
        "- JPG\n" +
        "- PNG\n" +
        "- TIF\n" +
        "- TIFF\n" +
        "- WBMP";
    assertEquals(expected, newTransferer.getFileTypes());
  }
}