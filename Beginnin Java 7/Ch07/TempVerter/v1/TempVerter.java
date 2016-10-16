import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class TempVerter extends Frame
{
   TempVerter()
   {
      super("TempVerter");
      addWindowListener(new WindowAdapter()
                        {
                           @Override
                           public void windowClosing(WindowEvent we)
                           {
                              System.out.println("window closing");
                              dispose();
                           }
                           @Override
                           public void windowClosed(WindowEvent we)
                           {
                              System.out.println("window closed");
                           }
                        });
      Panel pnlLayout = new Panel();
      pnlLayout.setLayout(new GridLayout(3, 2));
      pnlLayout.add(new Label("Degrees"));
      final TextField txtDegrees = new TextField(10);
      pnlLayout.add(txtDegrees);
      pnlLayout.add(new Label("Result"));
      final TextField txtResult = new TextField(30);
      pnlLayout.add(txtResult);
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
      pnlLayout.add(btnConvertToCelsius);
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
      pnlLayout.add(btnConvertToFahrenheit);
      add(pnlLayout);
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
                         new TempVerter();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
