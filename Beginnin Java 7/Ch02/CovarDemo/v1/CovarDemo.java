class SuperReturnType
{
   @Override
   public String toString()
   {
      return "superclass return type";
   }
}
class SubReturnType extends SuperReturnType
{
   @Override
   public String toString()
   {
      return "subclass return type";
   }
}
class Superclass
{
   SuperReturnType createReturnType()
   {
      return new SuperReturnType();
   }
}
class Subclass extends Superclass
{
   @Override
   SubReturnType createReturnType()
   {
      return new SubReturnType();
   }
}
class CovarDemo
{
   public static void main(String[] args)
   {
      SuperReturnType suprt = new Superclass().createReturnType();
      System.out.println(suprt); // Output: superclass return type
      SubReturnType subrt = new Subclass().createReturnType();
      System.out.println(subrt); // Output: subclass return type
   }
}
