import java.awt.Button;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class TempVerter
{
   static Panel createGUI()
   {
      Panel pnlLayout = new Panel();
      pnlLayout.setLayout(new GridLayout(3, 1));
      Panel pnlTemp = new Panel();
      ((FlowLayout) pnlTemp.getLayout()).setAlignment(FlowLayout.LEFT);
      pnlTemp.add(new Label("Degrees"));
      final TextField txtDegrees = new TextField(10);
      pnlTemp.add(txtDegrees);
      pnlLayout.add(pnlTemp);
      pnlTemp = new Panel();
      ((FlowLayout) pnlTemp.getLayout()).setAlignment(FlowLayout.LEFT);
      pnlTemp.add(new Label("Result"));
      final TextField txtResult = new TextField(30);
      pnlTemp.add(txtResult);
      pnlLayout.add(pnlTemp);
      pnlTemp = new Panel();
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
      Button btnConvertToCelsius = new Button("Convert to Celsius");
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
      Button btnConvertToFahrenheit = new Button("Convert to Fahrenheit");
      btnConvertToFahrenheit.addActionListener(al);
      pnlTemp.add(btnConvertToFahrenheit);
      pnlLayout.add(pnlTemp);
      return pnlLayout;
   }
   static void fixGUI(Frame f)
   {
      Panel pnl = (Panel) f.getComponents()[0];
      Panel pnlRow = (Panel) pnl.getComponents()[0];
      Label l1 = (Label) pnlRow.getComponents()[0];
      pnlRow = (Panel) pnl.getComponents()[1];
      Label l2 = (Label) pnlRow.getComponents()[0];
      l1.setPreferredSize(l2.getPreferredSize());
      pnlRow = (Panel) pnl.getComponents()[2];
      Button btnToC = (Button) pnlRow.getComponents()[0];
      Button btnToF = (Button) pnlRow.getComponents()[1];
      btnToC.setPreferredSize(btnToF.getPreferredSize());
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         final Frame f = new Frame("TempVerter");
                         f.addWindowListener(new WindowAdapter()
                         {
                              @Override
                              public void windowClosing(WindowEvent we)
                              {
                                 f.dispose();
                              }
                         });
                         f.add(createGUI());
                         f.pack();
                         fixGUI(f);
                         f.setResizable(false);
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
