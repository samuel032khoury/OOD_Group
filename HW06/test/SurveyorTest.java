import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.imagefile.ImageFile;
import view.utils.ISurveyor;
import view.utils.Surveyor;

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

  }
}