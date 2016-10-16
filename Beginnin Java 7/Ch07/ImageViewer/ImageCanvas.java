import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

class ImageCanvas extends Canvas
{
   private Image image;
   @Override
   public void paint(Graphics g)
   {
      // drawImage() does nothing when image contains the null reference.
      g.drawImage(image, 0, 0, null);
   }
   void setImage(Image image)
   {
      MediaTracker mt = new MediaTracker(this);
      mt.addImage(image, 1);
      try
      {
         mt.waitForID(1);
      }
      catch (InterruptedException ie)
      {
         assert false;
      }
      setPreferredSize(new Dimension(image.getWidth(null),
                                     image.getHeight(null)));
      this.image = image;
   }
}
