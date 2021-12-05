package model.image.operation;

import org.junit.Test;

import java.util.ArrayList;

import model.AbstractModelTest;
import model.image.Image;
import model.image.operation.colortransform.MosaicOperation;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;

import static org.junit.Assert.*;

public class MosaicOperationTest extends AbstractModelTest {

  @Test
  public void testOperation1() {
    Image newImage = new MosaicOperation(1).apply(img);

    assertEquals(1, this.checkImageCluster(newImage));
    assertEquals(new RGBPixel(87, 147, 38), newImage.getPixel(0, 0));
    assertEquals(new RGBPixel(87, 147, 38), newImage.getPixel(0, 1));
    assertEquals(new RGBPixel(87, 147, 38), newImage.getPixel(1, 0));
    assertEquals(new RGBPixel(87, 147, 38), newImage.getPixel(1, 1));
  }

  @Test
  public void testOperation2() {
    Image newImage = new MosaicOperation(2).apply(img);

    assertEquals(2, this.checkImageCluster(newImage));
  }

  @Test
  public void testOperation3() {
    Image newImage = new MosaicOperation(3).apply(img);

    assertEquals(3, this.checkImageCluster(newImage));
  }

  @Test
  public void testOperation4() {
    Image newImage = new MosaicOperation(4).apply(img);

    assertEquals(4, this.checkImageCluster(newImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperationException() {
    Image newImage = new MosaicOperation(5).apply(img);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperationException1() {
    Image newImage = new MosaicOperation(0).apply(img);
  }


  private int checkImageCluster(Image image) {
    ArrayList<Pixel> RGBPixelList = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        Pixel p = image.getPixel(i,j);
        if (!RGBPixelList.contains(p)) {
          RGBPixelList.add(p);
        }
      }
    }
    return RGBPixelList.size();
  }
}