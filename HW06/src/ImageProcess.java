import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.controller.IImageProcessController;
import controller.controller.ImageProcessControllerGUIImpl;
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
    final ImageLibModel model = new ImageLibModelImpl();

    final Readable input;
    final IImageProcessController controller;

    if (args.length < 1) {
      controller = new ImageProcessControllerGUIImpl(model);
    } else if (args[0].equals("-text")) {
      input = new InputStreamReader(System.in);
      // controller uses default view with System.out as output destination and give model for
      // which it renders.
      controller = new ImageProcessControllerImplV2(model, input);
    } else if (args[0].equals("-file")) {
      try {
        String filePath = args[1];
        input = new FileReader(filePath);
        controller = new ImageProcessControllerImplV2(model, input);
      } catch (IndexOutOfBoundsException e) {
        throw new IllegalArgumentException(
                "Please provide the path of the text-based script file!");
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Unable to find the provided file! Please "
                + "check the name or the path of the file is accurate and try again!");
      }
    } else {
      throw new IllegalArgumentException("Unknown arguments! Use -file to run a "
              + "text-based script, or use -text to enter interactive mode, or use "
              + "GUI by providing no arguments ");
    }

    controller.run();
  }
}
