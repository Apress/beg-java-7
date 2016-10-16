import java.io.InputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

class GetResource
{
   public static void main(String[] args)
   {
      if (args.length != 1)
      {
         System.err.println("usage: java GetResource url");
         return;
      }
      try
      {
         URL url = new URL(args[0]);
         try (InputStream is = url.openStream())
         {
            int ch;
            while ((ch = is.read()) != -1)
               System.out.print((char) ch);
         }
      }
      catch (MalformedURLException murle)
      {
         System.err.println("invalid URL");
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
   }
}
