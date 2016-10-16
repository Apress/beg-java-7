import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

class Picture extends JComponent
{
   private Image image;
   private Dimension d;
   Picture(int width, int height, Image image)
   {
      this.image = image;
      d = new Dimension(300, 300);
   }
   @Override
   public Dimension getPreferredSize()
   {
      return d;
   }
   @Override
   public void paint(Graphics g)
   {
      g.drawImage(image, 0, 0, this);
   }
   void setImage(Image image)
   {
      this.image = image;
      repaint();
   }
}
