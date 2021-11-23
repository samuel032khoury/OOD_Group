import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import view.utils.ISurveyor;
import view.utils.Surveyor;

import static org.junit.Assert.assertEquals;

public class SurveyorTest {

  ImageFile file;
  Color[][] color;
  List<List<Integer>> histogramData;
  ISurveyor surveyor;

  @Before
  public void init() {
    this.histogramData = new ArrayList<>() {{
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
      add(new ArrayList<>());
    }};

    surveyor = new Surveyor(this.histogramData);

  }

  @Test
  public void testSurveyor() {
    this.color = new Color[][]{{new Color(100, 101,102), new Color(1, 2,3), new Color(1, 2,3)},
            {new Color(101, 101,103), new Color(4, 2,2), new Color(1, 101,103)}};
    // 101 2 2 102 3 68
    this.file = new ImageFileImpl(this.color);
    this.surveyor.updateHistogramList(this.file);

    int valueR100 = this.histogramData.get(0).get(100);
    assertEquals(1, valueR100);
    int valueR1 = this.histogramData.get(0).get(1);
    assertEquals(3, valueR1);
    int valueR101 = this.histogramData.get(0).get(100);
    assertEquals(1, valueR101);
    int valueR4 = this.histogramData.get(0).get(4);
    assertEquals(1, valueR4);
    int valueR150 = this.histogramData.get(0).get(150);
    assertEquals(0, valueR150);

    int valueG101 = this.histogramData.get(1).get(101);
    assertEquals(3, valueG101);
    int valueG2 = this.histogramData.get(1).get(2);
    assertEquals(3, valueG2);
    int valueG150 = this.histogramData.get(1).get(150);
    assertEquals(0, valueG150);

    int valueB102 = this.histogramData.get(2).get(102);
    assertEquals(1, valueB102);
    int valueB103 = this.histogramData.get(2).get(103);
    assertEquals(2, valueB103);
    int valueB3 = this.histogramData.get(2).get(3);
    assertEquals(2, valueB3);
    int valueB2 = this.histogramData.get(2).get(2);
    assertEquals(1, valueB2);
    int valueB150 = this.histogramData.get(2).get(150);
    assertEquals(0, valueB150);

    int valueI101 = this.histogramData.get(3).get(101);
    assertEquals(2, valueI101);
    int valueI2 = this.histogramData.get(3).get(2);
    assertEquals(3, valueI2);
    int valueI68 = this.histogramData.get(3).get(68);
    assertEquals(1, valueI68);
    int valueI150 = this.histogramData.get(3).get(150);
    assertEquals(0, valueI150);
  }
}