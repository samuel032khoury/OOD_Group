package model.image.operation;

import java.util.ArrayList;

import model.image.Image;
import model.image.pixel.Pixel;

/**
 * Performs a mosaic operation on a given image. A mosaic operation applies the mosaic effect onto a
 * given image by clustering pixels into provided {@code seedNum} fragments(tiles).
 */
public class MosaicOperation implements ImageOperation {
  private final int seedNum;
  private final ArrayList<SeedNode> listOSeed;

  /**
   * Creates a new mosaic operation instance.
   *
   * @param seedNum the number of seed for mosaic. In general, the larger this number is, the more
   *                recognizable the resulted image is.
   */
  public MosaicOperation(int seedNum) {
    if (seedNum < 1) {
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
        for (SeedNode currNode : listOSeed) {
          if (currClosestNode.getKey() == 0) {
            break;
          }
          double currDistance = currNode.calculateDis(i, j);
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

  /**
   * To represent a mosaic seed node that has the location information on an image, and also stores
   * all the {@link Pixel}s of the same image that take this seed node as the nearest seed.
   */
  public static final class SeedNode {
    private final int x;
    private final int y;
    private final ArrayList<Pixel> list;

    /**
     * To instantiate a {@link SeedNode} located on the {@code x}, {@code y} on the image.
     *
     * @param x the x coordinate of the {@link SeedNode}
     * @param y the y coordinate of the {@link SeedNode}
     */
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

  /**
   * To represent a key-value pair.
   * @param <K> the data type of the key for the {@link Pair}
   * @param <V> the data type of the value for the {@link Pair}
   */
  public static class Pair<K, V> {
    private K key;
    private V value;

    /**
     * To instantiate a {@link Pair} with the key and the value.
     *
     * @param key the key of the {@link Pair}
     * @param value the value of the {@link Pair}
     */
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
