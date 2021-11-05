import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.FlipCommand;
import controller.command.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileNoAlpha;

/**
 * Class tests the {@code vertical-flip} command.
 */
public class VerticalCommandTest extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new FlipCommand(true);
  }

  @Override
  public ArrayList<Queue<String>> provideInputs() {
    ArrayList<Queue<String>> list = new ArrayList<>();
    list.add(new ArrayDeque<>(Arrays.asList("testImg", outputName())));
    return list;
  }

  @Override
  public ArrayList<Queue<String>> errorInputs() {
    ArrayList<Queue<String>> list = new ArrayList<>();
    list.add(new ArrayDeque<>(Arrays.asList("50", "testImg", outputName())));
    list.add(new ArrayDeque<>(Arrays.asList("50", "testImg")));
    list.add(new ArrayDeque<>(List.of("testImg")));
    return list;
  }

  @Override
  public ImageFile[] provideOutputs() {
    return new ImageFile[]{new ImageFileNoAlpha(new Color[][]{
            {new Color(40, 41, 42), new Color(50, 51, 52), new Color(60, 61, 62)},
            {new Color(10, 11, 12), new Color(20, 21, 22), new Color(30, 31, 32)}})};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Vertical flipped image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
