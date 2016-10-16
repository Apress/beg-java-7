class SuperException extends Exception
{
}
class SubException1 extends SuperException
{
}
class SubException2 extends SuperException
{
}
class BreakageDemo
{
   public static void main(String[] args) throws SuperException
   {
      try
      {
         throw new SubException1();
      }
      catch (SuperException se)
      {
         try
         {
            throw se;
         }
         catch (SubException2 se2)
         {
         }
      }
   }
}
