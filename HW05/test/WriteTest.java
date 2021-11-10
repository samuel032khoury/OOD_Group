import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import controller.utils.ILoader;
import controller.utils.IWriter;
import controller.utils.LoadSuffixManagerV2;
import controller.utils.WriteSuffixManagerV2;
import model.imagefile.ImageFile;

import static org.junit.Assert.assertEquals;

/**
 * this test based on the fact that loading test passes
 */
public class WriteTest {

  public static void main(String[] args) {
    ILoader loader = new LoadSuffixManagerV2().provide("png");
    IWriter writer = new WriteSuffixManagerV2().provide("png");
    ImageFile img = loader.loadFile("test/pics/grey.png");
    writer.write(img, "result.jpg");
  }

  @Test
  public void testGrey() throws Exception {
    String[] path = new String[]{"test/pics/grey.png", "test/pics/grey.jpg", "test/pics/grey.bmp", "test/pics/grey.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {
      System.out.println(i);
      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result."+suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertEquals(image1, image2);
      f.delete();
    }
  }

  @Test
  public void testRed() throws Exception {
    String[] path = new String[]{"test/pics/red.png", "test/pics/red.jpg",
            "test/pics/red.bmp", "test/pics/red.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {
      System.out.println(i);
      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result."+suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertEquals(image1, image2);
      f.delete();
    }
  }

  @Test
  public void testBlue() throws Exception {
    String[] path = new String[]{"test/pics/blue.png", "test/pics/blue.jpg", "test/pics/blue.bmp", "test/pics/blue.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {
      System.out.println(i);
      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result."+suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertEquals(image1, image2);
      f.delete();
    }
  }

  @Test
  public void testGreen() throws Exception {
    String[] path = new String[]{"test/pics/green.png", "test/pics/green.jpg", "test/pics/green.bmp", "test/pics/green.ppm"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {
      System.out.println(i);
      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result."+suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertEquals(image1, image2);
      f.delete();
    }
  }
}