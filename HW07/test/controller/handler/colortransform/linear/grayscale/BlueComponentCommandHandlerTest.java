package controller.handler.colortransform.linear.grayscale;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Contains all the test that pertain to the {@link BlueComponentCommandHandler}.
 */
public class BlueComponentCommandHandlerTest {

  @Test
  public void testGetCommandName() {
    assertEquals("blue-component", new BlueComponentCommandHandler().getCommandName());
  }

  @Test
  public void testGetHelpText() {
    assertEquals("blue-component [image-name] [dest-image-name]",
        new BlueComponentCommandHandler().getHelpText());
  }
}