import java.util.concurrent.Semaphore;

final class Pool
{
   private static final int MAX_AVAILABLE = 1;
   private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
   Object getItem() throws InterruptedException
   {
      available.acquire();
      return getNextAvailableItem();
   }
   void putItem(Object x)
   {
      if (markAsUnused(x))
         available.release();
   }
   // Not a particularly efficient data structure; just for demo
   Object[] items = new String[] { "single pooled resource" };
   boolean[] used = new boolean[MAX_AVAILABLE];
   synchronized Object getNextAvailableItem()
   {
      for (int i = 0; i < MAX_AVAILABLE; ++i)
         if (!used[i])
         {
            used[i] = true;
            return items[i];
         }
      return null; // not reached
   }
   synchronized boolean markAsUnused(Object item)
   {
      for (int i = 0; i < MAX_AVAILABLE; ++i)
      {
         if (item == items[i])
         {
            if (used[i])
            {
               used[i] = false;
               return true;
            }
            else
               return false;
         }
      }
      return false;
   }
}
class SemaphoreDemo
{
   static volatile Pool pool = new Pool();
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      public void run()
                      {
                         try
                         {
                            String name = Thread.currentThread().getName();
                            while (true)
                            {
                               System.out.println(name+" wants resource");
                               Object o = pool.getItem();
                               System.out.println(name+" gets "+o);
                               System.out.println(name+" using resource");
                               Thread.sleep(3000);
                               System.out.println(name+" returns resource");
                               pool.putItem(o);
                            }
                         }
                         catch (InterruptedException ie)
                         {
                         }
                      }
                   };
      Thread dick = new Thread(r, "Dick");
      Thread jane = new Thread(r, "Jane");
      dick.start();
      jane.start();
   }
}
