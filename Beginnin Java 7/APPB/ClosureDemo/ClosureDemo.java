import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ClosureDemo
{
   public static void each(Collection<?> c, MethodHandle mh) throws Throwable
   {
      for (Object element : c)
         mh.invoke(element);
   }
   public static void sayHello(Object message)
   {
      System.out.println("hello "+message);
   }
   public static void main(String[] args) throws Throwable
   {
      MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
      MethodType methodType = MethodType.methodType(void.class, Object.class);
      MethodHandle mh = LOOKUP.findStatic(ClosureDemo.class, "sayHello",
                                          methodType);
      List list = Arrays.asList("davinci", "closure");
      each(list, mh);
   }
}
