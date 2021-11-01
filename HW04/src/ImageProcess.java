import java.io.InputStreamReader;

import controller.controller.ImageProcessControllerImpl;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.SimpleImageProcessViewImpl;

public class ImageProcess {
  public static void main(String[] args) {
    ImageLibModel m = new ImageLibModelImpl();
    ImageProcessControllerImpl c = new ImageProcessControllerImpl(m,
            new InputStreamReader(System.in),
            new SimpleImageProcessViewImpl(System.out, m));
    c.run();
  }
}
