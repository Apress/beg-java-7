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
   }
   @Override
   public Dimension getPreferredSize()
   {
      return d;
   }
   @Override
   public void paint(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
      int width = getWidth();
      int height = getHeight();
      g.setColor(invert ? Color.BLACK : Color.WHITE);
      g.fillRect(0, 0, width, height);
      g.setColor(invert ? Color.WHITE : Color.BLACK);
      for (int y = 0; y < height; y += 5)
         for (int x = 0; x < width; x += 5)
            g.drawLine(x, y, width-x, height-y);
      g.setColor(Color.YELLOW);
      g.setFont(f);
      FontMetrics fm = g.getFontMetrics();
      int strwid = fm.stringWidth(title);
      g.drawString(title, (width-strwid)/2, height/2);
      g.setColor(Color.RED);
      strwid = fm.stringWidth(title);
      g.drawString(title, (width-strwid)/2+3, height/2+3);
      g.setColor(Color.GREEN);
      g.fillOval(10, 10, 50, 50);
      g.setColor(Color.BLUE);
      g.fillRect(width-60, height-60, 50, 50);
   }
}
