import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.StringReader;

import controller.controller.text.ImageProcessControllerImplV2;
import utils.MockImage;
import utils.MockModel;
import controller.controller.text.IImageProcessController;
import controller.controller.text.ImageProcessControllerImpl;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.text.IImageProcessView;
import view.text.SimpleImageProcessViewImpl;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * To test the {@link ImageProcessControllerImpl} class.
 */
public class ImageProcessControllerImplTest {
  ImageLibModel model;
  ImageFile img;
  IImageProcessView view;
  Appendable out;
  Readable in;

  private IImageProcessController giveModel(ImageLibModel model, Readable in,
                                            IImageProcessView view) {
    return new ImageProcessControllerImplV2(model, in, view);
  }



  @Before
  public void setUp() {
    model = new ImageLibModelImpl();
    img = new ImageFileImpl(new Color[][]{
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
      new ImageProcessControllerImpl();
      new ImageProcessControllerImpl(model);
      new ImageProcessControllerImpl(model, in);
      new ImageProcessControllerImpl(model, in, view);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx1() {
    new ImageProcessControllerImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx2() {
    new ImageProcessControllerImpl(null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx3() {
    new ImageProcessControllerImpl(model, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx4() {
    new ImageProcessControllerImpl(model, in, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx5() {
    new ImageProcessControllerImpl(model, null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx6() {
    new ImageProcessControllerImpl(null, in, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx7() {
    new ImageProcessControllerImpl(null, null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx8() {
    new ImageProcessControllerImpl(null, in, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx9() {
    new ImageProcessControllerImpl(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx10() {
    new ImageProcessControllerImpl(null, null, null);
  }

  @Test
  public void testQuit() {
    in = new StringReader("QUIT");
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> Processing command, please wait!\n> The program is quit!\n", out.toString());
  }

  @Test
  public void testSize() {
    in = new StringReader("size");
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> Processing command, please wait!\n> There are 0 images in the library!\n",
            out.toString());
  }

  @Test
  public void testSize1() {
    in = new StringReader("size");
    model.loadImage("img1", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> Processing command, please wait!\n> There are 1 images in the library!\n",
            out.toString());
  }

  @Test
  public void testSizeError() {
    in = new StringReader("size w");
    model.loadImage("img1", img);
    IImageProcessController controller = new ImageProcessControllerImpl(model, in, view);
    controller.run();
    assertEquals("> Processing command, please wait!\n[31mToo many arguments provided for size, "
            + "try again![0m\n", out.toString());
  }

  @Test
  public void testCommandNotFound() {
    in = new StringReader("isNew w");
    model.loadImage("img1", img);
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("\u001B[31mCommand not found!\u001B[0m\n", out.toString());
  }

  @Test
  public void testCommandSendCorrection() {
    in = new StringReader("vertical-flip mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.visual.FlipOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection1() {
    in = new StringReader("horizontal-flip mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.visual.FlipOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection2() {
    in = new StringReader("brighten 20 mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.visual.BrightnessOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection3() {
    in = new StringReader("darken 20 mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.visual.BrightnessOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection4() {
    in = new StringReader("blue-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection5() {
    in = new StringReader("red-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection6() {
    in = new StringReader("green-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection7() {
    in = new StringReader("luma-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection8() {
    in = new StringReader("value-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection9() {
    in = new StringReader("intensity-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection10() {
    in = new StringReader("load mock mock");
    Appendable output = new StringBuilder();
    ImageLibModel model = new MockModel(output);
    model.loadImage("mock", img);
    IImageProcessController controller
            = this.giveModel(model, in, view);
    controller.run();
    assertEquals("loaded mock", output.toString());
  }

  @Test
  public void testCommandSendCorrection11() {
    in = new StringReader("save mock.jpg mock");
    Appendable output = new StringBuilder();
    ImageLibModel model = new MockModel(output);
    model.loadImage("mock", img);
    IImageProcessController controller
            = this.giveModel(model, in, view);
    controller.run();
    assertEquals("loaded mockPeek mock", output.toString());
  }

  @Test
  public void testCommandSendCorrection12() {
    in = new StringReader("blur mock mock");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.FilterOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection13() {
    in = new StringReader("sharpen mock mock");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.FilterOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection14() {
    in = new StringReader("greyscale mock mock");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testCommandSendCorrection15() {
    in = new StringReader("sepia mock mock");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.TintingOperation", output.toString());
  }

  @Test
  public void testLineMultiCommand() {
    in = new StringReader("value-component mock mockNew & intensity-component mock mockNew");
    Appendable output = new StringBuilder();
    model.loadImage("mock", new MockImage(output));
    IImageProcessController controller = this.giveModel(model, in, view);
    controller.run();
    assertEquals("did class model.operation.color.GreyscaleOperation"
            + "did class model.operation.color.GreyscaleOperation", output.toString());
  }

  @Test
  public void testGoThrough() {
    in = new StringReader("value-component img imgNew");
    model.loadImage("img", img);
    IImageProcessController controller = this.giveModel(model, in, view);

    controller.run();
    assertEquals(new ImageFileImpl(new Color[][]{
            {new Color(12,12,12), new Color(22,22,22), new Color(32,32,32)},
            {new Color(42,42,42), new Color(52,52,52), new Color(62,62,62)}
    }), model.get("imgNew"));
  }

}