import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.io.IOException;

class CopyCutAndPaste extends Frame implements ActionListener
{
   TextArea ta;
   static Clipboard clipboard;
   CopyCutAndPaste()
   {
      super("Copy, Cut, and Paste");
      addWindowListener(new WindowAdapter()
                        {
                           @Override
                           public void windowClosing(WindowEvent we)
                           {
                              dispose();
                           }
                        });
      ta = new TextArea(10, 60);
      add(ta);
      MenuBar mb = new MenuBar();
      Menu mnuFile = new Menu("File");
      MenuItem miFile = new MenuItem("Exit");
      miFile.addActionListener(new ActionListener()
                               {
                                  public void actionPerformed(ActionEvent ae)
                                  {
                                     dispose();
                                  }
                               });
      mnuFile.add(miFile);
      mb.add(mnuFile);
      Menu mnuEdit = new Menu("Edit");
      MenuItem miCopy = new MenuItem("Copy");
      miCopy.setActionCommand("copy");
      miCopy.addActionListener(this);
      mnuEdit.add(miCopy);
      MenuItem miCut = new MenuItem("Cut");
      miCut.setActionCommand("cut");
      miCut.addActionListener(this);
      mnuEdit.add(miCut);
      MenuItem miPaste = new MenuItem("Paste");
      miPaste.setActionCommand("paste");
      miPaste.addActionListener(this);
      mnuEdit.add(miPaste);
      mb.add(mnuEdit);
      setMenuBar(mb);
      pack();
      setVisible(true);
   }
   @Override
   public void actionPerformed(ActionEvent ae)
   {
      switch (ae.getActionCommand())
      {
         case "copy" : copy(); break;
         case "cut"  : cut(); break;
         case "paste": paste();
      }
   }
   void copy()
   {
      StringSelection ss = new StringSelection(ta.getSelectedText());
      clipboard.setContents(ss, ss);
   }
   void cut()
   {
      copy();
      ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
   }
   void paste()
   {
      Transferable clipData = clipboard.getContents(this);
      if (clipData != null)
         try
         {
            if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor))
            {
               String text = (String)
                             clipData.getTransferData(DataFlavor.stringFlavor);
               ta.replaceRange(text, ta.getSelectionStart(),
                               ta.getSelectionEnd());
            }
         }
         catch (UnsupportedFlavorException ufe)
         {
            ta.setText("Flavor not supported");
         }
         catch (IOException ioe)
         {
            ta.setText("No data to paste");
         }
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         Toolkit toolkit = Toolkit.getDefaultToolkit();
                         clipboard = toolkit.getSystemClipboard();
                         new CopyCutAndPaste();
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
