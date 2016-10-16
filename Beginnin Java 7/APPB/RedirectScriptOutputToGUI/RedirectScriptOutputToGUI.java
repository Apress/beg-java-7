import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.PrintWriter;
import java.io.Writer;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class RedirectScriptOutputToGUI extends JFrame
{
   static ScriptEngine engine;
   public RedirectScriptOutputToGUI()
   {
      super("Redirect Script Output to GUI");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      getContentPane().add(createGUI());
      pack();
      setVisible(true);
   }
   JPanel createGUI()
   {
      JPanel pnlGUI = new JPanel();
      pnlGUI.setLayout(new BorderLayout());
      JPanel pnl = new JPanel();
      pnl.setLayout(new GridLayout(2, 1));
      final JTextArea txtScriptInput = new JTextArea(10, 60);
      pnl.add(new JScrollPane(txtScriptInput));
      final JTextArea txtScriptOutput = new JTextArea(10, 60);
      pnl.add(new JScrollPane(txtScriptOutput));
      pnlGUI.add(pnl, BorderLayout.NORTH);
      GUIWriter writer = new GUIWriter(txtScriptOutput);
      PrintWriter pw = new PrintWriter(writer, true);
      engine.getContext().setWriter(pw);
      engine.getContext().setErrorWriter(pw);
      pnl = new JPanel();
      JButton btnEvaluate = new JButton("Evaluate");
      ActionListener actionEvaluate;
      actionEvaluate = new ActionListener()
                       {
                          public void actionPerformed(ActionEvent ae)
                          {
                             try
                             {
                                engine.eval(txtScriptInput.getText());
                                dumpBindings();
                             }
                             catch (ScriptException se)
                             {
                                JFrame parent;
                                parent = RedirectScriptOutputToGUI.this;
                                JOptionPane.
                                   showMessageDialog(parent,
                                                     se.getMessage());
                             }
                          }
                       };
      btnEvaluate.addActionListener(actionEvaluate);
      pnl.add(btnEvaluate);
      JButton btnClear = new JButton("Clear");
      ActionListener actionClear;
      actionClear = new ActionListener()
                    {
                       public void actionPerformed(ActionEvent ae)
                       {
                          txtScriptInput.setText("");
                          txtScriptOutput.setText("");
                       }
                    };
      btnClear.addActionListener(actionClear);
      pnl.add(btnClear);
      pnlGUI.add(pnl, BorderLayout.SOUTH);
      return pnlGUI;
   }
   static void dumpBindings()
   {
      System.out.println("ENGINE BINDINGS");
      Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
      if (bindings == null)
         System.out.println("  No bindings");
      else
         for (String key: bindings.keySet())
            System.out.println("  "+key+": "+bindings.get(key));
      System.out.println();
   }
   public static void main(String[] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      engine = manager.getEngineByName("rhino");
      dumpBindings();
      Runnable r = new Runnable()
                   {
                      public void run()
                      {
                         new RedirectScriptOutputToGUI();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
class GUIWriter extends Writer
{
   private JTextArea txtOutput;
   GUIWriter(JTextArea txtOutput)
   {
      this.txtOutput = txtOutput;
   }
   public void close()
   {
      System.out.println("close");
   }
   public void flush()
   {
      System.out.println("flush");
   }
   public void write(char[] cbuf, int off, int len)
   {
      txtOutput.setText(txtOutput.getText()+new String(cbuf, off, len));
   }
}
