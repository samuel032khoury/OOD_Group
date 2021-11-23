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
  public void testButtonsBrighten() {
    try {
      this.controller.performAction(new ActionEvent(this.controller, 10, "brighten"));
      // please manually choose 10 and press two enters
    } catch (Exception e) {
      assertEquals("brighten#¥#10#¥#null#¥#null-brighten10#¥#", this.output.toString());
    }

  }

  @Test
  public void testButtonsDarken() {
    try {
      this.controller.performAction(new ActionEvent(this.controller, 10, "darken"));
      // please manually choose 10 and press two enters
    } catch (Exception e) {
      assertEquals("darken#¥#10#¥#null#¥#null-darken10#¥#", this.output.toString());
    }
  }

  @Test
  public void testButtonsLoad() {
    try {
      this.controller.performAction(new ActionEvent(this.controller, 10, "load"));
      // please manually /res/elephant.ppm and press one enter
    } catch (Exception e) {
      String[] responseList = this.output.toString().split("#¥#");

      assertEquals("load", responseList[0]);
      assertTrue( responseList[1].endsWith("elephant.ppm"));
      assertEquals("elephant", responseList[2]);
    }
  }

  @Test
  public void testButtonsSave() {
    this.controller.performAction(new ActionEvent(this.controller, 10, "save"));
    // please save at hw6/
    String[] responseList = this.output.toString().split("#¥#");
    assertEquals("save", responseList[0]);
    assertTrue( responseList[1].endsWith("untitled.png"));
    assertEquals("null", responseList[2]);
  }

  @Test
  public void testButtonsArbitrary() {
    try {
      this.controller.performAction(new ActionEvent(this.controller, 10, "anything"));
      // please simply press enter
    } catch (Exception e) {
      assertEquals("anything#¥#null#¥#null-anything#¥#", this.output.toString());
    }
  }
}