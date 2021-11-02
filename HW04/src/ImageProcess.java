import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import controller.controller.ImageProcessControllerImpl;
import model.imageFile.ImageFileNoAlpha;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;

public class ImageProcess {

  public static void main(String[] args) {
    if(args.length < 1) {
      throw new IllegalArgumentException("Insufficient Arguments! Use -f to run a text-based " +
              "script (f)ile or use -m to (m)anually manage and process images!");
    }

    InputStream targetStream;
    if (args[0].equals("-f")) {
      try {
        String filePath = args[1];
        File file = new File(filePath);
        targetStream = new FileInputStream(file);
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException("Please provide the path of the text-based script file!");
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Unable to find the provided file! Please " +
                "check the name or the path of the file is accurate and try again!");
      }
    } else if (args[0].equals("-m")) {
      targetStream = System.in;
    } else {
      targetStream = null;
    }

    if(targetStream == null) {
      throw new IllegalArgumentException("Unsupported input type! Use either -f to run a " +
              "text-based script (f)ile or use -m to (m)anually manage and process images!");
    }

    ImageLibModel model = new ImageLibModelImpl();
    Readable input = new InputStreamReader(targetStream);

    // controller uses default view with System.out as output destination and give model for
    // which it renders.
    ImageProcessControllerImpl c = new ImageProcessControllerImpl(model, input);
    c.run();
  }
}
