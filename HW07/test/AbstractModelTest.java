import java.util.Arrays;
import java.util.List;
import model.image.Image;
import model.image.RGBImage;
import model.image.pixel.Pixel;
import model.image.pixel.RGBPixel;
import org.junit.Before;

/**
 * Contains all the test that pertain to the {@link Image}.
 */
public class AbstractModelTest {

  protected Image img;

  @Before
  public void setUp() {
    // Creates a 2x2 image:
    // (1, 2, 3)    (100, 100, 100)
    // (48, 255, 50) (200, 231, 0)
    List<List<Pixel>> data = Arrays.asList(
        Arrays.asList(new RGBPixel(1, 2, 3), new RGBPixel(100, 100, 100)),
        Arrays.asList(new RGBPixel(48, 255, 50), new RGBPixel(200, 231, 0))
    );

    img = new RGBImage(data);
  }
}
