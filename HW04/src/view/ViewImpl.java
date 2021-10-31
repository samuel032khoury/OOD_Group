package view;


import java.io.IOException;

import model.ImageLibState;

public class ViewImpl implements IView {

  Appendable output;
  ImageLibState model;

  public ViewImpl(Appendable output, ImageLibState model) {
    output = output;
    model = model;
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
