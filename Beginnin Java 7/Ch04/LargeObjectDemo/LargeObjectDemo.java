import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

class LargeObject
{
   private byte[] memory = new byte[1024*1024*50]; // 50 megabytes
}
class LargeObjectDemo
{
   public static void main(String[] args)
   {
      ReferenceQueue<LargeObject> rq;
      rq = new ReferenceQueue<LargeObject>();
      PhantomReference<LargeObject> pr;
      pr = new PhantomReference<LargeObject>(new LargeObject(), rq);
      byte[] b = new byte[1024];
      while (rq.poll() == null)
      {
         System.out.println("waiting for large object to be finalized");
         b = new byte[b.length*10];
      }
      System.out.println("large object finalized");
      System.out.println("pr.get() returns "+pr.get());
   }
}
