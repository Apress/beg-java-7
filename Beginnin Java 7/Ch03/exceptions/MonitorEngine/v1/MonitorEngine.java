class PressureException extends Exception
{
   PressureException(String msg)
   {
      super(msg);
   }
}
class TemperatureException extends Exception
{
   TemperatureException(String msg)
   {
      super(msg);
   }
}
class MonitorEngine
{
   public static void main(String[] args)
   {
      try
      {
         monitor();
      }
      catch (Exception e)
      {
         if (e instanceof PressureException)
            System.out.println("correcting pressure problem");
         else
            System.out.println("correcting temperature problem");
      }
   }
   static void monitor() throws Exception
   {
      try
      {
         if (Math.random() < 0.1)
            throw new PressureException("pressure too high");
         else
         if (Math.random() > 0.9)
            throw new TemperatureException("temperature too high");
         else
            System.out.println("all is well");
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
         throw e;
      }
   }
}
