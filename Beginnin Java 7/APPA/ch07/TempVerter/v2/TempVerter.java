import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

class TempVerter
{
   static JPanel createGUI()
   {
      JPanel pnlLayout = new JPanel();
      pnlLayout.setLayout(new GridLayout(3, 1));
      JPanel pnlTemp = new JPanel();
      ((FlowLayout) pnlTemp.getLayout()).setAlignment(FlowLayout.LEFT);
      pnlTemp.add(new JLabel("Degrees"));
      final JTextField txtDegrees = new JTextField(10);
      txtDegrees.setToolTipText("Enter a numeric value in this field.");
      pnlTemp.add(txtDegrees);
      pnlLayout.add(pnlTemp);
      pnlTemp = new JPanel();
      ((FlowLayout) pnlTemp.getLayout()).setAlignment(FlowLayout.LEFT);
      pnlTemp.add(new JLabel("Result"));
      final JTextField txtResult = new JTextField(30);
      txtResult.setToolTipText("Don't enter anything in this field.");
      pnlTemp.add(txtResult);
      pnlLayout.add(pnlTemp);
      pnlTemp = new JPanel();
      ImageIcon ii = new ImageIcon("thermometer.gif");
      ActionListener al;
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 try
                 {
                    double value = Double.parseDouble(txtDegrees.getText());
                    double result = (value-32.0)*5.0/9.0;
                    txtResult.setText("Celsius = "+result);
                 }
                 catch (NumberFormatException nfe)
                 {
                    System.err.println("bad input");
                 }
              }
           };
      JButton btnConvertToCelsius = new JButton("Convert to Celsius", ii);
      btnConvertToCelsius.setMnemonic(KeyEvent.VK_C);
      btnConvertToCelsius.addActionListener(al);
      pnlTemp.add(btnConvertToCelsius);
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 try
                 {
                    double value = Double.parseDouble(txtDegrees.getText());
                    double result = value*9.0/5.0+32.0;
                    txtResult.setText("Fahrenheit = "+result);
                 }
                 catch (NumberFormatException nfe)
                 {
                    System.err.println("bad input");
                 }
              }
           };
      JButton btnConvertToFahrenheit = new JButton("Convert to Fahrenheit", ii);
      btnConvertToFahrenheit.setMnemonic(KeyEvent.VK_F);
      btnConvertToFahrenheit.addActionListener(al);
      pnlTemp.add(btnConvertToFahrenheit);
      Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
      pnlTemp.setBorder(border);
      pnlLayout.add(pnlTemp);
      return pnlLayout;
   }
   static void fixGUI(Container c)
   {
      JPanel pnlRow = (JPanel) c.getComponents()[0];
      JLabel l1 = (JLabel) pnlRow.getComponents()[0];
      pnlRow = (JPanel) c.getComponents()[1];
      JLabel l2 = (JLabel) pnlRow.getComponents()[0];
      l2.setPreferredSize(l1.getPreferredSize());
      pnlRow = (JPanel) c.getComponents()[2];
      JButton btnToC = (JButton) pnlRow.getComponents()[0];
      JButton btnToF = (JButton) pnlRow.getComponents()[1];
      btnToC.setPreferredSize(btnToF.getPreferredSize());
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         final JFrame f = new JFrame("TempVerter");
                         f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                         Border b = BorderFactory.createEmptyBorder(5, 5, 5, 5);
                         f.getRootPane().setBorder(b);
                         f.setContentPane(createGUI());
                         fixGUI(f.getContentPane());
                         f.pack();
                         f.setResizable(false);
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
