package controller.handler.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link LumaComponentCommandHandler}.
 */
public class LumaComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("luma-component", new LumaComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("luma-component [image-name] [dest-image-name]",
        new LumaComponentCommandHandler().getHelpText());
  }
}