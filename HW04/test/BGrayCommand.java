import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import controller.command.GreyCommand;
import controller.command.ICommand;
import model.imageFile.ImageFile;
import model.imageFile.ImageFileNoAlpha;
import model.operation.SingleChannelOperator;

public class BGrayCommand extends ACommandTest{
  @Override
  public ICommand provideCommand() {
    return new GreyCommand(SingleChannelOperator.Blue);
  }

  @Override
  public ArrayList<Queue<String>> provideInputs() {
    ArrayList<Queue<String>> list = new ArrayList<Queue<String>>();
    list.add(new ArrayDeque<String>(Arrays.asList("testImg", outputName())));
    return list;
  }

  @Override
  public ArrayList<Queue<String>> errorInputs() {
    ArrayList<Queue<String>> list = new ArrayList<Queue<String>>();
    list.add(new ArrayDeque<String>(Arrays.asList("50", "testImg", outputName())));
    list.add(new ArrayDeque<String>(Arrays.asList("50", "testImg")));
    list.add(new ArrayDeque<String>(Arrays.asList( "testImg")));
    return list;
  }

  @Override
  public ImageFile[] provideOutputs() {
    return new ImageFile[]{new ImageFileNoAlpha(new Color[][]{
            {new Color(12,12,12), new Color(22,22,22), new Color(32,32,32)},
            {new Color(42,42,42), new Color(52,52,52), new Color(62,62,62)}
    })};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{"> Blue-component image of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
