import java.awt.*;

import javax.swing.*;

class ImagePanel extends JPanel {
   private Image img;

   public ImagePanel(String imgStr) {
      this.img = new ImageIcon(imgStr).getImage();
      Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
      setSize(size);
      setLayout(null);
   }

   public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
   }
}