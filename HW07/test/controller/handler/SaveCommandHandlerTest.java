package controller.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link SaveCommandHandler}.
 */
public class SaveCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("save", new SaveCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("save [image-path] [image-name]",
        new SaveCommandHandler().getHelpText());
  }
}