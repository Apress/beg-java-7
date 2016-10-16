import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class FindAll
{
   final static String LINE_SEPARATOR = System.getProperty("line.separator");
   static JTextArea txtSrchResults;
   static JFrame f;
   static volatile String result;
   static JPanel createGUI()
   {
      JPanel pnl = new JPanel();
      pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
      JPanel pnlTemp = new JPanel();
      JLabel lblStartDir = new JLabel("Start directory");
      pnlTemp.add(lblStartDir);
      final JTextField txtStartDir = new JTextField(30);
      pnlTemp.add(txtStartDir);
      pnl.add(pnlTemp);
      pnlTemp = new JPanel();
      JLabel lblSrchText = new JLabel("Search text");
      pnlTemp.add(lblSrchText);
      lblSrchText.setPreferredSize(lblStartDir.getPreferredSize());
      final JTextField txtSrchText = new JTextField(30);
      pnlTemp.add(txtSrchText);
      pnl.add(pnlTemp);
      pnlTemp = new JPanel();
      JButton btnSearch = new JButton("Search");
      pnlTemp.add(btnSearch);
      pnl.add(pnlTemp);
      pnlTemp = new JPanel();
      txtSrchResults = new JTextArea(20, 30);
      pnlTemp.add(new JScrollPane(txtSrchResults));
      pnl.add(pnlTemp);
      ActionListener al;
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 final String startDir = txtStartDir.getText();
                 final String srchText = txtSrchText.getText();
                 txtSrchResults.setText("");
                 Runnable r;
                 r = new Runnable()
                     {
                        @Override
                        public void run()
                        {
                           if (!findAll(new File(startDir), srchText))
                           {
                              Runnable r;
                              r = new Runnable()
                                  {
                                     @Override
                                     public void run()
                                     {
                                        String msg = "not a directory";
                                        JOptionPane.showMessageDialog(f, msg);
                                     }
                                  };
                              EventQueue.invokeLater(r);
                           }
                        }
                     };
                 new Thread(r).start();
              }
           };
      btnSearch.addActionListener(al);
      return pnl;
   }
   static boolean findAll(File file, String srchText)
   {
      File[] files = file.listFiles();
      if (files == null)
         return false;
      for (int i = 0; i < files.length; i++)
         if (files[i].isDirectory())
            findAll(files[i], srchText);
         else
         if (find(files[i].getPath(), srchText))
         {
            result = files[i].getPath();
            Runnable r = new Runnable()
                         {
                            @Override
                            public void run()
                            {
                               txtSrchResults.append(result+LINE_SEPARATOR);
                            }
                         };
            EventQueue.invokeLater(r);
         }
      return true;
   }
   static boolean find(String filename, String srchText)
   {
      try (BufferedReader br = new BufferedReader(new FileReader(filename)))
      {
         int ch;
         outer_loop:
         do
         {
            if ((ch = br.read()) == -1)
               return false;
            if (ch == srchText.charAt(0))
            {
               for (int i = 1; i < srchText.length(); i++)
               {
                  if ((ch = br.read()) == -1)
                     return false;
                  if (ch != srchText.charAt(i))
                     continue outer_loop;
               }
               return true;
            }
         }
         while (true);
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
      return false;
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         f = new JFrame("FindAll");
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         f.setContentPane(createGUI());
                         f.pack();
                         f.setResizable(false);
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
