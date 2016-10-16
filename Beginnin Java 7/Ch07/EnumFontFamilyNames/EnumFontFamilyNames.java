import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

class EnumFontFamilyNames
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
      String[] ffns = ge.getAvailableFontFamilyNames();
      for (String ffn: ffns)
         System.out.println(ffn);
   }
}
