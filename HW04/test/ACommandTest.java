import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import controller.command.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileNoAlpha;
import model.library.ImageLibModel;
import model.library.ImageLibModelImpl;
import view.IImageProcessView;
import view.SimpleImageProcessViewImpl;

import java.util.ArrayList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Abstract testing class for Command Processing.
 */
public abstract class ACommandTest {


  public abstract ICommand provideCommand();

  public abstract ArrayList<Queue<String>> provideInputs();

  public abstract ArrayList<Queue<String>> errorInputs();

  public abstract ImageFile[] provideOutputs();

  public abstract String[] viewOutputs();

  public abstract String outputName();


  ImageLibModel model;
  ImageFile img;
  IImageProcessView view;
  Appendable out;

  @Before
  public void setUp() {

    img = new ImageFileNoAlpha(new Color[][]{
            {new Color(10, 11, 12), new Color(20, 21, 22), new Color(30, 31, 32)},
            {new Color(40, 41, 42), new Color(50, 51, 52), new Color(60, 61, 62)}}
    );


  }

  @Test
  public void testConstructor() {
    try {
      this.provideCommand();
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGoodCommand() {
    ArrayList<Queue<String>> inputs = this.provideInputs();
    ImageFile[] outputs = this.provideOutputs();
    ICommand command = this.provideCommand();
    String[] viewOutputs = this.viewOutputs();

    for (int i = 0; i < inputs.size(); i++) {
      model = new ImageLibModelImpl();
      out = new StringBuilder();
      view = new SimpleImageProcessViewImpl(out, model);
      model.loadImage("testImg", img.copyImage());
      command.execute(this.model, inputs.get(i), this.view);
      ImageFile newImageFile = this.model.get(this.outputName());
      assertEquals(outputs[i], newImageFile);
      assertEquals(viewOutputs[i], out.toString());
    }
  }

  @Test
  public void testExceptions() {
    ArrayList<Queue<String>> inputs = this.errorInputs();
    ImageFile[] outputs = this.provideOutputs();
    ICommand command = this.provideCommand();
    String[] viewOutputs = this.viewOutputs();

    for (Queue<String> input : inputs) {
      model = new ImageLibModelImpl();
      out = new StringBuilder();
      view = new SimpleImageProcessViewImpl(out, model);
      model.loadImage("testImg", img.copyImage());
      try {
        command.execute(this.model, input, this.view);
        System.out.println("fail at " + input);
        fail();
      } catch (IllegalStateException e) {
        System.out.println("you passed");
      }

    }
  }
}

