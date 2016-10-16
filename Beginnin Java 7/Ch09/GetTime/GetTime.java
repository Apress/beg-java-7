import java.io.InputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

class GetTime
{
   public static void main(String[] args)
   {
      if (args.length != 1)
      {
         System.err.println("usage  : java GetTime server");
         System.err.println("example: java GetTime time.nist.gov");
         return;
      }
      try (Socket socket = new Socket(args[0], 13))
      {
         InputStream is = socket.getInputStream();
         int ch;
         while ((ch = is.read()) != -1)
            System.out.print((char) ch);
      }
      catch (UnknownHostException uhe)
      {
         System.err.println("unknown host: "+uhe.getMessage());
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
   }
}
