import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import controller.controller.gui.ImageProcessControllerGUI;
import model.library.ImageLibModelImpl;
import utils.MockControllerGUI;
import utils.MockModel;
import view.gui.GUIIViewImpl;

import static org.junit.Assert.*;

/**
 * Due to project structures, please manually run all tests here.
 */
public class GUIIViewImplTest {

  MockControllerGUI controller;
  Appendable output;

  @Before
  public void setUp() throws Exception {
    this.output = new StringBuilder();
    this.controller = new MockControllerGUI(new ImageLibModelImpl(), output);

  }

  @Test
  public void testButtons1() {
    try {
      this.controller.performAction(new ActionEvent(this.controller, 10, "brighten"));
      // please manually choose 10 and press two enters
    } catch (Exception e) {
      assertEquals("brighten 10 null null-brighten10 ", this.output.toString());
    }

  }
}