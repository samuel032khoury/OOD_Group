package view.gui.histogram;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.imagefile.ReadOnlyImageFile;

/**
 * To represent a surveyor that updates the data for drawing a histogram. This implementation uses a
 * list containing 4 integer lists, in an order of red-Channel, green-Channel, blue-Channel, and
 * intensity-Channel, to store that data for the histogram.
 */
public class HistogramSurveyorImpl implements IHistogramSurveyor {

  private final List<List<Integer>> histogramData;

  /**
   * To construct a {@link HistogramSurveyorImpl} with the given {@code histogramData} of size 4.
   *
   * @param histogramData a list containing 4 integer lists, in an order of red-Channel,
   *                      green-Channel, blue-Channel, and intensity-Channel
   * @throws IllegalArgumentException when the provided list is null or is not of size of 4.
   */
  public HistogramSurveyorImpl(List<List<Integer>> histogramData) throws IllegalArgumentException {
    if (histogramData == null) {
      throw new IllegalArgumentException("Fail to specify a list that contains histogram data!");
    } else if (histogramData.size() != 4) {
      throw new IllegalArgumentException("Invalid number of histogram channels!");
    }
    this.histogramData = histogramData;
  }

  /**
   * Provided a {@link ReadOnlyImageFile}, update the histogram data based on it.
   */
  public void updateHistogramData(ReadOnlyImageFile currImageFile) {
    int currWidth = currImageFile.getWidth();
    int currHeight = currImageFile.getHeight();

    List<Integer> redList = new ArrayList<>();
    List<Integer> greenList = new ArrayList<>();
    List<Integer> blueList = new ArrayList<>();
    List<Integer> intensityList = new ArrayList<>();

    for (List<Integer> list : List.of(redList, greenList, blueList, intensityList)) {
      for (int currVal = 0; currVal < 256; currVal++) {
        list.add(0);
      }
    }

    for (int i = 0; i < currWidth; i++) {
      for (int j = 0; j < currHeight; j++) {
        Color color = currImageFile.getColorAt(j, i);

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int intensity = (red + green + blue) / 3;

        redList.set(red, redList.get(red) + 1);
        greenList.set(green, greenList.get(green) + 1);
        blueList.set(blue, blueList.get(blue) + 1);
        intensityList.set(intensity, intensityList.get(intensity) + 1);
      }
    }

    for (int i = 0; i < 4; i++) {
      this.histogramData.set(i, List.of(redList, greenList, blueList, intensityList).get(i));
    }
  }
}
