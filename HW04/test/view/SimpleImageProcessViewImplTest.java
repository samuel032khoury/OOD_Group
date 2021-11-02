package view;

import org.junit.Before;
import org.junit.Test;

import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;

import static org.junit.Assert.*;

public class SimpleImageProcessViewImplTest {

  ImageLibModel model;

  @Before
  public void setUp() throws Exception {
    model = new ImageLibModelImpl();
  }

  @Test
  public void testConstructor() throws Exception {
    IImageProcessView view = new SimpleImageProcessViewImpl();
    IImageProcessView view1 = new SimpleImageProcessViewImpl(model);
  }
}