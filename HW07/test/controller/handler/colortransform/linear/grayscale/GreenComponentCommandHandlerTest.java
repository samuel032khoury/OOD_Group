package controller.handler.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link GreenComponentCommandHandler}.
 */
public class GreenComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("green-component", new GreenComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("green-component [image-name] [dest-image-name]",
        new GreenComponentCommandHandler().getHelpText());
  }
}