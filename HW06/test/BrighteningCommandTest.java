import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import controller.command.visual.AdjustBrightnessCommand;
import controller.command.macro.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;

/**
 * Class that tests the {@code brighten} command.
 */
public class BrighteningCommandTest extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new AdjustBrightnessCommand(true);
  }

  @Override
  public ArrayList<Queue<String>> provideInputs() {
    ArrayList<Queue<String>> list = new ArrayList<>();
    list.add(new ArrayDeque<>(Arrays.asList("50", "testImg", outputName())));
    return list;
  }

  @Override
  public ArrayList<Queue<String>> errorInputs() {
    ArrayList<Queue<String>> list = new ArrayList<>();
    list.add(new ArrayDeque<>(Arrays.asList("50", outputName())));
    list.add(new ArrayDeque<>(Arrays.asList("50", "testImg2", outputName())));
    list.add(new ArrayDeque<>(Arrays.asList("50", "testImg", outputName(), "asdrf")));
    list.add(new ArrayDeque<>(Arrays.asList("5.2", "testImg", outputName())));
    return list;
  }

  @Override
  public ImageFile[] provideOutputs() {
    return new ImageFile[]{new ImageFileImpl(new Color[][]{
            {new Color(60, 61, 62), new Color(70, 71, 72), new Color(80, 81, 82)},
            {new Color(90, 91, 92), new Color(100, 101, 102), new Color(110, 111, 112)}})};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Brightened image (value: 50) of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
