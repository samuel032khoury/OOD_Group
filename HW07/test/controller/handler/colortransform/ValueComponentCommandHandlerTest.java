package controller.handler.colortransform;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link ValueComponentCommandHandler}.
 */
public class ValueComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("value-component", new ValueComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("value-component [image-name] [dest-image-name]",
        new ValueComponentCommandHandler().getHelpText());
  }
}