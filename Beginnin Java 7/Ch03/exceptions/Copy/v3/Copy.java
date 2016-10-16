import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class Copy
{
   public static void main(String[] args)
   {
      if (args.length != 2)
      {
         System.err.println("usage: java Copy srcfile dstfile");
         return;
      }
      try
      {
         copy(args[0], args[1]);
      }
      catch (FileNotFoundException fnfe)
      {
         String msg = args[0]+" could not be found or might be a directory,"+
                      " or "+args[1]+" could not be created, "+
                      "possibly because "+args[1]+" is a directory";
         System.err.println(msg);
      }
      catch (IOException ioe)
      {
         String msg = args[0]+" could not be read, or "+args[1]+
                      " could not be written";
         System.err.println(msg);
      }
   }
   static void copy(String srcFile, String dstFile) throws IOException
   {
      try (FileInputStream fis = new FileInputStream(srcFile); 
           FileOutputStream fos = new FileOutputStream(dstFile))
      {
         int b;
         while ((b = fis.read()) != -1)
            fos.write(b);
      }
   }
}
