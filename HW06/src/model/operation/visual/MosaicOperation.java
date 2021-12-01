package model.operation.visual;

import java.awt.*;
import java.util.ArrayList;

import model.operation.ANoAlphaOperation;
import model.operation.RegularImageOperation;

public class MosaicOperation extends ANoAlphaOperation {
  private final int seedNum;
  private final ArrayList<SeedNode> listOSeed;

  public MosaicOperation(int seedNum) {
    this.seedNum = seedNum;
    this.listOSeed = new ArrayList<>();
  }
  /**
   * Perform operations on the given {@code pixels}, the operation rule depends on specific
   * implementations.
   *
   * @param pixels a 2-D {@code Array} of {@link Color} that represents an image
   * @return a processed 2-D {@code Array} of {@link Color} representing an image, by the operation
   * rule provided by concrete classes.
   */
  @Override
  protected Color[][] process(Color[][] pixels) {
    Color[][] adjusted = pixels.clone();
    Pair<Double, SeedNode> currClosestNode;
    for (int i = 0; i < seedNum; i++) {
      int randX = (int) ((Math.random() * this.height));
      int randY = (int) ((Math.random() * this.width));
      listOSeed.add(new SeedNode(randX, randY));
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        SeedNode firstNode = listOSeed.get(0);
        currClosestNode = new Pair<>(firstNode.calculateDis(i, j), firstNode);
        for(SeedNode currNode : listOSeed) {
          double currDistance = currNode.calculateDis(i,j);
          if (currDistance < currClosestNode.getKey()) {
            currClosestNode.resetPair(currDistance, currNode);
          }
        }
        currClosestNode.getVal().include(new Point(i,j), adjusted[i][j]);
      }
    }

    for (SeedNode node : listOSeed) {
       node.pasteTile(adjusted);
    }

    return adjusted;
  }



  private static final class SeedNode {
   private final int x;
   private final int y;
   private final ArrayList<Color> colorList;
   private final ArrayList<Point> pointList;

   public SeedNode(int x, int y) {
     this.x = x;
     this.y = y;
     this.colorList = new ArrayList<>();
     this.pointList = new ArrayList<>();
   }

   private double calculateDis(int x, int y) {
     return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
   }

   private void include(Point p, Color c) {
     pointList.add(p);
     colorList.add(c);
   }

   private void pasteTile(Color[][] image) {
     int sumRed = 0;
     int sumGreen = 0;
     int sumBlue = 0;
     int counter = 0;
     for (Color c : colorList) {
       sumRed += c.getRed();
       sumGreen += c.getGreen();
       sumBlue += c.getBlue();
       counter += 1;
     }
     for (Point p : pointList) {
       image[p.x][p.y] = new Color(sumRed / counter,sumGreen / counter,sumBlue / counter);
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
