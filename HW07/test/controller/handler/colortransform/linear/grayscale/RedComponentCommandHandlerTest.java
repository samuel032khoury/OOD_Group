package controller.handler.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link RedComponentCommandHandler}.
 */
public class RedComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("red-component", new RedComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("red-component [image-name] [dest-image-name]",
        new RedComponentCommandHandler().getHelpText());
  }
}