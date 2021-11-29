package view.component;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A view component that contains all the operations one could perform on a single picture.
 */
public class ControlsBox extends JPanel {

  /**
   * Creates a new instance of the box with controls.
   */
  public ControlsBox() {
    setSize(200, 600);
    setLayout(new FlowLayout());

    JButton btnLoad = new JButton("Load image");
    JButton btnSave = new JButton("Save image");

    add(btnLoad);
    add(btnSave);

    setVisible(true);
  }
}
