import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.StringReader;

import utils.MockImage;
import utils.MockModel;
import controller.controller.IImageProcessController;
import controller.controller.ImageProcessControllerImpl;
import model.imageFile.ImageFile;
import model.imageFile.ImageFileNoAlpha;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

import static org.junit.Assert.*;

public class ImageProcessControllerImplTest {
  ImageLibModel model;
  ImageFile img;
  IImageProcessView view;
  Appendable out;
  Readable in;

  @Before
  public void setUp() throws Exception {
    model = new ImageLibModelImpl();
    img = new ImageFileNoAlpha(new Color[][]{
            {new Color(10,11,12), new Color(20,21,22), new Color(30,31,32)},
            {new Color(40,41,42), new Color(50,51,52), new Color(60,61,62)}}
    );
    out = new StringBuilder();
    view = new SimpleImageProcessViewImpl(out, model);
    in = new StringReader("");
  }

  @Test
  public void testConstructor() {
    try {
      IImageProcessController controller1 = new ImageProcessControllerImpl();
      IImageProcessController controller2 = new ImageProcessControllerImpl(model);
      IImageProcessController controller3 = new ImageProcessControllerImpl(model, in);
      IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx1() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx2() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx3() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx4() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(model, in, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx5() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(model, null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx6() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null, in, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx7() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null, null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx8() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null, in, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx9() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx10() {
    IImageProcessController controller2 = new ImageProcessControllerImpl(null, null, null);
  }

  @Test
  public void testQuit() {
    in = new StringReader("QUIT");
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> Program is quit.\n", out.toString());
  }

  @Test
  public void testSize() {
    in = new StringReader("SIZE");
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> There are 0 images in the library!\n", out.toString());
  }

  @Test
  public void testSize1() {
    in = new StringReader("SIZE");
    model.loadImage("img1", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> There are 1 images in the library!\n", out.toString());
  }

  @Test
  public void testSizeError() {
    in = new StringReader("SIZE w");
    model.loadImage("img1", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("\u001B[31mSIZE expect no arguments while provide at least one," +
            " try again!\u001B[0m\n", out.toString());
  }

  @Test
  public void testCommandNotFound() {
    in = new StringReader("isurew w");
    model.loadImage("img1", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("\u001B[31mCommand not found!\u001B[0m\n", out.toString());
  }

  @Test
  public void testCommandSendCorrection() {
    in = new StringReader("vertical-flip mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did vertiFlip", output.toString());
  }

  @Test
  public void testCommandSendCorrection1() {
    in = new StringReader("horizontal-flip mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did horizFlip", output.toString());
  }

  @Test
  public void testCommandSendCorrection2() {
    in = new StringReader("brighten 20 mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did brighten with value 20", output.toString());
  }

  @Test
  public void testCommandSendCorrection3() {
    in = new StringReader("darken 20 mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did darken with value 20", output.toString());
  }

  @Test
  public void testCommandSendCorrection4() {
    in = new StringReader("blue-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Blue", output.toString());
  }

  @Test
  public void testCommandSendCorrection5() {
    in = new StringReader("red-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Red", output.toString());
  }

  @Test
  public void testCommandSendCorrection6() {
    in = new StringReader("green-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Green", output.toString());
  }

  @Test
  public void testCommandSendCorrection7() {
    in = new StringReader("luma-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Luma", output.toString());
  }

  @Test
  public void testCommandSendCorrection8() {
    in = new StringReader("value-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Value", output.toString());
  }

  @Test
  public void testCommandSendCorrection9() {
    in = new StringReader("intensity-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Intensity", output.toString());
  }

  @Test
  public void testCommandSendCorrection10() {
    in = new StringReader("load mock mock");
    Appendable output = new StringBuilder();
    ImageLibModel model = new MockModel(output);
    model.loadImage("mock", img);
    IImageProcessController controller
            = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("loaded mock", output.toString());
  }

  @Test
  public void testCommandSendCorrection11() {
    in = new StringReader("save mock mock");
    Appendable output = new StringBuilder();
    ImageLibModel model = new MockModel(output);
    model.loadImage("mock", img);
    IImageProcessController controller
            = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("loaded mockpeek mock", output.toString());
  }

  @Test
  public void testLineMutilCommand() {
    in = new StringReader("value-component mock mocknew & intensity-component mock mocknew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("did greyscale with operator Value" +
            "did greyscale with operator Intensity", output.toString());
  }

  @Test
  public void testGoThrough() {
    in = new StringReader("value-component img imgnew");
    model.loadImage("img", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);

    controller.run();
    assertEquals(new ImageFileNoAlpha(new Color[][]{
            {new Color(12,12,12), new Color(22,22,22), new Color(32,32,32)},
            {new Color(42,42,42), new Color(52,52,52), new Color(62,62,62)}
    }), model.get("imgnew"));
  }

}