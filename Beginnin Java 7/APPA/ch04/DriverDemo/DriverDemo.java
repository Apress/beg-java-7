import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DriverDemo
{
   public static void main(String[] args)
   {
      if (args.length != 0)
      {
         System.err.println("usage: java DriverDemo");
         return;
      }
      try
      {
         Class<?> clazz = Class.forName("Driver");
         Driver driver = (Driver) clazz.newInstance();
         try
         {
            Method methodExt = clazz.getMethod("getCapabilitiesEx",
                                               (Class<?>[]) null);
            System.out.println(methodExt.invoke(driver, (Object[]) null));
         }
         catch (NoSuchMethodException nsme)
         {
            System.err.println("No getCapabilitiesEx() method");
            try
            {
               Method methodDef = clazz.getMethod("getCapabilities",
                                                  (Class<?>[])  null);
               System.out.println(methodDef.invoke(driver, (Object[]) null));
            }
            catch (NoSuchMethodException nsme2)
            {
               System.out.println("No getCapabilities() method");
            }
            catch (InvocationTargetException ite)
            {
               System.err.println("exception thrown from getCapabilities()");
            }
         }
         catch (InvocationTargetException ite)
         {
            System.err.println("exception thrown from getCapabilitiesEx()");
         }
      }
      catch (ClassNotFoundException cnfe)
      {
         System.err.println("Class not found: "+cnfe.getMessage());
      }
      catch (IllegalAccessException iae)
      {
         System.err.println("Illegal access: "+iae.getMessage());
      }
      catch (InstantiationException ie)
      {
         System.err.println("Unable to instantiate loaded class");
      }
   }
}
