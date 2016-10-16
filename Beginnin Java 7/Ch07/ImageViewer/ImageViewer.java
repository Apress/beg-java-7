import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ImageViewer
{
   static ImageCanvas ic;
   static ScrollPane sp;
   static Toolkit tk = Toolkit.getDefaultToolkit();
   static ImageCanvas createGUI(final Frame f)
   {
      MenuBar mb = new MenuBar();
      Menu mFile = new Menu("File");
      MenuItem miOpen = new MenuItem("Open...");
      ActionListener al;
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 FileDialog fd = new FileDialog(f, "Open file");
                 fd.setVisible(true);
                 String curFile = fd.getFile();
                 if (curFile != null)
                 {
                    ic.setImage(tk.getImage(fd.getDirectory()+curFile));
                    sp.doLayout();
                 }
              }
           };
      miOpen.addActionListener(al);
      mFile.add(miOpen);
      MenuItem miExit = new MenuItem("Exit");
      miExit.addActionListener(new ActionListener()
                               {
                                  @Override
                                  public void actionPerformed(ActionEvent ae)
                                  {
                                     f.dispose();
                                  }
                               });
      mFile.add(miExit);
      mb.add(mFile);
      f.setMenuBar(mb);
      return new ImageCanvas();
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         final Frame f = new Frame("ImageViewer");
                         WindowAdapter wa;
                         wa = new WindowAdapter()
                              {
                                 @Override
                                 public void windowClosing(WindowEvent we)
                                 {
                                    f.dispose();
                                 }
                              };
                         f.addWindowListener(wa);
                         sp = new ScrollPane();
                         sp.setPreferredSize(new Dimension(300, 300));
                         sp.add(ic = createGUI(f));
                         f.add(sp);
                         f.pack();
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
