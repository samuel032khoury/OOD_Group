package model.image.operation;

import java.util.ArrayList;

import model.image.Image;
import model.image.pixel.Pixel;

public class MosaicOperation implements ImageOperation {
  private final int seedNum;
  private final ArrayList<SeedNode> listOSeed;

  public MosaicOperation(int seedNum) {
    if(seedNum < 1) {
      throw new IllegalArgumentException("Invalid mosaic seed number!");
    }
    this.seedNum = seedNum;
    this.listOSeed = new ArrayList<>();
  }

  @Override
  public Image apply(Image img) {
    Image copy = img.copy();
    int height = copy.getHeight();
    int width = copy.getWidth();
    Pair<Double, SeedNode> currClosestNode;
    for (int i = 0; i < seedNum; i++) {
      int randX = (int) ((Math.random() * height));
      int randY = (int) ((Math.random() * width));
      listOSeed.add(new SeedNode(randX, randY));
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        SeedNode firstNode = listOSeed.get(0);
        currClosestNode = new Pair<>(firstNode.calculateDis(i, j), firstNode);
        for(SeedNode currNode : listOSeed) {
          if (currClosestNode.getKey() == 0) {
            break;
          }
          double currDistance = currNode.calculateDis(i,j);
          if (currDistance < currClosestNode.getKey()) {
            currClosestNode.resetPair(currDistance, currNode);
          }
        }
        currClosestNode.getVal().include(copy.getPixel(i, j));
      }
    }

    for (SeedNode node : listOSeed) {
       node.average();
    }

    return copy;
  }

  private static final class SeedNode {
   private final int x;
   private final int y;
   private final ArrayList<Pixel> list;

   public SeedNode(int x, int y) {
     this.x = x;
     this.y = y;
     this.list = new ArrayList<>();
   }

   private double calculateDis(int x, int y) {
     return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
   }

   private void include(Pixel p) {
     list.add(p);
   }

   private void average() {
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

  private static class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    private K getKey() {
      return key;
    }

    private V getVal() {
      return value;
    }

    private void resetPair(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }
}
