import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.HashMap;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileNoAlpha;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests that test {@code ImageLibModelImpl}.
 */
public class LibraryTest {
  ImageFile img;
  HashMap<String, ImageFile> map;
  ImageLibModel model;

  @Before
  public void setUp() {
    img = new ImageFileNoAlpha(new Color[][]{
            {new Color(10, 11, 12), new Color(20, 21, 22), new Color(30, 31, 32)},
            {new Color(40, 41, 42), new Color(50, 51, 52), new Color(60, 61, 62)}}
    );
    map = new HashMap<String, ImageFile>();

    model = new ImageLibModelImpl(map);
  }

  @Test
  public void testSuccessFulConstructor() throws Exception {
    try {
      ImageLibModel model0 = new ImageLibModelImpl();
      ImageLibModel model1 = new ImageLibModelImpl(map);
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLibEx() {
    new ImageLibModelImpl(null);
  }

  @Test
  public void testLoad() {
    model.loadImage("img", img);
    assertTrue(map.containsKey("img"));
    assertTrue(map.containsValue(img));
  }

  @Test
  public void testGet() {
    map.put("img", img);
    ImageFile getImg = model.get("img");
    assertEquals(getImg, img);
  }

  @Test
  public void testGetLibSize() {
    map.put("img", img);
    assertEquals(1, model.getLibSize());
  }

  @Test
  public void testGetLibSize1() {
    assertEquals(0, model.getLibSize());
  }
}
