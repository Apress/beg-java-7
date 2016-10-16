import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

class DragRect
{
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         JFrame f = new JFrame("Drag Rectangle");
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         f.setContentPane(new DragRectPane());
                         f.pack();
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
final class DragRectPane extends JComponent
{
   private boolean dragging;
   private double dragX, dragY;
   private Dimension d;
   private RoundRectangle2D rect;
   DragRectPane()
   {
      d = new Dimension(200, 200);
      rect = new RoundRectangle2D.Double(0.0, 0.0, 30.0, 30.0, 10.0, 10.0);
      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent me)
                          {
                             if (!rect.contains(me.getX(), me.getY()))
                                return;
                             dragX = me.getX();
                             dragY = me.getY();
                             dragging = true;
                          }
                          @Override
                          public void mouseReleased(MouseEvent me)
                          {
                             dragging = false;
                          }
                       });
      addMouseMotionListener(new MouseMotionAdapter()
                             {
                                @Override
                                public void mouseDragged(MouseEvent me)
                                {
                                   if (!dragging)
                                      return;
                                   double x = rect.getX()+me.getX()-dragX;
                                   double y = rect.getY()+me.getY()-dragY;
                                   rect.setRoundRect(x, y, rect.getWidth(),
                                                     rect.getHeight(),
                                                     rect.getArcWidth(),
                                                     rect.getArcHeight());
                                   repaint();
                                   dragX = me.getX();
                                   dragY = me.getY();
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
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(Color.RED);
      g2d.fill(rect);
   }
}
