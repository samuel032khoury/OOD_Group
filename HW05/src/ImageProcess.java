import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.controller.IImageProcessController;
import controller.controller.ImageProcessControllerImplV2;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;

/**
 * The main class to run the Image Processing program.
 */
public class ImageProcess {
  /**
   * Entry to the Image Processing program.
   */
  public static void main(String[] args) {
    InputStream targetStream = null;
    // TODO : ADD JUSTIFICATION FOR SUPPORT EMPTY COMMAND LEVEL ARGS
    if (args.length < 1) {
      targetStream = System.in;
    } else if (args[0].equals("-f")) {
      try {
        String filePath = args[1];
        File file = new File(filePath);
        targetStream = new FileInputStream(file);
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException(
                "Please provide the path of the text-based script file!");
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Unable to find the provided file! Please "
                + "check the name or the path of the file is accurate and try again!");
      }
    } //else if (args[0].equals("-m")) {
//      targetStream = System.in;
//    } else {
//      targetStream = null;
//    }
    // TODO: justification for editing error message
    if (targetStream == null) {
      throw new IllegalArgumentException("Unknown arguments! Use -f to run a "
              + "text-based script (f)ile or enter interactive mode by providing no arguments!");
    }

    ImageLibModel model = new ImageLibModelImpl();
    Readable input = new InputStreamReader(targetStream);

    // controller uses default view with System.out as output destination and give model for
    // which it renders.
    IImageProcessController c = new ImageProcessControllerImplV2(model, input);
    c.run();
  }
}
