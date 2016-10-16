import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class RandomCircles extends Frame
{
   RandomCircles()
   {
      super("Random Circles");
      addWindowListener(new WindowAdapter()
                        {
                           @Override
                           public void windowClosing(WindowEvent we)
                           {
                              dispose();
                           }
                        });
      add(new RCCanvas());
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
                         new RandomCircles();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
class RCCanvas extends Canvas
{
   private Dimension d;
   RCCanvas()
   {
      d = new Dimension(250, 250);
      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mouseClicked(MouseEvent me)
                          {
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
      g.setColor(new Color(rnd(256), rnd(256), rnd(256)));
      int extent = 5+rnd(31);
      g.fillOval(rnd(getWidth()), rnd(getHeight()), extent, extent);
   }
   int rnd(int limit)
   {
      return (int) (Math.random()*limit);
   }
}
