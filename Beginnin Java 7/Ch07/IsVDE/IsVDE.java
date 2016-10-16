import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

class IsVDE
{
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      public void run()
                      {
                         test();
                      }
                   };
      EventQueue.invokeLater(r);
   }
   static void test()
   {
      GraphicsEnvironment ge;
      ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      for (GraphicsDevice gd: gds)
      {
         GraphicsConfiguration[] gcs = gd.getConfigurations();
         for (GraphicsConfiguration gc: gcs)
         {
            Rectangle rect = gc.getBounds();
            if (rect.x != 0 || rect.y != 0)
            {
               System.out.println("virtual device environment detected");
               return;
            }
         }
         System.out.println("no virtual device environment detected");
      }
   }
}
