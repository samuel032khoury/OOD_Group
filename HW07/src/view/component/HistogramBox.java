package view.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import model.histogram.BlueChannelHistogram;
import model.histogram.GreenChannelHistogram;
import model.histogram.IntensityHistogram;
import model.histogram.RedChannelHistogram;
import model.image.Image;

/**
 * The visual GUI component that displays and renders the histograms for the currently displayed
 * image.
 */
public class HistogramBox extends JPanel {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 400;
  private static final int PADDING = 25;
  private static final int LABEL_PADDING = 25;
  private static final Color GRID_COLOR = new Color(200, 200, 200, 200);
  private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
  private static final int POINT_SIZE = 4;
  private static final int Y_DIVISIONS = 10; // number of divisions of the y-axis to draw

  private static final int MIN_SCORE = 0; // defines the minimum score for a histogram point

  private Image img; // stores the image

  /**
   * Creates a new GUI box that contains histograms for the current image.
   */
  public HistogramBox(Image img) {
    setSize(WIDTH, HEIGHT);
    this.img = img;
  }

  @Override
  public void paintComponent(Graphics g) {
    // disclaimer: this code draws inspiration from a few comments on this thread over here:
    // https://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // add some white background
    g2.setColor(Color.WHITE);
    g2.fillRect(PADDING + LABEL_PADDING, PADDING, getWidth() - (2 * PADDING) - LABEL_PADDING,
        getHeight() - 2 * PADDING - LABEL_PADDING);
    g2.setColor(Color.BLACK);

    // we use a green channel histogram here, because all histograms will have the same size
    // in other words, we could choose to use any histogram, and that wouldn't change the result
    int COUNTS_SIZE = new GreenChannelHistogram(img).getCounts().size();

    // adds grid lines and the hatch marks on the y-axis
    for (int i = 0; i < Y_DIVISIONS + 1; i++) {
      int x0 = PADDING + LABEL_PADDING;
      int x1 = POINT_SIZE + PADDING + LABEL_PADDING;
      int y0 = getHeight() - ((i * (getHeight() - PADDING * 2 - LABEL_PADDING)) / Y_DIVISIONS
          + PADDING + LABEL_PADDING);

      if (COUNTS_SIZE > 0) {
        g2.setColor(GRID_COLOR);
        g2.drawLine(PADDING + LABEL_PADDING + 1 + POINT_SIZE, y0, getWidth() - PADDING, y0);
        g2.setColor(Color.BLACK);
        String yLabel = ((int) (
            (MIN_SCORE + (getMaxScore() - MIN_SCORE) * ((i * 1.0) / Y_DIVISIONS))
                * 100)) / 100.0 + "";

        FontMetrics metrics = g2.getFontMetrics();
        int labelWidth = metrics.stringWidth(yLabel);
        g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
      }

      g2.drawLine(x0, y0, x1, y0);
    }

    // now we do the exact same thing, but for the x-axis
    for (int i = 0; i < COUNTS_SIZE; i++) {
      if (COUNTS_SIZE > 1) {
        int x0 = i * (getWidth() - PADDING * 2 - LABEL_PADDING) / (COUNTS_SIZE - 1) + PADDING
            + LABEL_PADDING;
        int y0 = getHeight() - PADDING - LABEL_PADDING;
        int y1 = y0 - POINT_SIZE;
        if ((i % ((int) ((COUNTS_SIZE / 20.0)) + 1)) == 0) {
          g2.setColor(GRID_COLOR);
          g2.drawLine(x0, getHeight() - PADDING - LABEL_PADDING - 1 - POINT_SIZE, x0, PADDING);
          g2.setColor(Color.BLACK);
          String xLabel = i + "";
          FontMetrics metrics = g2.getFontMetrics();
          int labelWidth = metrics.stringWidth(xLabel);
          g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
        }
        g2.drawLine(x0, y0, x0, y1);
      }
    }

    // we create the axes over here
    g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING,
        PADDING + LABEL_PADDING, PADDING);
    g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING,
        getWidth() - PADDING,
        getHeight() - PADDING - LABEL_PADDING);

    // draw points for red, green, blue, and intensity histograms
    drawPoints(g2, new RedChannelHistogram(img).getCounts(), Color.RED);
    drawPoints(g2, new GreenChannelHistogram(img).getCounts(), Color.GREEN);
    drawPoints(g2, new BlueChannelHistogram(img).getCounts(), Color.BLUE);
    drawPoints(g2, new IntensityHistogram(img).getCounts(), Color.YELLOW);
  }

  /**
   * Draws the points for the given list of points retrieved from the histogram and the color of the
   * line to draw.
   */
  private void drawPoints(Graphics2D g2, Map<Integer, Integer> points, Color lineColor) {

    int MAX_SCORE = getMaxScore();

    // compute the scale of this histogram
    double xScale = ((double) getWidth() - (2 * PADDING) - LABEL_PADDING) / (points.size() - 1);
    double yScale =
        ((double) getHeight() - 2 * PADDING - LABEL_PADDING) / (MAX_SCORE - MIN_SCORE);

    List<Point> graphPoints = new ArrayList<>();
    for (int i = 0; i < points.size(); i++) {
      // for every data point in the histogram, we add a new (x, y) point to the graph
      int x1 = (int) (i * xScale + PADDING + LABEL_PADDING);
      int y1 = (int) ((getMaxScore() - points.get(i)) * yScale + PADDING);
      graphPoints.add(new Point(x1, y1));
    }

    Stroke oldStroke = g2.getStroke();
    g2.setColor(lineColor);
    g2.setStroke(GRAPH_STROKE);
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }

    g2.setStroke(oldStroke);
    g2.setColor(lineColor);
    for (Point graphPoint : graphPoints) {
      int x = graphPoint.x - POINT_SIZE / 2;
      int y = graphPoint.y - POINT_SIZE / 2;
      g2.fillOval(x, y, POINT_SIZE, POINT_SIZE);
    }
  }

  /**
   * Gets the max possible count for a histogram point for this image.
   *
   * @return the max possible count for a histogram point for this image
   */
  private int getMaxScore() {
    return img.getWidth() * img.getHeight(); // the counts cannot physically exceed this amount
  }

  /**
   * Updates the currently drawn diagram with a new image.
   *
   * @param img the image to draw the histogram on
   */
  public void update(Image img) {
    this.img = img;
    this.repaint();
  }
}
