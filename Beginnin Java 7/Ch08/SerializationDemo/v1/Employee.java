class Employee implements java.io.Serializable
{
   private String name;
   private int age;
   Employee(String name, int age)
   {
      this.name = name;
      this.age = age;
   }
   String getName() { return name; }
   int getAge() { return age; }
}
