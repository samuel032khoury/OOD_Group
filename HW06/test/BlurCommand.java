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
public class BlurCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new FilterCommand(SimpleFilterOperator.Blur);
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
            {new Color(12, 12, 13), new Color(20, 20, 21), new Color(19, 19, 20)},
            {new Color(18, 18, 18), new Color(27, 27, 29), new Color(25, 25, 26)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Blur image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
