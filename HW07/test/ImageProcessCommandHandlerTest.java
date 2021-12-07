import static org.junit.Assert.assertTrue;

import controller.handler.ImageProcessCommandHandler;
import controller.handler.LoadCommandHandler;
import controller.handler.SaveCommandHandler;
import controller.handler.colortransform.ValueComponentCommandHandler;
import controller.handler.colortransform.linear.SepiaCommandHandler;
import controller.handler.colortransform.linear.grayscale.BlueComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.GreenComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.IntensityComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.LumaComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.RedComponentCommandHandler;
import controller.handler.geometric.HorizontalFlipCommandHandler;
import controller.handler.geometric.VerticalFlipCommandHandler;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import model.image.Image;
import model.image.RGBImage;
import model.image.pixel.RGBPixel;
import org.junit.Test;

/**
 * Tests a bunch of abstract image process command handlers.
 */
public class ImageProcessCommandHandlerTest {

  public static final RGBImage SAMPLE_IMAGE = new RGBImage(List.of(List.of(new RGBPixel(5, 5, 5))));

  private static final List<Supplier<ImageProcessCommandHandler>> ALL_HANDLERS = List.of(
      BlueComponentCommandHandler::new,
      GreenComponentCommandHandler::new,
      IntensityComponentCommandHandler::new,
      LumaComponentCommandHandler::new,
      RedComponentCommandHandler::new,
      ValueComponentCommandHandler::new,
      HorizontalFlipCommandHandler::new,
      VerticalFlipCommandHandler::new,
      LoadCommandHandler::new,
      SaveCommandHandler::new,
      SepiaCommandHandler::new
  );

  private static final List<Supplier<List<String>>> INVALID_ARGUMENT_SIZE = List.of(
      () -> List.of("lots-of-args", "arg", "arg", "arg", "arg", "arg"),
      () -> List.of("only-one-arg"),
      () -> List.of("blue-component", "too", "many", "args")
  );

  private static final List<Supplier<List<String>>> EMPTY_ARGUMENTS = List.of(
      () -> List.of("", "first-empty"),
      () -> List.of("second-empty", ""),
      () -> List.of("", "")
  );


  @Test
  public void testProcessInvalidArgumentsSize() {
    // 0 args, 1 arg, 3 args, lots of args
    for (Supplier<ImageProcessCommandHandler> handler : ALL_HANDLERS) {
      assertTrue(itShouldTestInvalidArgumentSizes(handler));
    }
  }

  private boolean itShouldTestInvalidArgumentSizes(
      Supplier<ImageProcessCommandHandler> handlerSupplier) {
    ImageProcessCommandHandler handler = handlerSupplier.get();
    boolean allPass = true;

    for (Supplier<List<String>> argSupplier : INVALID_ARGUMENT_SIZE) {
      try {
        handler.process(argSupplier.get(), (str) -> SAMPLE_IMAGE, (str, img) -> {
        });
        allPass = false;
      } catch (IllegalArgumentException e) {
        allPass &= e.getMessage().contains("Expected 2 arguments, received ");
      }
    }

    return allPass;
  }


  @Test
  public void testEmptyArguments() {
    // could probably write a boolean helper method
    // first, second
    for (Supplier<ImageProcessCommandHandler> handler : ALL_HANDLERS) {
      assertTrue(itShouldTestEmptyArguments(handler));
    }
  }

  private boolean itShouldTestEmptyArguments(Supplier<ImageProcessCommandHandler> handlerSupplier) {
    ImageProcessCommandHandler handler = handlerSupplier.get();
    boolean allPass = true;

    for (Supplier<List<String>> argSupplier : EMPTY_ARGUMENTS) {
      try {
        handler.process(argSupplier.get(), (str) -> SAMPLE_IMAGE, (str, img) -> {
        });
        allPass = false;
      } catch (IllegalArgumentException e) {
        allPass &= e.getMessage().equalsIgnoreCase("Image names cannot be empty");
      }
    }

    return allPass;
  }

  @Test
  public void testProcess() {
    // create a mock getImage object-function, and a mock addImage object-function
    // then, when an operation is applied, make sure that it writes it correctly
    // should verify the correct operation is applied by manually applying same
    // thing on the mock image and verifying image sameness
    for (Supplier<ImageProcessCommandHandler> handler : ALL_HANDLERS) {
      assertTrue(itShouldTestProcessWithMocks(handler));
    }
  }

  private boolean itShouldTestProcessWithMocks(
      Supplier<ImageProcessCommandHandler> handlerSupplier) {
    ImageProcessCommandHandler handler = handlerSupplier.get();

    List<String> args = List.of("sample-image", "sample-image");

    StringBuilder output = new StringBuilder();
    Function<String, Image> imageGetter = (imgName) -> {
      output.append("Getting ").append(imgName).append("\n");
      return SAMPLE_IMAGE;
    };
    BiConsumer<String, Image> imageSetter = (imgName, img) ->
        output.append("Setting ").append(imgName).append(" to img");

    handler.process(args, imageGetter, imageSetter);

    String expected = "Getting sample-image\nSetting sample-image to img";
    return expected.equalsIgnoreCase(output.toString());
  }
}
