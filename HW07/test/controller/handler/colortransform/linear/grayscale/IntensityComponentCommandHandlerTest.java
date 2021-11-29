package controller.handler.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import view.ImageProcessorCLI;
import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link ImageProcessorCLI}.
 */
public class IntensityComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("intensity-component", new IntensityComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("intensity-component [image-name] [dest-image-name]",
        new IntensityComponentCommandHandler().getHelpText());
  }
}