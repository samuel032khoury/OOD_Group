package view;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import view.component.ControlsBox;
import view.component.ImageBox;

/**
 * Describes the main GUI-based interface for the image-operating program.
 */
public class GUIMainView extends JFrame {

  /**
   * Creates a new instance of the GUI view.
   */
  public GUIMainView(String imagePath) {
    super();
    setTitle("Basic Image Processor");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout()); // temporary, should be group layout later

    // add all the components
    JPanel imageBox = new ImageBox(imagePath); // TODO: add tabs with multiple pictures later
    JPanel controlsBox = new ControlsBox();
    //JPanel histogramBox = new HistogramBox();

    add(imageBox);
    add(controlsBox);
    //add(histogramBox);
  }

  /**
   * Runs and displays the GUI.
   */
  public void run() {
    // display it
    pack();
    setVisible(true);
  }
}
