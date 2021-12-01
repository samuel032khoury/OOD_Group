package model.image.operation;

import java.util.ArrayList;
import java.util.Comparator;

import model.image.Image;
import model.image.pixel.Pixel;

public class MosaicOperation implements ImageOperation {
  int seedNum;
  ArrayList<SeedNode> listOSeed;

  public MosaicOperation(int seedNum) {
    this.seedNum = seedNum;
    listOSeed = new ArrayList<>();
  }

  @Override
  public Image apply(Image img) {
    Image imgCp = img.copy();
    int height = imgCp.getHeight();
    int width = imgCp.getWidth();
    for (int i = 0; i < seedNum; i++) {
      int randX = (int) ((Math.random() * height));
      int randY = (int) ((Math.random() * width));
      listOSeed.add(new SeedNode(randX, randY));
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel pix = imgCp.getPixel(i, j);
        listOSeed.sort(new NodeComparator(i, j));
        listOSeed.get(0).include(pix);
      }
    }

    for (SeedNode node : listOSeed) {
       node.average();
    }

    return imgCp;
  }

  protected class SeedNode {
   ArrayList<Pixel> list;
   int x;
   int y;

   public SeedNode(int x, int y) {
     this.list = new ArrayList<>();
     this.x = x;
     this.y = y;
   }

    double calculateDis(int x, int y) {
     return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
   }

   void include(Pixel p) {
     list.add(p);
   }

   void average() {
     int sumRed = 0;
     int sumGreen = 0;
     int sumBlue = 0;
     int counter = 0;
     for (Pixel p : list) {
       sumRed += p.getRed();
       sumGreen += p.getGreen();
       sumBlue += p.getBlue();
       counter += 1;
     }
     for (Pixel p : list) {
       p.setRed(sumRed / counter);
       p.setGreen(sumGreen / counter);
       p.setBlue(sumBlue / counter);
     }
   }
  }

  protected class NodeComparator implements Comparator<SeedNode> {
    int x;
    int y;

    public NodeComparator(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int compare(SeedNode node1, SeedNode node2) {
      final double difference =  node1.calculateDis(this.x, this.y)
              - node2.calculateDis(this.x, this.y);
      return (difference > 0)? 1 : (difference == 0)? 0 : -1;
    }
  }

}
