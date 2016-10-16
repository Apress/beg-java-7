import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Employee
{
   private String name;
   Employee(String name) { this.name = name; }
   @Override
   public String toString() { return name; }
}
class SerEmployee implements Serializable
{
   private Employee emp;
   private String name;
   SerEmployee(String name)
   {
      this.name = name;
      emp = new Employee(name);
   }
   private void writeObject(ObjectOutputStream oos) throws IOException
   {
      oos.writeUTF(name);
   }
   private void readObject(ObjectInputStream ois)
      throws ClassNotFoundException, IOException
   {
      name = ois.readUTF();
      emp = new Employee(name);
   }
   @Override
   public String toString()
   {
      return name;
   }
}
class SerializationDemo
{
   public static void main(String[] args)
   {
      try (ObjectOutputStream oos =
              new ObjectOutputStream(new FileOutputStream("employee.dat")))
      {
         SerEmployee se = new SerEmployee("John Doe");
         System.out.println(se);
         oos.writeObject(se);
         System.out.println("se object written to file");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      try (ObjectInputStream ois =
              new ObjectInputStream(new FileInputStream("employee.dat")))
      {
         SerEmployee se = (SerEmployee) ois.readObject();
         System.out.println("se object read from file");
         System.out.println(se);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
