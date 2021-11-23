import org.junit.Before;
import org.junit.Test;

import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import utils.FakeAppendable;
import view.text.IImageProcessView;
import view.text.SimpleImageProcessViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class that tests view.
 */
public class SimpleImageProcessViewImplTest {

  ImageLibModel model;
  Appendable out;
  IImageProcessView viewT;

  @Before
  public void setUp() {
    model = new ImageLibModelImpl();
    out = new StringBuilder();
    viewT = new SimpleImageProcessViewImpl(out, model);
  }

  @Test
  public void testConstructor() {
    try {
      new SimpleImageProcessViewImpl();
      new SimpleImageProcessViewImpl(model);
      new SimpleImageProcessViewImpl(out, model);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx1() {
    new SimpleImageProcessViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx2() {
    new SimpleImageProcessViewImpl(null, model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx3() {
    new SimpleImageProcessViewImpl(null, null);
  }

  @Test
  public void testRenderMessage() {
    this.viewT.renderMessage("hi");
    assertEquals("> hi\n", this.out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderMessageException() {
    IImageProcessView view123 = new SimpleImageProcessViewImpl(new FakeAppendable(), model);
    view123.renderMessage("hi");
  }

  @Test
  public void testRenderError() {
    this.viewT.renderError("hi");
    assertEquals("\u001B[31mhi\u001B[0m\n", this.out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderErrorException() {
    IImageProcessView view123 = new SimpleImageProcessViewImpl(new FakeAppendable(), model);
    view123.renderError("hi");
  }

}