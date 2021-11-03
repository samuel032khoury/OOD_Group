import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import model.imageFile.ImageFile;
import model.imageFile.ImageFileNoAlpha;
import model.imageFile.ReadOnlyImageFile;
import model.operation.SimpleArithmeticChannelOperator;
import model.operation.SingleChannelOperator;

/**
 * To test the model for the project, including {@link ImageFile} model
 * and {@link model.library.ImageLibModel} model.
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

  @Before
  public void setUp() {
    c1 = new Color(0, 0, 0);
    c2 = new Color(100, 100, 100);
    c3 = new Color(200, 200, 200);
    c6 = new Color(0, 50, 100);
    c4 = new Color(50, 100, 150);
    c5 = new Color(150, 200, 250);
    c7 = new Color(123, 45, 67);
    c8 = new Color(234, 56, 78);
    c9 = new Color(134, 156, 178);
    loc1 = new Color[]{c1, c2, c3};
    loc2 = new Color[]{c4, c5, c6};
    loc3 = new Color[]{c7, c8, c9};
    imgL = new Color[][]{loc1, loc2, loc3};
    imgF = new ImageFileNoAlpha(imgL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullImage() {
    new ImageFileNoAlpha(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNegMaxVal() {
    new ImageFileNoAlpha(this.imgL, -100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullColorRow() {
    Color[][] imgLAlt = new Color[][]{this.loc1, this.loc2, null};
    new ImageFileNoAlpha(imgLAlt);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestConstructExceptionNullColorEle() {
    this.loc3 = new Color[]{c7, null, c9};
    Color[][] imgLAlt = new Color[][]{this.loc1, this.loc2, this.loc3};
    new ImageFileNoAlpha(imgLAlt);
  }

  @Test
  public void TestVertiFlip() {
    Color[][] expected = new Color[][]{this.loc3, this.loc2, this.loc1};
    ReadOnlyImageFile actual = this.imgF.vertiFlip();
    assertTrue(sameContents(expected, actual));
  }

  @Test
  public void TestHorizFlip() {
    Color[] loc1Flip = new Color[]{this.c3, this.c2, this.c1};
    Color[] loc2Flip = new Color[]{this.c6, this.c5, this.c4};
    Color[] loc3Flip = new Color[]{this.c9, this.c8, this.c7};
    Color[][] expected = new Color[][]{loc1Flip, loc2Flip, loc3Flip};
    ReadOnlyImageFile actual = this.imgF.horizFlip();
    assertTrue(sameContents(expected, actual));
  }

  @Test
  public void TestBrighten() {
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
    Color[][] expected = new Color[][]{loc1Brightened, loc2Brightened, loc3Brightened};
    ReadOnlyImageFile actual = imgF.brighten(50);
    assertTrue(sameContents(expected, actual));
  }

  @Test
  public void TestDarken() {
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
    Color[][] expected = new Color[][]{loc1Darkened, loc2Darkened, loc3Darkened};
    ReadOnlyImageFile actual = imgF.darken(50);
    assertTrue(sameContents(expected, actual));
  }

  @Test
  public void TestGreyScale() {
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
    Color[][] expectedRed = new Color[][]{loc1Red, loc2Red, loc3Red};
    ReadOnlyImageFile actualRed = imgF.greyscale(SingleChannelOperator.Red);
    assertTrue(sameContents(expectedRed, actualRed));

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
    Color[][] expectedGreen = new Color[][]{loc1Green, loc2Green, loc3Green};
    ReadOnlyImageFile actualGreen = imgF.greyscale(SingleChannelOperator.Green);
    assertTrue(sameContents(expectedGreen, actualGreen));

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
    Color[][] expectedBlue = new Color[][]{loc1Blue, loc2Blue, loc3Blue};
    ReadOnlyImageFile actualBlue = imgF.greyscale(SingleChannelOperator.Blue);
    assertTrue(sameContents(expectedBlue, actualBlue));

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
    Color[][] expectedValue = new Color[][]{loc1Value, loc2Value, loc3Value};
    ReadOnlyImageFile actualValue = imgF.greyscale(SimpleArithmeticChannelOperator.Value);
    assertTrue(sameContents(expectedValue, actualValue));

    Color c1Intensity = new Color(0, 0, 0);
    Color c2Intensity = new Color(100, 100, 100);
    Color c3Intensity = new Color(200, 200, 200);
    Color c6Intensity = new Color(50, 50, 50);
    Color c4Intensity = new Color(100, 100, 100);
    Color c5Intensity = new Color(200, 200, 200);
    Color c7Intensity = new Color(78, 78, 78);
    Color c8Intensity = new Color(122, 122, 122);
    Color c9Intensity = new Color(156, 156, 156);
    Color[] loc1Intensity = new Color[]{c1Intensity, c2Intensity, c3Intensity};
    Color[] loc2Intensity = new Color[]{c4Intensity, c5Intensity, c6Intensity};
    Color[] loc3Intensity = new Color[]{c7Intensity, c8Intensity, c9Intensity};
    Color[][] expectedIntensity = new Color[][]{loc1Intensity, loc2Intensity, loc3Intensity};
    ReadOnlyImageFile actualIntensity = imgF.greyscale(SimpleArithmeticChannelOperator.Intensity);
    assertTrue(sameContents(expectedIntensity, actualIntensity));

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
    Color[][] expectedLuma = new Color[][]{loc1Luma, loc2Luma, loc3Luma};
    ReadOnlyImageFile actualLuma = imgF.greyscale(SimpleArithmeticChannelOperator.Luma);
    assertTrue(sameContents(expectedLuma, actualLuma));
  }

  @Test
  public void TestGetHeight() {
    assertEquals(3, imgF.getHeight());
    Color[][] imgLAlt = new Color[][]{loc1, loc2, loc3, loc1, loc2, loc3};
    ReadOnlyImageFile imgFAlt = new ImageFileNoAlpha(imgLAlt);
    assertEquals(6, imgFAlt.getHeight());
  }

  @Test
  public void TestGetWidth() {
    assertEquals(3, imgF.getWidth());
    Color[] loc1DoubleLength = new Color[]{c1, c2, c3, c1, c2, c3};
    Color[] loc2DoubleLength = new Color[]{c4, c5, c6, c4, c5, c6};
    Color[] loc3DoubleLength = new Color[]{c7, c8, c9, c7, c8, c9};
    Color[][] imgLAlt = new Color[][]{loc1DoubleLength, loc2DoubleLength, loc3DoubleLength};
    ReadOnlyImageFile imgFAlt = new ImageFileNoAlpha(imgLAlt);
    assertEquals(6, imgFAlt.getWidth());
  }

  @Test
  public void TestAlpha() {
    ReadOnlyImageFile imgFAlt = new ImageFileNoAlpha(this.imgL, 20);
    assertFalse(imgF.alpha());
    assertFalse(imgFAlt.alpha());
  }

  @Test
  public void TestGetMaxColorVal() {
    assertEquals(255, imgF.getMaxColorVal());
    ReadOnlyImageFile imgFAlt = new ImageFileNoAlpha(this.imgL, 20);
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

  private boolean sameContents(Color[][] imgL, ReadOnlyImageFile imgF) {
    boolean same = true;
    for (int i = 0; i < imgF.getHeight(); i++) {
      for (int j = 0; j < imgF.getWidth(); j++) {
        same = same & imgF.getColorAt(i, j).equals(imgL[i][j]);
      }
    }
    return same;
  }
}
