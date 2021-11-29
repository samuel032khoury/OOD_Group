package controller.handler.colortransform;

import static controller.handler.ImageProcessCommandHandlerTest.SAMPLE_IMAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link BrightenCommandHandler}.
 */
public class BrightenCommandHandlerTest {

  private static final BrightenCommandHandler HANDLER = new BrightenCommandHandler();

  @Test
  public void testGetCommandName() {
    assertEquals("brighten", new BrightenCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("brighten [image-name] [dest-image-name] [increment]",
        new BrightenCommandHandler().getHelpText());
  }

  @Test
  public void testBrightenAmountValidation() {
    // less than -255 or higher than 255 is invalid
    assertTrue(itInvalidatesBrightnessAmount(-256));
    assertTrue(itInvalidatesBrightnessAmount(-255));
    assertTrue(itInvalidatesBrightnessAmount(255));
    assertTrue(itInvalidatesBrightnessAmount(256));
    // Within -255 and 255 is valid
    assertFalse(itInvalidatesBrightnessAmount(-254));
    assertFalse(itInvalidatesBrightnessAmount(-100));
    assertFalse(itInvalidatesBrightnessAmount(-1));
    assertFalse(itInvalidatesBrightnessAmount(55));
    assertFalse(itInvalidatesBrightnessAmount(254));
    assertFalse(itInvalidatesBrightnessAmount(1));
  }

  private boolean itInvalidatesBrightnessAmount(int brightness) {
    try {
      HANDLER.process(
          List.of("image", "image-2", String.valueOf(brightness)),
          (str) -> SAMPLE_IMAGE,
          (str, img) -> {
          }
      );
      return false;
    } catch (IllegalArgumentException e) {
      return e.getMessage().contains(
          "Can not brighten/darken an image by a number outside the valid RGB range [-255, 255]"
      );
    }
  }

  @Test
  public void testProcessArgumentsSize() {
    // 0 args, 1 arg, 2 args, lots of args
    assertTrue(itInvalidatesArgumentSizes(List.of("one-arg")));
    assertTrue(itInvalidatesArgumentSizes(List.of("two", "arg")));
    assertTrue(itInvalidatesArgumentSizes(List.of("too", "many", "dang", "args")));
    assertTrue(itInvalidatesArgumentSizes(List.of()));
    // Happy path
    assertFalse(itInvalidatesArgumentSizes(List.of("image", "image", "5")));
  }

  private boolean itInvalidatesArgumentSizes(List<String> args) {
    try {
      HANDLER.process(args, (str) -> SAMPLE_IMAGE, (str, img) -> {
      });
      return false;
    } catch (IllegalArgumentException e) {
      return e.getMessage().contains("Expected 3 arguments, received ");
    }
  }

  @Test
  public void testEmptyImageNames() {
    assertTrue(itInvalidatesEmptyImageNames(List.of("", "not-empty", "5")));
    assertTrue(itInvalidatesEmptyImageNames(List.of("", "", "5")));
    assertTrue(itInvalidatesEmptyImageNames(List.of("not-empty", "", "5")));
    // Happy path
    assertFalse(itInvalidatesArgumentSizes(List.of("image", "image", "5")));
  }

  private boolean itInvalidatesEmptyImageNames(List<String> args) {
    try {
      HANDLER.process(args, (str) -> SAMPLE_IMAGE, (str, img) -> {
      });
      return false;
    } catch (IllegalArgumentException e) {
      return e.getMessage().equalsIgnoreCase("Image names cannot be empty");
    }
  }


  @Test
  public void testProcess() {
    List<String> args = List.of("image-one", "image-two", "5");

    StringBuilder output = new StringBuilder();
    Function<String, Image> imageGetter = (imgName) -> {
      output.append("Getting ").append(imgName).append("\n");
      return SAMPLE_IMAGE;
    };
    BiConsumer<String, Image> imageSetter = (imgName, img) ->
        output.append("Setting ").append(imgName).append(" to img");

    HANDLER.process(args, imageGetter, imageSetter);

    String expected = "Getting image-one\nSetting image-two to img";
    assertEquals(expected, output.toString());
  }
}