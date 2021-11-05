import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.GreyCommand;
import controller.command.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileNoAlpha;
import model.operation.SimpleArithmeticChannelOperator;

/**
 * Class that tests the {@code luma-component} command.
 */
public class LGrayCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new GreyCommand(SimpleArithmeticChannelOperator.Luma);
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
            {new Color(10, 10, 10), new Color(20, 20, 20), new Color(30, 30, 30)},
            {new Color(40, 40, 40), new Color(50, 50, 50), new Color(60, 60, 60)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Luma-component image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}