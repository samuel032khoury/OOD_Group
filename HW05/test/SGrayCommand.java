import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.color.GreyCommand;
import controller.command.color.TintingCommand;
import controller.command.macro.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.operation.opertor.colortrans.SingleChannelGreyscaleOperator;
import model.operation.opertor.colortrans.TiltingOperator;

/**
 * Class that tests the {@code red-component} command.
 */
public class SGrayCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new TintingCommand(TiltingOperator.Sepia);
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
            {new Color(14, 13, 10), new Color(28, 25, 19), new Color(41, 37, 28)},
            {new Color(55, 49, 38), new Color(68, 61, 47), new Color(82, 73, 57)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Sepia image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
