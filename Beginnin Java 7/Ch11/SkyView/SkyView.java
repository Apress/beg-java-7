import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sdss.skyserver.ImgCutout;
import org.sdss.skyserver.ImgCutoutSoap;

class SkyView extends JFrame
{
   final static int IMAGE_WIDTH = 300;
   final static int IMAGE_HEIGHT = 300;
   static ImgCutoutSoap imgcutoutsoap;
   SkyView()
   {
      super("SkyView");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setContentPane(createContentPane());
      pack();
      setResizable(false);
      setVisible(true);
   }
   JPanel createContentPane()
   {
      JPanel pane = new JPanel(new BorderLayout(10, 10));
      pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      final JLabel lblImage = new JLabel("", JLabel.CENTER);
      lblImage.setPreferredSize(new Dimension(IMAGE_WIDTH+9,
                                                IMAGE_HEIGHT+9));
      lblImage.setBorder(BorderFactory.createEtchedBorder());
      pane.add(new JPanel() {{ add(lblImage); }}, BorderLayout.NORTH);
      JPanel form = new JPanel(new GridLayout(4, 1));
      final JLabel lblRA = new JLabel("Right ascension:");
      int width = lblRA.getPreferredSize().width+20;
      int height = lblRA.getPreferredSize().height;
      lblRA.setPreferredSize(new Dimension(width, height));
      lblRA.setDisplayedMnemonic('R');
      final JTextField txtRA = new JTextField(15);
      lblRA.setLabelFor(txtRA);
      form.add(new JPanel()
               {{
                   add(lblRA); add(txtRA);
                   setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
               }});
      final JLabel lblDec = new JLabel("Declination:");
      lblDec.setPreferredSize(new Dimension(width, height));
      lblDec.setDisplayedMnemonic('D');
      final JTextField txtDec = new JTextField(15);
      lblDec.setLabelFor(txtDec);
      form.add(new JPanel()
               {{
                   add(lblDec); add(txtDec);
                   setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
               }});
      final JLabel lblScale = new JLabel("Scale:");
      lblScale.setPreferredSize(new Dimension(width, height));
      lblScale.setDisplayedMnemonic('S');
      final JTextField txtScale = new JTextField(15);
      lblScale.setLabelFor(txtScale);
      form.add(new JPanel()
               {{
                   add(lblScale); add(txtScale);
                   setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
               }});
      final JLabel lblDO = new JLabel("Drawing options:");
      lblDO.setPreferredSize(new Dimension(width, height));
      lblDO.setDisplayedMnemonic('o');
      final JTextField txtDO = new JTextField(15);
      lblDO.setLabelFor(txtDO);
      form.add(new JPanel()
                {{
                   add(lblDO); add(txtDO);
                   setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
                }});

      pane.add(form, BorderLayout.CENTER);
      final JButton btnGP = new JButton("Get Picture");
      ActionListener al;
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                 try
                 {
                    double ra = Double.parseDouble(txtRA.getText());
                    double dec = Double.parseDouble(txtDec.getText());
                    double scale = Double.parseDouble(txtScale.getText());
                    String dopt = txtDO.getText().trim();
                    byte[] image = imgcutoutsoap.getJpeg(ra, dec, scale,
                                                         IMAGE_WIDTH,
                                                         IMAGE_HEIGHT,
                                                         dopt);
                    lblImage.setIcon(new ImageIcon(image));
                 }
                 catch (Exception exc)
                 {
                    JOptionPane.showMessageDialog(SkyView.this,
                                                  exc.getMessage());
                 }
              }
           };
      btnGP.addActionListener(al);
      pane.add(new JPanel() {{ add(btnGP); }}, BorderLayout.SOUTH);
      return pane;
   }
   public static void main(String[] args)
   {
      ImgCutout imgcutout = new ImgCutout();
      imgcutoutsoap = imgcutout.getImgCutoutSoap();
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new SkyView();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
