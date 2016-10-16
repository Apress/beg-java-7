import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class Graphics2DAttribDemo
{
   static JMenuBar createMenu(final DemoPane dp)
   {
      JMenuBar mb = new JMenuBar();
      JMenu m = new JMenu("Demo");
      JMenuItem mi = new JMenuItem("Paint");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doPaintDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Stroke");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doStrokeDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Font");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doFontDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Transformation");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doTransformationDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Composite Rule");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doCompositeRuleDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Clipping Shape");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doClippingShapeDemo();
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Rendering Hints");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dp.doRenderingHintsDemo();
                              }
                           });
      m.add(mi);
      mb.add(m);
      return mb;
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         JFrame f = new JFrame("Graphics2D Attributes Demo");
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         DemoPane dp = new DemoPane();
                         f.setContentPane(dp);
                         f.setJMenuBar(createMenu(dp));
                         f.pack();
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
final class DemoPane extends JComponent
{
   private Dimension d;
   private ClippingShapeDemo clipd;
   private CompositeRuleDemo compd;
   private FontDemo fd;
   private PaintDemo pd;
   private RenderingHintsDemo rhd;
   private StrokeDemo sd;
   private TransformationDemo td;
   private Demo demo;
   DemoPane()
   {
      d = new Dimension(350, 350);
      clipd = new ClippingShapeDemo();
      compd = new CompositeRuleDemo();
      fd = new FontDemo();
      pd = new PaintDemo();
      rhd = new RenderingHintsDemo();
      sd = new StrokeDemo();
      td = new TransformationDemo();
      demo = new DefaultDemo();
   }
   @Override
   public Dimension getPreferredSize()
   {
      return d;
   }
   @Override
   public void paint(Graphics g)
   {
      demo.paint((Graphics2D) g, getWidth(), getHeight());
   }
   void doClippingShapeDemo()
   {
      demo = clipd;
      repaint();
   }
   void doCompositeRuleDemo()
   {
      demo = compd;
      repaint();
   }
   void doFontDemo()
   {
      demo = fd;
      repaint();
   }
   void doPaintDemo()
   {
      demo = pd;
      repaint();
   }
   void doRenderingHintsDemo()
   {
      demo = rhd;
      repaint();
   }
   void doStrokeDemo()
   {
      demo = sd;
      repaint();
   }
   void doTransformationDemo()
   {
      demo = td;
      repaint();
   }
   private interface Demo
   {
      void paint(Graphics2D g, int w, int h);
   }
   private class ClippingShapeDemo implements Demo
   {
      private Polygon polygon;
      ClippingShapeDemo()
      {
         polygon = new Polygon();
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         polygon.addPoint(30, 30);
         polygon.addPoint(w-30, 30);
         polygon.addPoint(w-30, h-30);
         polygon.addPoint(30, h-30);
         g.clip(polygon);
         g.setColor(Color.GREEN);
         g.fillRect(0, 0, w, h);
         polygon.reset();
      }
   }
   private class CompositeRuleDemo implements Demo
   {
      private BufferedImage bi;
      private AlphaComposite[] acRules =
      {
         AlphaComposite.Clear,
         AlphaComposite.Dst,
         AlphaComposite.DstAtop,
         AlphaComposite.DstIn,
         AlphaComposite.DstOut,
         AlphaComposite.DstOver,
         AlphaComposite.Src,
         AlphaComposite.SrcAtop,
         AlphaComposite.SrcIn,
         AlphaComposite.SrcOut,
         AlphaComposite.SrcOver,
         AlphaComposite.Xor
      };
      private String[] ruleNames =
      {
         "CLEAR", "DST", "DST_ATOP", "DST_IN", "DST_OUT", "DST_OVER", "SRC",
         "SRC_ATOP", "SRC_IN", "SRC_OUT", "SRC_OVER", "XOR"
      };
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         if (bi == null || bi.getWidth() != w || bi.getHeight() != h)
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
         Graphics2D g2d = bi.createGraphics();
         g2d.setPaint(new Color(255, 255, 255, 0));
         g2d.fillRect(0, 0, w, h);
         int x = 10, y = 10;
         for (int acRule = 0; acRule < acRules.length; acRule++)
         {
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, 40, 40);
            Composite prev = g.getComposite();
            g2d.setComposite(acRules[acRule]);
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x+20, y+20, 30, 30);
            g2d.setComposite(prev);
            g2d.setColor(Color.BLACK);
            g2d.drawString(ruleNames[acRule], x, y+70);
            x += 80;
            if ((x+80) >= w)
            {
               x = 10;
               y += 80;
            }
         }
         g2d.dispose();
         g.drawImage(bi, 0, 0, null);
      }
   }
   private class DefaultDemo implements Demo
   {
      private String title;
      private AlphaComposite ac;
      private Font font;
      DefaultDemo()
      {
         title = "Graphics2D Attributes Demo";
         ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
         font = new Font("Arial", Font.BOLD, 20);
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         g.setComposite(ac);
         int boxw = 100;
         int boxh = 100;
         g.setPaint(Color.RED);
         g.fillOval((w-boxw)/2, (h-boxh)/2-boxh/2, boxw, boxh);
         g.setPaint(Color.GREEN);
         g.fillOval((w-boxw)/2-boxw/2, (h-boxh)/2, boxw, boxh);
         g.setPaint(Color.BLUE);
         g.fillOval((w-boxw)/2, (h-boxh)/2+boxh/2, boxw, boxh);
         g.setPaint(Color.YELLOW);
         g.fillOval((w-boxw)/2+boxw/2, (h-boxh)/2, boxw, boxh);
         g.setFont(font);
         FontMetrics fm = g.getFontMetrics();
         g.setColor(Color.BLACK);
         g.drawString(title, (w-fm.stringWidth(title))/2, h/2);
      }
   }
   private class FontDemo implements Demo
   {
      private Font font1, font2, font3, font4;
      FontDemo()
      {
         font1 = new Font("Arial", Font.PLAIN, 30);
         font2 = font1.deriveFont(Font.BOLD);
         font3 = font1.deriveFont(Font.ITALIC);
         font4 = font1.deriveFont(Font.BOLD|Font.ITALIC);
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         g.setFont(font1);
         g.drawString("Plain", 10, 30);
         g.setFont(font2);
         g.drawString("Bold", 10, 90);
         g.setFont(font3);
         g.drawString("Italic", 10, 150);
         g.setFont(font4);
         g.drawString("Bold|Italic", 10, 210);
      }
   }
   private class PaintDemo implements Demo
   {
      private BufferedImage bi;
      private TexturePaint tp;
      PaintDemo()
      {
         Image image = Toolkit.getDefaultToolkit().getImage("redbox.jpg");
         MediaTracker mt = new MediaTracker(DemoPane.this);
         mt.addImage(image, 1);
         try
         {
            mt.waitForID(1);
         }
         catch (InterruptedException ie)
         {
         }
         bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
         Graphics2D g2d = bi.createGraphics();
         g2d.drawImage(image, 0, 0, null);
         g2d.dispose();
         tp = new TexturePaint(bi, new Rectangle2D.Double(0, 0, 16, 16));
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setPaint(Color.red);
         g.fillRect(0, 0, w/2, h/2);
         GradientPaint gp = new GradientPaint(w/2, 0, Color.BLUE, w/2, h/2,
                                              Color.GREEN);
         g.setPaint(gp);
         g.fillRect(w/2, 0, w/2, h/2);
         g.setPaint(tp);
         g.fillRect(0, h/2, w, h/2);
      }
   }
   private class RenderingHintsDemo implements Demo
   {
      private Font font;
      RenderingHintsDemo()
      {
         font = new Font("Arial", Font.BOLD, 50);
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setFont(font);
         FontMetrics fm = g.getFontMetrics();
         g.drawString("Aliased", (w-fm.stringWidth("Aliased"))/2, 100);
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         g.drawString("Antialiased", (w-fm.stringWidth("Antialiased"))/2, h-100);
      }
   }
   private class StrokeDemo implements Demo
   {
      private float[] dashSpaces;
      private BasicStroke bs1, bs2, bs3, bs4, bs5, bs6, bs7;
      private Polygon polygon1, polygon2, polygon3;
      StrokeDemo()
      {
         dashSpaces = new float[] { 20.0f, 10.0f, 5.0f, 10.0f };
         bs1 = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                               BasicStroke.JOIN_ROUND, 0.0f, dashSpaces, 0.0f);
         bs2 = new BasicStroke(15.0f, BasicStroke.CAP_BUTT,
                               BasicStroke.JOIN_BEVEL);
         bs3 = new BasicStroke(15.0f, BasicStroke.CAP_ROUND,
                               BasicStroke.JOIN_BEVEL);
         bs4 = new BasicStroke(15.0f, BasicStroke.CAP_SQUARE,
                               BasicStroke.JOIN_BEVEL);
         bs5 = new BasicStroke(15.0f, BasicStroke.CAP_BUTT,
                               BasicStroke.JOIN_BEVEL);
         bs6 = new BasicStroke(15.0f, BasicStroke.CAP_BUTT,
                               BasicStroke.JOIN_MITER);
         bs7 = new BasicStroke(15.0f, BasicStroke.CAP_BUTT,
                               BasicStroke.JOIN_ROUND);
         polygon1 = new Polygon();
         polygon1.addPoint(20, 130);
         polygon1.addPoint(40, 90);
         polygon1.addPoint(60, 130);
         polygon2 = new Polygon();
         polygon2.addPoint(20, 210);
         polygon2.addPoint(40, 170);
         polygon2.addPoint(60, 210);
         polygon3 = new Polygon();
         polygon3.addPoint(20, 290);
         polygon3.addPoint(40, 250);
         polygon3.addPoint(60, 290);
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         Stroke s = g.getStroke();
         g.setStroke(bs1);
         g.drawRect(5, 5, w-10, h-10);
         g.setStroke(bs2);
         g.drawLine(20, 30, 60, 30);
         g.drawString("CAP_BUTT", 110, 30);
         g.setStroke(bs3);
         g.drawLine(20, 50, 60, 50);
         g.drawString("CAP_ROUND", 110, 50);
         g.setStroke(bs4);
         g.drawLine(20, 70, 60, 70);
         g.drawString("CAP_SQUARE", 110, 70);
         g.setStroke(s);
         g.setColor(Color.RED);
         g.drawLine(60, 30, 110, 30);
         g.drawLine(60, 50, 110, 50);
         g.drawLine(60, 70, 110, 70);
         g.setColor(Color.BLACK);
         g.setStroke(bs5);
         g.drawPolygon(polygon1);
         g.drawString("CAP_BUTT/JOIN_BEVEL", 110, 130);
         g.setStroke(bs6);
         g.drawPolygon(polygon2);
         g.drawString("CAP_BUTTON/JOIN_MITER", 110, 210);
         g.setStroke(bs7);
         g.drawPolygon(polygon3);
         g.drawString("CAP_BUTTON/JOIN_ROUND", 110, 290);
      }
   }
   private class TransformationDemo implements Demo
   {
      private GradientPaint gp;
      TransformationDemo()
      {
         gp = new GradientPaint(75, 75, Color.GREEN, 175, 175, Color.RED);
      }
      @Override
      public void paint(Graphics2D g, int w, int h)
      {
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
         g.setColor(Color.YELLOW);
         g.fillRect(0, 0, w, h);
         g.setPaint(Color.BLUE);
         g.fillRect(75, 75, 100, 100);
         g.setPaint(gp);
         AffineTransform transform = g.getTransform();
         g.rotate(Math.toRadians(45.0), 75, 75);
         g.fillRect(75, 75, 100, 100);
         g.setTransform(transform);
         g.shear(0.1, 0.0);
         g.fillRect(w-175, h-175, 100, 100);
      }
   }
}
