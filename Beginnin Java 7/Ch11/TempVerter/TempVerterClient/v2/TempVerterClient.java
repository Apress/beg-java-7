import client.TempVerter;
import client.TempVerterImplService;

class TempVerterClient
{
   public static void main(String[] args) throws Exception
   {
      TempVerterImplService tvis = new TempVerterImplService();
      TempVerter tv = tvis.getTempVerterImplPort();
      System.out.println(tv.c2F(37.0));
      System.out.println(tv.f2C(212.0));
   }
}
