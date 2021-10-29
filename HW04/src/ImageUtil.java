import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

  public static Color[][] readPPMIMG(String filename) {
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
    int maxValue = sc.nextInt();

    Color[][] image = new Color[height][width];

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Color color = new Color(r,g,b);
        image[i][j] = color;
      }
    }
    return image;
  }

  public static void savePPM(String filename, Color[][] img){
    BufferedWriter myWriter = null;
    StringBuilder out = new StringBuilder();

    int height = img.length;
    int width = img[0].length;

    try {
      myWriter = new BufferedWriter(new FileWriter(filename));;
      myWriter.write("P3\n");
      myWriter.write("# ppm - RGB");
      myWriter.write(String.format("%d %d", width, height));
      myWriter.write("PlaceHolderForMax");

    } catch (IOException e) {
      System.out.println("An error occurred");
      e.printStackTrace();
    }

    int maxColor = 0;

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        Color color = img[i][j];
        try {
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();
          assert myWriter != null;
          myWriter.write(red);
          myWriter.write(green);
          myWriter.write(blue);
          maxColor = Math.max(Math.max(maxColor, red), Math.max(green, blue));
        } catch (IOException e) {
            throw new RuntimeException("Can't write");
        }
      }
    }

    try {
      assert myWriter != null;
      myWriter.close();
    } catch (IOException e) {
      throw new RuntimeException("Can't write");
    }

    String tmpFileName = "tmp.ppm";

    BufferedReader br = null;
    BufferedWriter bw = null;
    try {
      br = new BufferedReader(new FileReader(filename));
      bw = new BufferedWriter(new FileWriter(tmpFileName));
      String line;
      while ((line = br.readLine()) != null) {
        if (line.contains("PlaceHolderForMax"))
          line = line.replace("PlaceHolderForMax", ""+maxColor);
        bw.write(line+"\n");
      }
    } catch (Exception e) {
      return;
    } finally {
      try {
        if(br != null)
          br.close();
      } catch (IOException e) {
        //
      }
      try {
        if(bw != null)
          bw.close();
      } catch (IOException e) {
        //
      }
    }
    // Once everything is complete, delete old file..
    File oldFile = new File(filename);
    oldFile.delete();

    // And rename tmp file's name to old file name
    File newFile = new File(tmpFileName);
    newFile.renameTo(oldFile);

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
      String filename = "/Users/eric/Documents/CS stuff/cs3500github/OOD_Group/HW04/exampleRes/Koala.ppm";

      
      Color[][] img = ImageUtil.readPPMIMG(filename);
      savePPM("save.ppm", img);
  }
}

