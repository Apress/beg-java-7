import java.awt.EventQueue;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class BIP extends JFrame
{
   Picture pic;
   BufferedImage bi;
   BIP()
   {
      super("Buffered Image Processing");
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      ImageIcon ii = new ImageIcon("rose.jpg");
      bi = new BufferedImage(ii.getIconWidth(), ii.getIconHeight(),
                             BufferedImage.TYPE_INT_RGB);
      Graphics2D g2d = bi.createGraphics();
      g2d.drawImage(ii.getImage(), 0, 0, null);
      g2d.dispose();
      JMenuBar mb = new JMenuBar();
      JMenu m = new JMenu("Process");
      JMenuItem mi = new JMenuItem("Undo");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 pic.setImage(bi);
                              }
                           });
      m.add(mi);
      m.addSeparator();
      mi = new JMenuItem("Blur");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 float ninth = 1.0f/9.0f;
                                 float[] blurKernel =
                                 {
                                    ninth, ninth, ninth,
                                    ninth, ninth, ninth,
                                    ninth, ninth, ninth
                                 };
                                 BufferedImageOp blurOp =
                                    new ConvolveOp(new Kernel(3, 3,
                                                              blurKernel));
                                 BufferedImage biRes = blurOp.filter(bi, null);
                                 pic.setImage(biRes);
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Blur More");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 float tfth = 1.0f/25.0f;
                                 float[] blurKernel =
                                 {
                                    tfth, tfth, tfth, tfth, tfth,
                                    tfth, tfth, tfth, tfth, tfth,
                                    tfth, tfth, tfth, tfth, tfth,
                                    tfth, tfth, tfth, tfth, tfth,
                                    tfth, tfth, tfth, tfth, tfth
                                 };
                                 BufferedImageOp blurOp =
                                    new ConvolveOp(new Kernel(5, 5,
                                                              blurKernel));
                                 BufferedImage biRes = blurOp.filter(bi, null);
                                 pic.setImage(biRes);
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Edge");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 float[] edgeKernel =
                                 {
                                     0.0f, -1.0f,  0.0f,
                                    -1.0f,  4.0f, -1.0f,
                                     0.0f, -1.0f,  0.0f
                                 };
                                 BufferedImageOp edgeOp =
                                    new ConvolveOp(new Kernel(3, 3, edgeKernel));
                                 BufferedImage biRes = edgeOp.filter(bi, null);
                                 pic.setImage(biRes);
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Negative");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 short[] invert = new short[256];
                                 for (int i = 0; i < invert.length; i++)
                                    invert[i] = (short) (255-i);
                                 BufferedImageOp invertOp =
                                    new LookupOp(new ShortLookupTable(0,
                                                                      invert),
                                                 null);
                                 BufferedImage biRes = invertOp.filter(bi,
                                                                       null);
                                 pic.setImage(biRes);
                              }
                           });
      m.add(mi);
      mi = new JMenuItem("Sharpen");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 float[] sharpenKernel =
                                 {
                                     0.0f, -1.0f,  0.0f,
                                    -1.0f,  5.0f, -1.0f,
                                     0.0f, -1.0f,  0.0f
                                 };
                                 BufferedImageOp sharpenOp =
                                    new ConvolveOp(new Kernel(3, 3,
                                                              sharpenKernel));
                                 BufferedImage biRes = sharpenOp.filter(bi,
                                                                        null);
                                 pic.setImage(biRes);
                              }
                           });
      m.add(mi);
      m.addSeparator();
      mi = new JMenuItem("Exit");
      mi.addActionListener(new ActionListener()
                           {
                              @Override
                              public void actionPerformed(ActionEvent ae)
                              {
                                 dispose();
                              }
                           });
      m.add(mi);
      mb.add(m);
      setJMenuBar(mb);
      setContentPane(pic = new Picture(bi.getWidth(), bi.getHeight(), bi));
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
                         new BIP();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
