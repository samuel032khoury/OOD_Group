import org.junit.Test;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import controller.utils.ILoader;
import controller.utils.IWriter;
import controller.utils.LoadSuffixManagerV2;
import controller.utils.WriteSuffixManagerV2;
import model.imagefile.ImageFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * this test based on the fact that loading test passes.
 */
public class WriteTest {

  private boolean compareImage(BufferedImage i1, BufferedImage i2) {
    if ((i1.getHeight() != i2.getHeight()) ||  (i1.getWidth() != i2.getWidth())) {
      return false;
    } else if (i1.getType() != i2.getType()) {
      return false;
    } else {
      for (int i = 0; i < i1.getHeight(); i++) {
        for (int j = 0; j < i2.getHeight(); j++) {
          if (i1.getRGB(i,j) != (i2.getRGB(i,j))) {
            return false;
          }
        }
      }
      return true;
    }
  }

  @Test
  public void testGrey() throws Exception {
    String[] path = new String[]{"test/pics/grey.png", "test/pics/grey.jpg", "test/pics/grey.bmp"};
    String[] suffix = new String[]{"png", "jpg", "bmp", "ppm"};

    for (int i = 0; i < path.length; i++) {

      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result." + suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertTrue(compareImage(image1, image2));


      f.delete();
    }
  }

  @Test
  public void testRed() throws Exception {
    String[] path = new String[]{"test/pics/red.png", "test/pics/red.jpg",
      "test/pics/red.bmp"};
    String[] suffix = new String[]{"png", "jpg", "bmp"};

    for (int i = 0; i < path.length; i++) {

      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result." + suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertTrue(compareImage(image1, image2));


      f.delete();
    }
  }

  @Test
  public void testBlue() throws Exception {
    String[] path = new String[]{"test/pics/blue.png", "test/pics/blue.jpg", "test/pics/blue.bmp"};
    String[] suffix = new String[]{"png", "jpg", "bmp"};

    for (int i = 0; i < path.length; i++) {

      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result." + suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertTrue(compareImage(image1, image2));


      f.delete();
    }
  }

  @Test
  public void testGreen() throws Exception {
    String[] path = new String[]{"test/pics/green.png",
      "test/pics/green.jpg", "test/pics/green.bmp"};
    String[] suffix = new String[]{"png", "jpg", "bmp"};

    for (int i = 0; i < path.length; i++) {

      BufferedImage image1 = ImageIO.read(new File(path[i]));
      ILoader loader = new LoadSuffixManagerV2().provide(suffix[i]);
      IWriter writer = new WriteSuffixManagerV2().provide(suffix[i]);
      ImageFile img = loader.loadFile(path[i]);
      String pathName = "result." + suffix[i];
      writer.write(img, pathName);
      File f = new File(pathName);
      BufferedImage image2 = ImageIO.read(f);
      assertTrue(compareImage(image1, image2));


      f.delete();
    }
  }

  @Test
  public void testPPM() throws Exception {
    ILoader loader = new LoadSuffixManagerV2().provide("ppm");
    IWriter writer = new WriteSuffixManagerV2().provide("ppm");
    ImageFile img = loader.loadFile("test/pics/green.ppm");
    writer.write(img, "result.ppm");
    ImageFile img2 = loader.loadFile("result.ppm");
    assertEquals(img2, img);
    File f = new File("result.ppm");
    f.delete();
  }
}