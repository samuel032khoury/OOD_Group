import org.junit.Test;

import java.awt.Color;

import controller.utils.ILoader;
import controller.utils.LoadSuffixManagerV2;
import model.imagefile.ImageFile;

import static org.junit.Assert.assertEquals;


public class LoadTest {
  @Test
  public void testGrey() throws Exception {
    String[] path = new String[]{"test/pics/grey.png", "test/pics/grey.jpg",
      "test/pics/grey.bmp", "test/pics/grey.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      assertEquals(new Color(89,89,89), img.getColorAt(2,2));
    }
  }

  @Test
  public void testRed() throws Exception {
    String[] path = new String[]{"test/pics/red.png", "test/pics/red.jpg",
      "test/pics/red.bmp", "test/pics/red.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};
    Color[] colors = new Color[]{new Color(255,0,17), new Color(255,0,16),
      new Color(255,0,16),new Color(255,36,0)};

    for (int i = 0; i < path.length; i++) {
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      assertEquals(colors[i], img.getColorAt(2,2));
    }
  }

  @Test
  public void testBlue() throws Exception {
    String[] path = new String[]{"test/pics/blue.png", "test/pics/blue.jpg",
      "test/pics/blue.bmp", "test/pics/blue.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};
    Color[] colors = new Color[]{new Color(0,21,246), new Color(0,21,246),
      new Color(0,21,246),new Color(0,21,246)};

    for (int i = 0; i < path.length; i++) {
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      assertEquals(colors[i], img.getColorAt(2,2));
    }
  }

  @Test
  public void testGreen() throws Exception {
    String[] path = new String[]{"test/pics/green.png", "test/pics/green.jpg",
      "test/pics/green.bmp", "test/pics/green.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};
    Color[] colors = new Color[]{new Color(0,255,61), new Color(0,255,61),
      new Color(0,255,61),new Color(0,255,61)};

    for (int i = 0; i < path.length; i++) {
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      assertEquals(colors[i], img.getColorAt(2,2));
    }
  }
}