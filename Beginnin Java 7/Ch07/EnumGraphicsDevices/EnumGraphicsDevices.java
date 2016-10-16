import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

class EnumGraphicsDevices
{
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         enumerate();
                      }
                   };
      EventQueue.invokeLater(r);
   }
   static void enumerate()
   {
      GraphicsEnvironment ge;
      ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      for (GraphicsDevice gd: gds)
      {
         System.out.println(gd.getIDstring());
         switch (gd.getType())
         {
            case GraphicsDevice.TYPE_IMAGE_BUFFER:
               System.out.println("image buffer");
               break;
            case GraphicsDevice.TYPE_PRINTER:
               System.out.println("printer");
               break;
            case GraphicsDevice.TYPE_RASTER_SCREEN:
               System.out.println("raster screen");
         }
         System.out.println();
      }
   }
}
