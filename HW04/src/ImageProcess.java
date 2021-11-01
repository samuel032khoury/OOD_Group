import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import controller.controller.ImageProcessControllerImpl;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import model.library.ImageLibState;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

public class ImageProcess {

  public static void main(String[] args)  {
    InputStream targetStream = null;
    if (Objects.equals(args[0], "-f")) {
      String filePath = args[1];
      File file = new File(filePath);
      try {
        targetStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("file is not found");
      }
    } else if (Objects.equals(args[0], "-n")){
      targetStream = System.in;
    }

    Readable input = new InputStreamReader(targetStream);
    Appendable output = System.out;
    ImageLibModel model = new ImageLibModelImpl();
    IImageProcessView view = new SimpleImageProcessViewImpl(output, model);
    ImageProcessControllerImpl c = new ImageProcessControllerImpl(model, input, view);
    c.run();
  }
}
