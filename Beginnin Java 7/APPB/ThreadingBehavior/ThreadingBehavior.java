import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

class ThreadingBehavior
{
   public static void main(String[] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      List<ScriptEngineFactory> factories = manager.getEngineFactories();
      for (ScriptEngineFactory factory: factories)
         System.out.println("Threading behavior: "+
                            factory.getParameter("THREADING"));
   }
}
