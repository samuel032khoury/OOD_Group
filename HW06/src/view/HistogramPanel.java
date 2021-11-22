package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.List;

import javax.swing.*;

public class HistogramPanel extends JPanel {

  private List<List<Integer>> histogram;

  HistogramPanel(List<List<Integer>> _histogram){
    super();
    histogram = _histogram;
  }



  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawHistogram(g);
  }

  private int getMaxPixel(List<List<Integer>> histogram) {
    int maxR = 0;
    int maxG = 0;
    int maxB = 0;
    int maxI = 0;
    List<Integer> redList = histogram.get(0);
    List<Integer> greenList = histogram.get(1);
    List<Integer> blueList = histogram.get(2);
    List<Integer> intensityList = histogram.get(3);

    for (int i = 0; i < histogram.get(0).size(); i++) {
      if (redList.get(i) > maxR) {
        maxR = redList.get(i);
      }

      if (greenList.get(i) > maxG) {
        maxG = greenList.get(i);
      }

      if (blueList.get(i) > maxB) {
        maxB = blueList.get(i);
      }

      if (intensityList.get(i) > maxI) {
        maxI = intensityList.get(i);
      }
    }

    return Math.max(Math.max(maxB, maxG), Math.max(maxI, maxR));
  }

  private void drawHistogram(Graphics g) {
    int width = this.getWidth();
    int height = this.getHeight();
    int maxPixels = this.getMaxPixel(this.histogram);
    g.clearRect(0, 0, width, height);
    int verticalOffset = 10;
    int horizontalOffset = 10;
    double xSeparation = (width - 2.0 * horizontalOffset) / (this.histogram.get(0).size() - 1.0);
    double ySeparation = (height - 2.0 * verticalOffset) / maxPixels;

    int[] pX = new int[256];
    for (int i = 0; i < 256; i++) {
      pX[i] = (int) (horizontalOffset + i * xSeparation);
    }
    int[] pYR = new int[256];
    int[] pYG = new int[256];
    int[] pYB = new int[256];
    int[] pYI = new int[256];
    for (int i = 0; i < this.histogram.get(0).size(); i++) {
      int numOfPixelR = this.histogram.get(0).get(i);
      int numOfPixelG = this.histogram.get(1).get(i);
      int numOfPixelB = this.histogram.get(2).get(i);
      int numOfPixelI = this.histogram.get(3).get(i);

      pYR[i] = (int) (verticalOffset + (maxPixels - numOfPixelR) * ySeparation);
      pYG[i] = (int) (verticalOffset + (maxPixels - numOfPixelG) * ySeparation);
      pYB[i] = (int) (verticalOffset + (maxPixels - numOfPixelB) * ySeparation);
      pYI[i] = (int) (verticalOffset + (maxPixels - numOfPixelI) * ySeparation);
    }
    g.setColor(new Color(255, 0, 0, 125));
    g.drawPolyline(pX, pYR, pX.length);
    g.setColor(new Color(0, 255, 0, 125));
    g.drawPolyline(pX, pYG, pX.length);
    g.setColor(new Color(0, 0, 255, 125));
    g.drawPolyline(pX, pYB, pX.length);
    g.setColor(new Color(0, 0, 0, 200));
    g.drawPolyline(pX, pYI, pX.length);
  }
}
