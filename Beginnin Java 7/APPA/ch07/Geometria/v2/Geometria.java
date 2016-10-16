import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.image.BufferedImage;

class Geometria extends Frame
{
   Geometria()
   {
      super("Geometria");
      addWindowListener(new WindowAdapter()
                        {
                           @Override
                           public void windowClosing(WindowEvent we)
                           {
                              dispose();
                           }
                        });
      add(new SplashCanvas());
      pack();
      setResizable(false);
      setVisible(true);
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new Geometria();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
class SplashCanvas extends Canvas
{
   private Dimension d;
   private Font f;
   private String title;
   private boolean invert; // defaults to false (no invert)
   private BufferedImage bi, biInvert;
   SplashCanvas()
   {
      d = new Dimension(250, 250);
      f = new Font("Arial", Font.BOLD, 50);
      title = "Geometria";
      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mouseClicked(MouseEvent me)
                          {
                             invert = !invert;
                             repaint();
                          }
                       });
      bi = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
      biInvert = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
      makeImage(bi, false);
      makeImage(biInvert, true);
   }
   @Override
   public Dimension getPreferredSize()
   {
      return d;
   }
   private void makeImage(BufferedImage bi, boolean invert)
   {
      Graphics2D g2d = bi.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
      int width = d.width;
      int height = d.height;
      g2d.setColor(Color.WHITE);
      g2d.fillRect(0, 0, width, height);
      g2d.setColor(Color.BLACK);
      if (invert)
      {
         g2d.fillRect(0, 0, width, height);
         g2d.setColor(Color.WHITE);
      }
      for (int y = 0; y < height; y += 5)
         for (int x = 0; x < width; x += 5)
            g2d.drawLine(x, y, width-x, height-y);
      g2d.setColor(Color.YELLOW);
      g2d.setFont(f);
      FontMetrics fm = g2d.getFontMetrics();
      int strwid = fm.stringWidth(title);
      g2d.drawString(title, (width-strwid)/2, height/2);
      g2d.setColor(Color.RED);
      strwid = fm.stringWidth(title);
      g2d.drawString(title, (width-strwid)/2+3, height/2+3);
      g2d.setColor(Color.GREEN);
      g2d.fillOval(10, 10, 50, 50);
      g2d.setColor(Color.BLUE);
      g2d.fillRect(width-60, height-60, 50, 50);
      g2d.dispose();
   }
   @Override
   public void paint(Graphics g)
   {
      g.drawImage((invert) ? biInvert : bi, 0, 0, null);
   }
}
