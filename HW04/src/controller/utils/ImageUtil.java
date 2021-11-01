package controller.utils;

import java.io.IOException;
import java.io.InputStreamReader;

import controller.controller.ImageProcessControllerImpl;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.ImageProcessViewImpl;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class  ImageUtil {
  // defined methods can be put here in the future if they are not considered as any part of MVC.
  
  public static void main(String[] args) throws IOException {
    ImageLibModel m = new ImageLibModelImpl();
    ImageProcessControllerImpl c = new ImageProcessControllerImpl(m,
            new InputStreamReader(System.in),
            new ImageProcessViewImpl(System.out, m));
    c.run();
  }
}

