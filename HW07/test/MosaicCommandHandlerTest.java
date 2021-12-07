import org.junit.Test;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import controller.handler.colortransform.MosaicCommandHandler;
import model.image.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * To test if the mosaic command handler works correctly.
 */
public class MosaicCommandHandlerTest {




  private static final MosaicCommandHandler HANDLER = new MosaicCommandHandler();

  @Test
  public void testGetCommandName() {
    assertEquals("mosaic", new MosaicCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("mosaic [image-name] [dest-image-name] [seed]",
            new MosaicCommandHandler().getHelpText());
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
      HANDLER.process(args, (str) -> ImageProcessCommandHandlerTest.SAMPLE_IMAGE, (str, img) -> {
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
      HANDLER.process(args, (str) -> ImageProcessCommandHandlerTest.SAMPLE_IMAGE, (str, img) -> {
      });
      return false;
    } catch (IllegalArgumentException e) {
      return e.getMessage().equalsIgnoreCase("Image names cannot be empty");
    }
  }

  @Test
  public void testProcess() {
    List<String> args = List.of("image-one", "image-two", "1");

    StringBuilder output = new StringBuilder();
    Function<String, Image> imageGetter = (imgName) -> {
      output.append("Getting ").append(imgName).append("\n");
      return ImageProcessCommandHandlerTest.SAMPLE_IMAGE;
    };
    BiConsumer<String, Image> imageSetter = (imgName, img) ->
            output.append("Setting ").append(imgName).append(" to img");

    HANDLER.process(args, imageGetter, imageSetter);

    String expected = "Getting image-one\nSetting image-two to img";
    assertEquals(expected, output.toString());
  }


}