import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.imagefile.ReadOnlyImageFile;
import model.operation.color.FilterOperation;
import model.operation.color.GreyscaleOperation;
import model.operation.OperationUtil;
import model.operation.color.TintingOperation;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;
import model.operation.opertor.colortrans.SingleChannelGreyscaleOperator;
import model.operation.opertor.colortrans.TintingOperator;
import model.operation.opertor.filter.SimpleFilterOperator;
import model.operation.visual.BrightnessOperation;
import model.operation.visual.FlipOperation;

/**
 * To test the model for the project, including {@link ImageFile} model and {@link
 * model.library.ImageLibModel} model.
 */
public class ModelTest {
  Color c1;
  Color c2;
  Color c3;
  Color c6;
  Color c4;
  Color c5;
  Color c7;
  Color c8;
  Color c9;
  Color[] loc1;
  Color[] loc2;
  Color[] loc3;
  Color[][] imgL;
  ImageFile imgF;

  Color[][] expectedRedArray;
  Color[][] expectedGreenArray;
  Color[][] expectedBlueArray;
  Color[][] expectedValueArray;
  Color[][] expectedIntensityArray;
  Color[][] expectedLumaArray;
  Color[][] expectedSepiaArray;
  Color[][] expectedBrighten50Array;
  Color[][] expectedDarken50Array;
  Color[][] expectedBlurArray;
  Color[][] expectedSharpenArray;

