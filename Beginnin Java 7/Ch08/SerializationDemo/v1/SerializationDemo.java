import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SerializationDemo
{
   final static String FILENAME = "employee.dat";
   public static void main(String[] args)
   {
      try (ObjectOutputStream oos =
              new ObjectOutputStream(new FileOutputStream(FILENAME)))
      {
         Employee emp = new Employee("John Doe", 36);
         oos.writeObject(emp);
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
         return;
      }
      try (ObjectInputStream ois =
              new ObjectInputStream(new FileInputStream(FILENAME)))
      {
         Employee emp = (Employee) ois.readObject();
         System.out.println(emp.getName());
         System.out.println(emp.getAge());
      }
      catch (ClassNotFoundException cnfe)
      {
         System.err.println(cnfe.getMessage());
      }
      catch (IOException ioe)
      {
         System.err.println(ioe.getMessage());
      }
   }
}
