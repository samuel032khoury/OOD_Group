package view;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

public class HistogramGraphPanel extends JPanel {

  private final List<List<Integer>> histogramData;

  HistogramGraphPanel(List<List<Integer>> _histogramData){
    super();
    this.histogramData = _histogramData;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawHistogram(g);
  }

  private void drawHistogram(Graphics g) {
    final int width = this.getWidth();
    final int height = this.getHeight();
    g.clearRect(0, 0, width, height);

    final int colorBits = this.histogramData.get(0).size();
    final int maxPixels = this.getMaxPixel();
    final int verticalOffset = 10;
    final int horizontalOffset = 10;
    final double xStepLength = (width - 2.0 * horizontalOffset) / Math.max(1, (colorBits - 1));
    final double yStepLength = (height - 2.0 * verticalOffset) / maxPixels;
    final int[] pX = new int[colorBits];
    for (int i = 0; i < colorBits; i++) {
      pX[i] = (int) (horizontalOffset + i * xStepLength);
    }
    final int[] pYR = new int[colorBits];
    final int[] pYG = new int[colorBits];
    final int[] pYB = new int[colorBits];
    final int[] pYI = new int[colorBits];
    for (int i = 0; i < colorBits; i++) {
      final int numOfPixelR = this.histogramData.get(0).get(i);
      final int numOfPixelG = this.histogramData.get(1).get(i);
      final int numOfPixelB = this.histogramData.get(2).get(i);
      final int numOfPixelI = this.histogramData.get(3).get(i);

      pYR[i] = (int) (verticalOffset + (maxPixels - numOfPixelR) * yStepLength);
      pYG[i] = (int) (verticalOffset + (maxPixels - numOfPixelG) * yStepLength);
      pYB[i] = (int) (verticalOffset + (maxPixels - numOfPixelB) * yStepLength);
      pYI[i] = (int) (verticalOffset + (maxPixels - numOfPixelI) * yStepLength);
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

  private int getMaxPixel() {
    if (histogramData.get(0).size()==0) {
      return 0;
    }
    final int maxR = Collections.max(this.histogramData.get(0));
    final int maxG = Collections.max(this.histogramData.get(1));
    final int maxB = Collections.max(this.histogramData.get(2));
    final int maxI = Collections.max(this.histogramData.get(3));

    return Math.max(Math.max(maxB, maxG), Math.max(maxI, maxR));
  }
}
