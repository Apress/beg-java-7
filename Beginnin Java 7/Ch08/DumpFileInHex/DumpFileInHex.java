import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DumpFileInHex
{
   final static String LINE_SEPARATOR = System.getProperty("line.separator");
   public static void main(String[] args)
   {
      if (args.length != 1)
      {
         System.err.println("usage: java DumpFileInHex pathname");
         return;
      }
      String dest = args[0]+".hex";
      try (FileInputStream fis = new FileInputStream(args[0]);
           FileOutputStream fos = new FileOutputStream(dest))
      {
         StringBuffer sb = new StringBuffer();
         int offset = 0;
         int ch;
         while ((ch = fis.read()) != -1)
         {
            if ((offset%16) == 0)
            {
               writeStr(fos, toHexStr(offset, 8));
               fos.write(' ');
            }
            writeStr(fos, toHexStr(ch, 2));
            fos.write(' ');
            if (ch < 32 || ch > 127)
               sb.append('.');
            else
               sb.append((char) ch);
            if ((++offset%16) == 0)
            {
               writeStr(fos, sb.toString()+LINE_SEPARATOR);
               sb.setLength(0);
            }
         }
         if (sb.length() != 0)
         {
            for (int i = 0; i < 16-sb.length(); i++)
               writeStr(fos, "   ");
            writeStr(fos, sb.toString()+LINE_SEPARATOR);
         }
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
   }
   static String toHexStr(int value, int fieldWidth)
   {
      StringBuffer sb = new StringBuffer(Integer.toHexString(value));
      sb.reverse();
      int len = sb.length();
      for (int i = 0; i < fieldWidth-len; i++)
         sb.append('0');
      sb.reverse();
      return sb.toString();
   }
   static void writeStr(FileOutputStream fos, String s) throws IOException
   {
      for (int i = 0; i < s.length(); i++)
         fos.write(s.charAt(i));
   }
}
