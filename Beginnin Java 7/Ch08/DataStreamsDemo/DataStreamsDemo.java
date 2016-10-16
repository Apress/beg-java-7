import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DataStreamsDemo
{
   final static String FILENAME = "values.dat";
   public static void main(String[] args)
   {
      try (DataOutputStream dos =
              new DataOutputStream(new FileOutputStream(FILENAME)))
      {
         dos.writeInt(1995);
         dos.writeUTF("Saving this String in modified UTF-8 format!");
         dos.writeFloat(1.0F);
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
      try (DataInputStream dis =
              new DataInputStream(new FileInputStream(FILENAME)))
      {
         System.out.println(dis.readInt());
         System.out.println(dis.readUTF());
         System.out.println(dis.readFloat());
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
      }
   }
}
