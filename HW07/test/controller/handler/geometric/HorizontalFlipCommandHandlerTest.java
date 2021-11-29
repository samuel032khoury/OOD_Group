package controller.handler.geometric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link HorizontalFlipCommandHandler}.
 */
public class HorizontalFlipCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("horizontal-flip", new HorizontalFlipCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("horizontal-flip [image-name] [dest-image-name]",
        new HorizontalFlipCommandHandler().getHelpText());
  }
}