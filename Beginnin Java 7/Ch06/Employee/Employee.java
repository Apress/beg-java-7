import java.util.Objects;

class Employee
{
   private String firstName, lastName;
   Employee(String firstName, String lastName)
   {
      try
      {
         firstName = Objects.requireNonNull(firstName);
         lastName = Objects.requireNonNull(lastName,
                                           "lastName shouldn't be null");
         lastName = Character.toUpperCase(lastName.charAt(0))+
                    lastName.substring(1);
         this.firstName = firstName;
         this.lastName = lastName;
      }
      catch (NullPointerException npe)
      {
         // In lieu of a more sophisticated logging mechanism, and also for
         // brevity, I output the exception's message to standard output.
         System.out.println(npe.getMessage());
      }
   }
   String getName()
   {
      return firstName+" "+lastName;
   }
   public static void main(String[] args)
   {
      Employee e1 = new Employee(null, "Doe");
      Employee e2 = new Employee("John", null);
      Employee e3 = new Employee("John", "doe");
      System.out.println(e3.getName());
   }
}
