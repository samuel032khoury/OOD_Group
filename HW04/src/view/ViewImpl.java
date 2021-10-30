package view;


import java.io.IOException;

public class ViewImpl implements IView {

  Appendable output;

  public ViewImpl(Appendable output) {
    output = output;
  }

  @Override
  public void renderMessage(String message) {
    try {
      this.output.append(message);
    } catch(IOException e) {
      throw new RuntimeException("Can't write");
    }

  }
}
