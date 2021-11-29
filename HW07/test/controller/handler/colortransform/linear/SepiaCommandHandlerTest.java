package controller.handler.colortransform.linear;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link SepiaCommandHandler}.
 */
public class SepiaCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("sepia", new SepiaCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("sepia [image-name] [dest-image-name]",
        new SepiaCommandHandler().getHelpText());
  }
}