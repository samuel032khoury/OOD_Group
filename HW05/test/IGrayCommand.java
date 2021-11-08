import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.GreyCommand;
import controller.command.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.operation.SimpleArithmeticChannelOperator;

/**
 * Class that tests the {@code intensity-component} command.
 */
public class IGrayCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new GreyCommand(SimpleArithmeticChannelOperator.Intensity);
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
            {new Color(11, 11, 11), new Color(21, 21, 21), new Color(31, 31, 31)},
            {new Color(41, 41, 41), new Color(51, 51, 51), new Color(61, 61, 61)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Intensity-component image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
