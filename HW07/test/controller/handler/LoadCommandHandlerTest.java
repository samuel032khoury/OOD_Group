package controller.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link LoadCommandHandler}.
 */
public class LoadCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("load", new LoadCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("load [image-path] [image-name]",
        new LoadCommandHandler().getHelpText());
  }
}