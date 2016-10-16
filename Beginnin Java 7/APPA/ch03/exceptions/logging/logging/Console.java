package logging;

class Console implements Logger
{
   private String dstName;
   Console(String dstName)
   {
      this.dstName = dstName;
   }
   @Override
   public void connect() throws CannotConnectException
   {
   }
   @Override
   public void disconnect() throws NotConnectedException
   {
   }
   @Override
   public void log(String msg) throws NotConnectedException
   {
      System.out.println(msg);
   }
}
