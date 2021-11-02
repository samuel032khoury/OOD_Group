import org.junit.Before;
import org.junit.Test;

import Utils.FakeAppendable;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

import static org.junit.Assert.*;

public class SimpleImageProcessViewImplTest {

  ImageLibModel model;
  Appendable out;
  IImageProcessView viewT;

  @Before
  public void setUp() throws Exception {
    model = new ImageLibModelImpl();
    out = new StringBuilder();
    viewT = new SimpleImageProcessViewImpl(out, model);
  }

  @Test
  public void testConstructor() throws Exception {
    try {
      IImageProcessView view = new SimpleImageProcessViewImpl();
      IImageProcessView view1 = new SimpleImageProcessViewImpl(model);
      IImageProcessView view2 = new SimpleImageProcessViewImpl(out, model);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx1() throws Exception {
    new SimpleImageProcessViewImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx2() throws Exception {
    new SimpleImageProcessViewImpl(null, model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEx3() throws Exception {
    new SimpleImageProcessViewImpl(null, null);
  }

  @Test
  public void testRenderMessage() throws Exception {
    this.viewT.renderMessage("hi");
    assertEquals("> hi\n", this.out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderMessageException() throws Exception {
    IImageProcessView view123 = new SimpleImageProcessViewImpl(new FakeAppendable(), model);
    view123.renderMessage("hi");
  }

  @Test
  public void testRenderError() throws Exception {
    this.viewT.renderError("hi");
    assertEquals("\u001B[31mhi\u001B[0m\n", this.out.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderErrorException() throws Exception {
    IImageProcessView view123 = new SimpleImageProcessViewImpl(new FakeAppendable(), model);
    view123.renderError("hi");
  }

}