package view.component;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The visual image box component that contains the image currently displayed.
 */
public class ImageBox extends JPanel {
  /**
   * Generate an image box on GUI with the provided path of the image to render.
   *
   * @param imagePath the path of the image to render.
   */
  public ImageBox(String imagePath) {
    super();

    setSize(600, 600);
    setBorder(BorderFactory.createTitledBorder("Image"));
    setLayout(new GridLayout(1, 0, 10, 10));

    JLabel imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);

    if (!imagePath.isEmpty()) {
      imageLabel.setIcon(new ImageIcon(imagePath));
    } else {
      imageLabel.setText("Load an image to see it!");
    }

    imageScrollPane.setPreferredSize(new Dimension(600, 600));
    add(imageScrollPane);
  }
}
