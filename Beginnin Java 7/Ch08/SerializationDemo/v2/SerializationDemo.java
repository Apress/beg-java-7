import java.io.FileInputStream;
import java.io.FileOutputStream;
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
class SerEmployee extends Employee implements Serializable
{
   SerEmployee(String name) { super(name); }
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
         Object o = ois.readObject();
         System.out.println("se object read from byte array");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
