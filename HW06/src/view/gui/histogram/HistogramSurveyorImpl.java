package view.gui.histogram;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.imagefile.ReadOnlyImageFile;

public class HistogramSurveyorImpl implements IHistogramSurveyor {

  private final List<List<Integer>> histogramData;

  public HistogramSurveyorImpl(List<List<Integer>> histogramData){
    this.histogramData = histogramData;
  }

  public void updateHistogramList(ReadOnlyImageFile currImageFile) {
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
