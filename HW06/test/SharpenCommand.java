import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.color.FilterCommand;
import controller.command.macro.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.operation.opertor.filter.SimpleFilterOperator;

/**
 * Class that tests the {@code blue-component} command.
 */
public class SharpenCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new FilterCommand(SimpleFilterOperator.Sharpening);
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
    return new ImageFile[]{new ImageFileImpl(new Color[][]{
            {new Color(25, 26, 28), new Color(66, 67, 71), new Color(56, 56, 58)},
            {new Color(47, 48, 51), new Color(89, 90, 93), new Color(78, 78, 81)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Sharpening image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
