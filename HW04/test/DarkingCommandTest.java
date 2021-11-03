import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import controller.command.AdjustBrightnessCommand;
import controller.command.ICommand;
import model.imagefile.ImageFile;
import model.imagefile.ImageFileNoAlpha;

public class DarkingCommandTest extends ACommandTest {
  @Override
  public ICommand provideCommand() {
    return new AdjustBrightnessCommand(false);
  }

  @Override
  public ArrayList<Queue<String>> provideInputs() {
    ArrayList<Queue<String>> list = new ArrayList<Queue<String>>();
    list.add(new ArrayDeque<String>(Arrays.asList("50", "testImg", outputName())));
    return list;
  }

  @Override
  public ArrayList<Queue<String>> errorInputs() {
    ArrayList<Queue<String>> list = new ArrayList<Queue<String>>();
    list.add(new ArrayDeque<String>(Arrays.asList("50", outputName())));
    list.add(new ArrayDeque<String>(Arrays.asList("50", "testImg2", outputName())));
    list.add(new ArrayDeque<String>(Arrays.asList("50", "testImg", outputName(), "asdrf")));
    list.add(new ArrayDeque<String>(Arrays.asList("5.2", "testImg", outputName())));
    return list;
  }

  @Override
  public ImageFile[] provideOutputs() {
    return new ImageFile[]{new ImageFileNoAlpha(new Color[][]{{new Color(0,0,0), new Color(0,0,0), new Color(0,0,0)},
            {new Color(0,0,0), new Color(0,1,2), new Color(10,11,12)}} )};
  }

  @Override
  public String[] viewOutputs() {
    return new String[]{"> Darkened image (value: 50) of testImg has been created and is named resultImg.\n"};
  }

  @Override
  public String outputName() {
    return "resultImg";
  }
}
