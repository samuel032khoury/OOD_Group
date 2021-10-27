import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method 
 *  as required.
 */
public class ImageUtil {

  public static BufferedImage readPPMIMG(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return null;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    BufferedImage image = new BufferedImage(width, height, TYPE_INT_ARGB);

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Color color = new Color(r,g,b);
        image.setRGB(j, i, color.getRGB());
      }
    }
    return image;
  }

  public static void savePPM(String filename, BufferedImage img){
    File outputFile = new File(filename);
    FileWriter myWriter = null;
    StringBuilder out = new StringBuilder();
     try {
      if (outputFile.createNewFile()) {
        System.out.println("File created: " + outputFile.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }

    int height = img.getHeight();
    int width = img.getWidth();

    try {
      myWriter = new FileWriter(filename);
      myWriter.write("P3\n");
      myWriter.write("# ppm - RGB");
      myWriter.write(String.format("%d %d", width, height));

    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }



    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int rgb = img.getRGB(j,i);
        Color color = new Color(rgb);
        try {
          myWriter.write(color.getRed());
          myWriter.write(color.getGreen());
          myWriter.write(color.getBlue());
        } catch (IOException e) {

        }
      }
    }



  }


  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file. 
   */
  public static void readPPM(String filename) {
    Scanner sc;
    
    try {
        sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
        System.out.println("File "+filename+ " not found!");
        return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0)!='#') {
            builder.append(s+System.lineSeparator());
        }
    }
    
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token; 

    token = sc.next();
    if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);
    
    for (int i=0;i<height;i++) {
        for (int j=0;j<width;j++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            System.out.println("Color of pixel ("+j+","+i+"): "+ r+","+g+","+b);
        }
    }
  }

  //demo main
  public static void main(String []args) throws IOException {
      String filename;
      
      if (args.length>0) {
          filename = args[0];
      }
      else {
          filename = "/Users/eric/Documents/CS stuff/cs3500github/OOD_Group/HW04/exampleRes/Koala.ppm";
      }
      
      BufferedImage img = ImageUtil.readPPMIMG(filename);
      File outputfile = new File("saved.png");
      ImageIO.write(img, "png", outputfile);
  }
}

