import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

class CAG
{
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         JFrame f = new JFrame("Constructive Area Geometry");
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         f.setContentPane(new CAGPane());
                         f.pack();
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
final class CAGPane extends JComponent
{
   private Dimension d;
   private Ellipse2D ell1, ell2;
   private Area area1, area2, area3, area4, area5, area6;
   CAGPane()
   {
      d = new Dimension(400, 100);
      ell1 = new Ellipse2D.Double(10.0, 10.0, 40.0, 40.0);
      ell2 = new Ellipse2D.Double(30.0, 10.0, 40.0, 40.0);
      area1 = new Area(ell1);
      area2 = new Area(ell2);
      area3 = new Area((Area) area1.clone());
      area3.add(area2);
      area4 = new Area((Area) area1.clone());
      area4.subtract(area2);
      area5 = new Area((Area) area1.clone());
      area5.intersect(area2);
      area6 = new Area((Area) area1.clone());
      area6.exclusiveOr(area2);
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
      g2d.fill(area1);
      g2d.setColor(Color.GREEN);
      g2d.fill(area2);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Original", 10.0f, 70.0f);
      g2d.translate(75.0, 0.0);
      g2d.setColor(Color.BLUE);
      g2d.fill(area3);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Add", 10.0f, 70.0f);
      g2d.translate(75.0, 0.0);
      g2d.setColor(Color.PINK);
      g2d.fill(area4);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Subtract", 10.0f, 70.0f);
      g2d.translate(75.0, 0.0);
      g2d.setColor(Color.ORANGE);
      g2d.fill(area5);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Intersect", 10.0f, 70.0f);
      g2d.translate(75.0, 0.0);
      g2d.setColor(Color.MAGENTA);
      g2d.fill(area6);
      g2d.setColor(Color.BLACK);
      g2d.drawString("Exclusive Or", 10.0f, 70.0f);
   }
}
