import controller.controller.ImageProcessControllerImpl;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;

public class ImageProcess {
  public static void main(String[] args) {
    ImageProcessControllerImpl c = new ImageProcessControllerImpl();
    c.run();
  }
}
