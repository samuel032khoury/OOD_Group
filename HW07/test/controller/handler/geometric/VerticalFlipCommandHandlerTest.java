package controller.handler.geometric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link VerticalFlipCommandHandler}.
 */
public class VerticalFlipCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("vertical-flip", new VerticalFlipCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("vertical-flip [image-name] [dest-image-name]",
        new VerticalFlipCommandHandler().getHelpText());
  }


}