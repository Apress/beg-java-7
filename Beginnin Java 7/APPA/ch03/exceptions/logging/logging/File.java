package logging;

class File implements Logger
{
   private String dstName;
   File(String dstName)
   {
      this.dstName = dstName;
   }
   @Override
   public void connect() throws CannotConnectException
   {
      if (dstName == null)
          throw new CannotConnectException();
   }
   @Override
   public void disconnect() throws NotConnectedException
   {
      if (dstName == null)
          throw new NotConnectedException();
   }
   @Override
   public void log(String msg) throws NotConnectedException
   {
      if (dstName == null)
          throw new NotConnectedException();
      System.out.println("writing "+msg+" to file "+dstName);
   }
}
