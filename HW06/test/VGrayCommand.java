import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import controller.command.color.GreyCommand;

import controller.command.macro.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.operation.opertor.colortrans.SimpleArithmeticGreyscaleOperator;

/**
 * Class that tests the {@code value-component} command.
 */
public class VGrayCommand extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new GreyCommand(SimpleArithmeticGreyscaleOperator.Value);
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
            {new Color(12, 12, 12), new Color(22, 22, 22), new Color(32, 32, 32)},
            {new Color(42, 42, 42), new Color(52, 52, 52), new Color(62, 62, 62)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{
        "> Value-component image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