  @Before
  public void setUp() {
    this.c1 = new Color(0, 0, 0);
    this.c2 = new Color(100, 100, 100);
    this.c3 = new Color(200, 200, 200);
    this.c6 = new Color(0, 50, 100);
    this.c4 = new Color(50, 100, 150);
    this.c5 = new Color(150, 200, 250);
    this.c7 = new Color(123, 45, 67);
    this.c8 = new Color(234, 56, 78);
    this.c9 = new Color(134, 156, 178);
    this.loc1 = new Color[]{c1, c2, c3};
    this.loc2 = new Color[]{c4, c5, c6};
    this.loc3 = new Color[]{c7, c8, c9};
    this.imgL = new Color[][]{loc1, loc2, loc3};
    this.imgF = new ImageFileImpl(imgL, 255, false);

    Color c1Red = new Color(0, 0, 0);
    Color c2Red = new Color(100, 100, 100);
    Color c3Red = new Color(200, 200, 200);
    Color c6Red = new Color(0, 0, 0);
    Color c4Red = new Color(50, 50, 50);
    Color c5Red = new Color(150, 150, 150);
    Color c7Red = new Color(123, 123, 123);
    Color c8Red = new Color(234, 234, 234);
    Color c9Red = new Color(134, 134, 134);
    Color[] loc1Red = new Color[]{c1Red, c2Red, c3Red};
    Color[] loc2Red = new Color[]{c4Red, c5Red, c6Red};
    Color[] loc3Red = new Color[]{c7Red, c8Red, c9Red};
    this.expectedRedArray = new Color[][]{loc1Red, loc2Red, loc3Red};

    Color c1Green = new Color(0, 0, 0);
    Color c2Green = new Color(100, 100, 100);
    Color c3Green = new Color(200, 200, 200);
    Color c6Green = new Color(50, 50, 50);
    Color c4Green = new Color(100, 100, 100);
    Color c5Green = new Color(200, 200, 200);
    Color c7Green = new Color(45, 45, 45);
    Color c8Green = new Color(56, 56, 56);
    Color c9Green = new Color(156, 156, 156);
    Color[] loc1Green = new Color[]{c1Green, c2Green, c3Green};
    Color[] loc2Green = new Color[]{c4Green, c5Green, c6Green};
    Color[] loc3Green = new Color[]{c7Green, c8Green, c9Green};
    this.expectedGreenArray = new Color[][]{loc1Green, loc2Green, loc3Green};

    Color c1Blue = new Color(0, 0, 0);
    Color c2Blue = new Color(100, 100, 100);
    Color c3Blue = new Color(200, 200, 200);
    Color c6Blue = new Color(100, 100, 100);
    Color c4Blue = new Color(150, 150, 150);
    Color c5Blue = new Color(250, 250, 250);
    Color c7Blue = new Color(67, 67, 67);
    Color c8Blue = new Color(78, 78, 78);
    Color c9Blue = new Color(178, 178, 178);
    Color[] loc1Blue = new Color[]{c1Blue, c2Blue, c3Blue};
    Color[] loc2Blue = new Color[]{c4Blue, c5Blue, c6Blue};
    Color[] loc3Blue = new Color[]{c7Blue, c8Blue, c9Blue};
    this.expectedBlueArray = new Color[][]{loc1Blue, loc2Blue, loc3Blue};

    Color c1Value = new Color(0, 0, 0);
    Color c2Value = new Color(100, 100, 100);
    Color c3Value = new Color(200, 200, 200);
    Color c6Value = new Color(100, 100, 100);
    Color c4Value = new Color(150, 150, 150);
    Color c5Value = new Color(250, 250, 250);
    Color c7Value = new Color(123, 123, 123);
    Color c8Value = new Color(234, 234, 234);
    Color c9Value = new Color(178, 178, 178);
    Color[] loc1Value = new Color[]{c1Value, c2Value, c3Value};
    Color[] loc2Value = new Color[]{c4Value, c5Value, c6Value};
    Color[] loc3Value = new Color[]{c7Value, c8Value, c9Value};
    this.expectedValueArray = new Color[][]{loc1Value, loc2Value, loc3Value};

    Color c1Intensity = new Color(0, 0, 0);
    Color c2Intensity = new Color(99, 99, 99);
    Color c3Intensity = new Color(199, 199, 199);
    Color c6Intensity = new Color(49, 49, 49);
    Color c4Intensity = new Color(99, 99, 99);
    Color c5Intensity = new Color(199, 199, 199);
    Color c7Intensity = new Color(78, 78, 78);
    Color c8Intensity = new Color(122, 122, 122);
    Color c9Intensity = new Color(155, 155, 155);
    Color[] loc1Intensity = new Color[]{c1Intensity, c2Intensity, c3Intensity};
    Color[] loc2Intensity = new Color[]{c4Intensity, c5Intensity, c6Intensity};
    Color[] loc3Intensity = new Color[]{c7Intensity, c8Intensity, c9Intensity};
    this.expectedIntensityArray = new Color[][]{loc1Intensity, loc2Intensity, loc3Intensity};

    Color c1Luma = new Color(0, 0, 0);
    Color c2Luma = new Color(100, 100, 100);
    Color c3Luma = new Color(200, 200, 200);
    Color c6Luma = new Color(42, 42, 42);
    Color c4Luma = new Color(92, 92, 92);
    Color c5Luma = new Color(192, 192, 192);
    Color c7Luma = new Color(63, 63, 63);
    Color c8Luma = new Color(95, 95, 95);
    Color c9Luma = new Color(152, 152, 152);
    Color[] loc1Luma = new Color[]{c1Luma, c2Luma, c3Luma};
    Color[] loc2Luma = new Color[]{c4Luma, c5Luma, c6Luma};
    Color[] loc3Luma = new Color[]{c7Luma, c8Luma, c9Luma};
    this.expectedLumaArray = new Color[][]{loc1Luma, loc2Luma, loc3Luma};

    Color c1Sepia = new Color(0, 0, 0);
    Color c2Sepia = new Color(135, 120, 93);
    Color c3Sepia = new Color(255, 240, 187);
    Color c4Sepia = new Color(124, 111, 86);
    Color c5Sepia = new Color(255, 231, 180);
    Color c6Sepia = new Color(57, 51, 39);
    Color c7Sepia = new Color(95, 85, 66);
    Color c8Sepia = new Color(149, 133, 103);
    Color c9Sepia = new Color(206, 183, 143);
    Color[] loc1Sepia = new Color[]{c1Sepia, c2Sepia, c3Sepia};
    Color[] loc2Sepia = new Color[]{c4Sepia, c5Sepia, c6Sepia};
    Color[] loc3Sepia = new Color[]{c7Sepia, c8Sepia, c9Sepia};
    this.expectedSepiaArray = new Color[][]{loc1Sepia, loc2Sepia, loc3Sepia};

    Color c1Brightened = new Color(50, 50, 50);
    Color c2Brightened = new Color(150, 150, 150);
    Color c3Brightened = new Color(250, 250, 250);
    Color c6Brightened = new Color(50, 100, 150);
    Color c4Brightened = new Color(100, 150, 200);
    Color c5Brightened = new Color(200, 250, 255);
    Color c7Brightened = new Color(173, 95, 117);
    Color c8Brightened = new Color(255, 106, 128);
    Color c9Brightened = new Color(184, 206, 228);
    Color[] loc1Brightened = new Color[]{c1Brightened, c2Brightened, c3Brightened};
    Color[] loc2Brightened = new Color[]{c4Brightened, c5Brightened, c6Brightened};
    Color[] loc3Brightened = new Color[]{c7Brightened, c8Brightened, c9Brightened};
    this.expectedBrighten50Array = new Color[][]{loc1Brightened, loc2Brightened, loc3Brightened};

    Color c1Darkened = new Color(0, 0, 0);
    Color c2Darkened = new Color(50, 50, 50);
    Color c3Darkened = new Color(150, 150, 150);
    Color c6Darkened = new Color(0, 0, 50);
    Color c4Darkened = new Color(0, 50, 100);
    Color c5Darkened = new Color(100, 150, 200);
    Color c7Darkened = new Color(73, 0, 17);
    Color c8Darkened = new Color(184, 6, 28);
    Color c9Darkened = new Color(84, 106, 128);
    Color[] loc1Darkened = new Color[]{c1Darkened, c2Darkened, c3Darkened};
    Color[] loc2Darkened = new Color[]{c4Darkened, c5Darkened, c6Darkened};
    Color[] loc3Darkened = new Color[]{c7Darkened, c8Darkened, c9Darkened};
    this.expectedDarken50Array = new Color[][]{loc1Darkened, loc2Darkened, loc3Darkened};

    Color c1Blurred = new Color(27,36,45);
    Color c2Blurred = new Color(71,84,96);
    Color c3Blurred = new Color(71,80,89);
    Color c4Blurred = new Color(65,64,86);
    Color c5Blurred = new Color(111,110,140);
    Color c6Blurred = new Color(79,90,113);
    Color c7Blurred = new Color(74,42,58);
    Color c8Blurred = new Color(110,72,95);
    Color c9Blurred = new Color(71,64,80);
    Color[] loc1Blurred = new Color[]{c1Blurred, c2Blurred, c3Blurred};
    Color[] loc2Blurred = new Color[]{c4Blurred, c5Blurred, c6Blurred};
    Color[] loc3Blurred = new Color[]{c7Blurred, c8Blurred, c9Blurred};
    this.expectedBlurArray = new Color[][]{loc1Blurred, loc2Blurred, loc3Blurred};

    Color c1Sharpened = new Color(0,35,44);
    Color c2Sharpened = new Color(136,204,232);
    Color c3Sharpened = new Color(192,241,251);
    Color c4Sharpened = new Color(158,148,211);
    Color c5Sharpened = new Color(255,255,255);
    Color c6Sharpened = new Color(180,209,255);
    Color c7Sharpened = new Color(177,70,112);
    Color c8Sharpened = new Color(255,156,225);
    Color c9Sharpened = new Color(172,177,220);
    Color[] loc1Sharpened = new Color[]{c1Sharpened, c2Sharpened, c3Sharpened};
    Color[] loc2Sharpened = new Color[]{c4Sharpened, c5Sharpened, c6Sharpened};
    Color[] loc3Sharpened = new Color[]{c7Sharpened, c8Sharpened, c9Sharpened};
    this.expectedSharpenArray = new Color[][]{loc1Sharpened, loc2Sharpened, loc3Sharpened};
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullImage() {
    new ImageFileImpl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNegMaxVal() {
    new ImageFileImpl(this.imgL, -100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullColorRow() {
    Color[][] imgLAlt = new Color[][]{this.loc1, this.loc2, null};
    new ImageFileImpl(imgLAlt);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullColorEle() {
    this.loc3 = new Color[]{c7, null, c9};
    Color[][] imgLAlt = new Color[][]{this.loc1, this.loc2, this.loc3};
    new ImageFileImpl(imgLAlt);
  }

  @Test
  public void TestCopyImage() {
    ImageFile actualCopy = this.imgF.copy();
    assertNotSame(this.imgF, actualCopy);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(this.imgL[i][j], actualCopy.getColorAt(i, j));
        assertNotSame(this.imgL[i][j], actualCopy.getColorAt(i, j));
      }
    }
    assertEquals(imgF.getMaxColorVal(), actualCopy.getMaxColorVal());
    assertEquals(imgF.alpha(), actualCopy.alpha());
  }

  @Test
  public void TestFilterOperation() {
    FilterOperation blur = new FilterOperation(SimpleFilterOperator.Blur);
    FilterOperation sharpen = new FilterOperation(SimpleFilterOperator.Sharpening);

    Color[][] actualBlur = blur.apply(false, imgL);
    Color[][] actualSharpen = sharpen.apply(false, imgL);

    double[][] blurKernel = SimpleFilterOperator.Blur.getKernel();
    double[][] sharpenKernel = SimpleFilterOperator.Sharpening.getKernel();

    Color c00 = imgL[0][0];
    Color c01 = imgL[0][1];
    Color c10 = imgL[1][0];
    Color c11 = imgL[1][1];

    int blur00R = (int) (c00.getRed() * blurKernel[1][1]) + (int) (c01.getRed() * blurKernel[1][2])
            + (int) (c10.getRed() * blurKernel[2][1]) + (int) (c11.getRed() * blurKernel[2][2]);
    assertEquals(OperationUtil.produceValidColorValue(blur00R)[0], actualBlur[0][0].getRed());

    Color c02 = imgL[0][2];
    Color c12 = imgL[1][2];
    Color c20 = imgL[2][0];
    Color c21 = imgL[2][1];
    Color c22 = imgL[2][2];

    int blur11B = (int) (c00.getBlue() * blurKernel[0][0])
            + (int) (c01.getBlue() * blurKernel[0][1]) + (int) (c02.getBlue() * blurKernel[0][2])
            + (int) (c10.getBlue() * blurKernel[1][0]) + (int) (c11.getBlue() * blurKernel[1][1])
            + (int) (c12.getBlue() * blurKernel[1][2]) + (int) (c20.getBlue() * blurKernel[2][0])
            + (int) (c21.getBlue() * blurKernel[2][1]) + (int) (c22.getBlue() * blurKernel[2][2]);
    assertEquals(OperationUtil.produceValidColorValue(blur11B)[0], actualBlur[1][1].getBlue());

    int sharpen11G = (int) (c00.getGreen() * sharpenKernel[1][1])
            + (int) (c01.getGreen() * sharpenKernel[1][2])
            + (int) (c02.getGreen() * sharpenKernel[1][3])
            + (int) (c10.getGreen() * sharpenKernel[2][1])
            + (int) (c11.getGreen() * sharpenKernel[2][2])
            + (int) (c12.getGreen() * sharpenKernel[2][3])
            + (int) (c20.getGreen() * sharpenKernel[3][1])
            + (int) (c21.getGreen() * sharpenKernel[3][2])
            + (int) (c22.getGreen() * sharpenKernel[3][3]);

    assertEquals(OperationUtil.produceValidColorValue(sharpen11G)[0], actualSharpen[1][1].getGreen());
  }

  @Test
  public void TestColorTransformOperation() {
    GreyscaleOperation red = new GreyscaleOperation(SingleChannelGreyscaleOperator.Red);
    GreyscaleOperation green = new GreyscaleOperation(SingleChannelGreyscaleOperator.Green);
    GreyscaleOperation blue = new GreyscaleOperation(SingleChannelGreyscaleOperator.Blue);
    GreyscaleOperation luma = new GreyscaleOperation(SimpleArithmeticGreyscaleOperator.Luma);
    GreyscaleOperation intensity = new GreyscaleOperation(
            SimpleArithmeticGreyscaleOperator.Intensity);
    GreyscaleOperation value = new GreyscaleOperation(SimpleArithmeticGreyscaleOperator.Value);
    TintingOperation sepia = new TintingOperation(TintingOperator.Sepia);

    assertArrayEquals(this.expectedRedArray, red.apply(false, this.imgL));

    assertArrayEquals(this.expectedGreenArray, green.apply(false, this.imgL));

    assertArrayEquals(this.expectedBlueArray, blue.apply(false, this.imgL));

    assertArrayEquals(this.expectedValueArray, value.apply(false, this.imgL));

    assertArrayEquals(this.expectedIntensityArray, intensity.apply(false, this.imgL));

    assertArrayEquals(this.expectedLumaArray, luma.apply(false, this.imgL));

    assertArrayEquals(this.expectedSepiaArray, sepia.apply(false, this.imgL));
  }

  @Test
  public void TestFlipOperation() {
    FlipOperation verti = new FlipOperation(true);
    FlipOperation horiz = new FlipOperation(false);

    Color[][] vertiExpected = new Color[][]{this.loc3, this.loc2, this.loc1};
    Color[][] vertiActual = verti.apply(false, this.imgL);
    Color[] loc1HorizFlip = new Color[]{this.c3, this.c2, this.c1};
    Color[] loc2HorizFlip = new Color[]{this.c6, this.c5, this.c4};
    Color[] loc3HorizFlip = new Color[]{this.c9, this.c8, this.c7};
    Color[][] hoirzExpected = new Color[][]{loc1HorizFlip, loc2HorizFlip, loc3HorizFlip};
    Color[][] hoirzActual = horiz.apply(false, this.imgL);

    assertArrayEquals(vertiExpected, vertiActual);
    assertArrayEquals(hoirzExpected, hoirzActual);
    for (int i = 0; i < 3; i++) {
      assertArrayEquals(vertiExpected[0], vertiActual[0]);
      assertArrayEquals(hoirzExpected[0], hoirzActual[0]);
    }
  }

  @Test
  public void TestBrightenOperation() {
    BrightnessOperation brighten = new BrightnessOperation(true, 50);
    assertArrayEquals(this.expectedBrighten50Array, brighten.apply(false, imgL));
  }

  @Test
  public void TestDarkenOperation() {
    BrightnessOperation darken = new BrightnessOperation(false, 50);
    assertArrayEquals(this.expectedDarken50Array, darken.apply(false, imgL));
  }

  @Test
  public void TestGetHeight() {
    assertEquals(3, imgF.getHeight());
    Color[][] imgLAlt = new Color[][]{loc1, loc2, loc3, loc1, loc2, loc3};
    ReadOnlyImageFile imgFAlt = new ImageFileImpl(imgLAlt);
    assertEquals(6, imgFAlt.getHeight());
  }

  @Test
  public void TestGetWidth() {
    assertEquals(3, imgF.getWidth());
    Color[] loc1DoubleLength = new Color[]{c1, c2, c3, c1, c2, c3};
    Color[] loc2DoubleLength = new Color[]{c4, c5, c6, c4, c5, c6};
    Color[] loc3DoubleLength = new Color[]{c7, c8, c9, c7, c8, c9};
    Color[][] imgLAlt = new Color[][]{loc1DoubleLength, loc2DoubleLength, loc3DoubleLength};
    ReadOnlyImageFile imgFAlt = new ImageFileImpl(imgLAlt);
    assertEquals(6, imgFAlt.getWidth());
  }

  @Test
  public void TestAlpha() {
    ReadOnlyImageFile imgFAlt = new ImageFileImpl(this.imgL, 20);
    assertFalse(imgF.alpha());
    assertFalse(imgFAlt.alpha());
  }

  @Test
  public void TestGetMaxColorVal() {
    assertEquals(255, imgF.getMaxColorVal());
    ReadOnlyImageFile imgFAlt = new ImageFileImpl(this.imgL, 20);
    assertEquals(20, imgFAlt.getMaxColorVal());
  }

  @Test
  public void TestGetColorAt() {
    assertEquals(c1, imgF.getColorAt(0, 0));
    assertEquals(c2, imgF.getColorAt(0, 1));
    assertEquals(c3, imgF.getColorAt(0, 2));
    assertEquals(c4, imgF.getColorAt(1, 0));
    assertEquals(c5, imgF.getColorAt(1, 1));
    assertEquals(c6, imgF.getColorAt(1, 2));
    assertEquals(c7, imgF.getColorAt(2, 0));
    assertEquals(c8, imgF.getColorAt(2, 1));
    assertEquals(c9, imgF.getColorAt(2, 2));
  }

  @Test
  public void TestGetKernelANDGetTransformMatrix() {
    double[][] expectBlurKernel = (new double[][]{{1 / 16.0, 1 / 8.0, 1 / 16.0},
            {1 / 8.0, 1 / 4.0, 1 / 8.0},
            {1 / 16.0, 1 / 8.0, 1 / 16.0}});
    double[][] expectedSharpeningKernel = new double[][]{
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}};
    double[][] expectedValueMatrix = new double[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
    double[][] expectedLumaMatrix = new double[][]{{0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    double[][] expectedIntensityMatrix = new double[][]{{0.3333, 0.3333, 0.3333},
            {0.3333, 0.3333, 0.3333}, {0.3333, 0.3333, 0.3333}};
    double[][] expectedRedMatrix = new double[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}};
    double[][] expectedGreenMatrix = new double[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
    double[][] expectedBlueMatrix = new double[][]{{0, 0, 1}, {0, 0, 1}, {0, 0, 1}};
    for (int i = 0; i < 3; i++) {
      assertArrayEquals(expectBlurKernel[i], SimpleFilterOperator.Blur.getKernel()[i], 0.0);
      assertArrayEquals(expectedValueMatrix[i],
              SimpleArithmeticGreyscaleOperator.Value.getMatrix()[i], 0.0);
      assertArrayEquals(expectedLumaMatrix[i],
              SimpleArithmeticGreyscaleOperator.Luma.getMatrix()[i], 0.0);
      assertArrayEquals(expectedIntensityMatrix[i],
              SimpleArithmeticGreyscaleOperator.Intensity.getMatrix()[i], 0.0);
      assertArrayEquals(expectedRedMatrix[i],
              SingleChannelGreyscaleOperator.Red.getMatrix()[i], 0.0);
      assertArrayEquals(expectedGreenMatrix[i],
              SingleChannelGreyscaleOperator.Green.getMatrix()[i], 0.0);
      assertArrayEquals(expectedBlueMatrix[i],
              SingleChannelGreyscaleOperator.Blue.getMatrix()[i], 0.0);
    }

    for (int i = 0; i < 5; i++) {
      assertArrayEquals(expectedSharpeningKernel[i],
              SimpleFilterOperator.Sharpening.getKernel()[i], 0.0);
    }

  }

  @Test
  public void TestImageFileApply() {
    ImageFile vertiFlippedImageFile = this.imgF.applyOperation(new FlipOperation(true));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(this.imgF.getColorAt(i, j), vertiFlippedImageFile.getColorAt(2 - i, j));
        assertNotSame(this.imgF.getColorAt(i, j), vertiFlippedImageFile.getColorAt(2 - i, j));
      }
    }

    ImageFile horizontalFlippedImageFile = this.imgF.applyOperation(new FlipOperation(false));
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(this.imgF.getColorAt(i, j), horizontalFlippedImageFile.getColorAt(i, 2 - j));
        assertNotSame(this.imgF.getColorAt(i, j), horizontalFlippedImageFile.getColorAt(i, 2 - 1));
      }
    }

    ImageFile brightenedImageFileActual = this.imgF.applyOperation(new BrightnessOperation(true,
            50));
    ImageFile brightenedImageFileExpected = new ImageFileImpl(this.expectedBrighten50Array, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(brightenedImageFileExpected,
            brightenedImageFileActual));

    ImageFile darkenedImageFileActual = this.imgF.applyOperation(new BrightnessOperation(false,
            50));
    ImageFile darkenedImageFileExpected = new ImageFileImpl(this.expectedDarken50Array, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(darkenedImageFileExpected,
            darkenedImageFileActual));

    ImageFile redImageFileActual =
            this.imgF.applyOperation(new GreyscaleOperation(SingleChannelGreyscaleOperator.Red));
    ImageFile redImageFileExpected = new ImageFileImpl(this.expectedRedArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(redImageFileExpected,
            redImageFileActual));

    ImageFile greenImageFileActual =
            this.imgF.applyOperation(new GreyscaleOperation(SingleChannelGreyscaleOperator.Green));
    ImageFile greenImageFileExpected = new ImageFileImpl(this.expectedGreenArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(greenImageFileExpected,
            greenImageFileActual));

    ImageFile blueImageFileActual =
            this.imgF.applyOperation(new GreyscaleOperation(SingleChannelGreyscaleOperator.Blue));
    ImageFile blueImageFileExpected = new ImageFileImpl(this.expectedBlueArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(blueImageFileExpected,
            blueImageFileActual));

    ImageFile valueImageFileActual = this.imgF.applyOperation(
            new GreyscaleOperation(SimpleArithmeticGreyscaleOperator.Value));
    ImageFile valueImageFileExpected = new ImageFileImpl(this.expectedValueArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(valueImageFileExpected,
            valueImageFileActual));

    ImageFile intensityImageFileActual = this.imgF.applyOperation(
            new GreyscaleOperation(SimpleArithmeticGreyscaleOperator.Intensity));
    ImageFile intensityImageFileExpected = new ImageFileImpl(this.expectedIntensityArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(intensityImageFileExpected,
            intensityImageFileActual));

    ImageFile lumaImageFileActual = this.imgF.applyOperation(
            new GreyscaleOperation(SimpleArithmeticGreyscaleOperator.Luma));
    ImageFile lumaImageFileExpected = new ImageFileImpl(this.expectedLumaArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(lumaImageFileExpected,
            lumaImageFileActual));

    ImageFile sepiaImageFileActual =
            this.imgF.applyOperation(new TintingOperation(TintingOperator.Sepia));
    ImageFile sepiaImageFileExpected = new ImageFileImpl(this.expectedSepiaArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(sepiaImageFileExpected,
            sepiaImageFileActual));

    ImageFile blurredImageFileActual =
            this.imgF.applyOperation(new FilterOperation(SimpleFilterOperator.Blur));
    ImageFile blurredImageFileExpected = new ImageFileImpl(this.expectedBlurArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(blurredImageFileExpected,
            blurredImageFileActual));

    ImageFile sharpenedImageFileActual =
            this.imgF.applyOperation(new FilterOperation(SimpleFilterOperator.Sharpening));
    ImageFile sharpenedImageFileExpected = new ImageFileImpl(this.expectedSharpenArray, 255);
    assertTrue(checkEqualAndNotSameFor3By3Example(sharpenedImageFileExpected,
            sharpenedImageFileActual));
  }

  @Test(expected = IllegalStateException.class)
  public void TestPassInvalidOperator1() {
    this.imgF.applyOperation(new GreyscaleOperation(TintingOperator.Sepia));
  }

  @Test(expected = IllegalStateException.class)
  public void TestPassInvalidOperator2() {
    this.imgF.applyOperation(new TintingOperation(SimpleArithmeticGreyscaleOperator.Luma));
  }

  private boolean checkEqualAndNotSameFor3By3Example(ImageFile expect, ImageFile actual) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        boolean equiv = (expect.getColorAt(i, j).equals(actual.getColorAt(i, j)));
        boolean notSame = expect.getColorAt(i, j) != actual.getColorAt(i, j);
        if (!(equiv && notSame)) {
          return false;
        }
      }
    }
    return true;
  }
}
