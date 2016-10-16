package logging;

class NullDevice implements Logger
{
   private String dstName;
   NullDevice(String dstName)
   {
   }
   @Override
   public boolean connect()
   {
      return true;
   }
   @Override
   public boolean disconnect()
   {
      return true;
   }
   @Override
   public boolean log(String msg)
   {
      return true;
   }
}
