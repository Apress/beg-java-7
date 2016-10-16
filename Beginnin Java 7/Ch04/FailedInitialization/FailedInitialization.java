class FailedInitialization
{
   static
   {
      someMethod(null);
   }
   static void someMethod(String s)
   {
      int len = s.length(); // s contains null
      System.out.println(s+"'s length is "+len+" characters");
   }
   public static void main(String[] args)
   {
   }
}
