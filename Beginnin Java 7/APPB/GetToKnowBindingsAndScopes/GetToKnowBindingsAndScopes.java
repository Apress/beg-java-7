import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GetToKnowBindingsAndScopes
{
   public static void main(String[] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      manager.put("global", "global bindings");
      System.out.println("INITIAL GLOBAL SCOPE BINDINGS");
      dumpBindings(manager.getBindings());
      ScriptEngine engine = manager.getEngineByExtension("js");
      engine.put("engine", "engine bindings");
      System.out.println("ENGINE'S GLOBAL SCOPE BINDINGS");
      dumpBindings(engine.getBindings(ScriptContext.GLOBAL_SCOPE));
      System.out.println("ENGINE'S ENGINE SCOPE BINDINGS");
      dumpBindings(engine.getBindings(ScriptContext.ENGINE_SCOPE));
      try
      {
         Bindings bindings = engine.createBindings();
         bindings.put("engine", "overridden engine bindings");
         bindings.put("app", new GetToKnowBindingsAndScopes());
         bindings.put("bindings", bindings);
         System.out.println("ENGINE'S OVERRIDDEN ENGINE SCOPE BINDINGS");
         engine.eval("app.dumpBindings(bindings);", bindings);
      }
      catch (ScriptException se)
      {
         System.err.println(se.getMessage());
      }
      ScriptEngine engine2 = manager.getEngineByExtension("js");
      engine2.put("engine2", "engine2 bindings");
      System.out.println("ENGINE2'S GLOBAL SCOPE BINDINGS");
      dumpBindings(engine2.getBindings(ScriptContext.GLOBAL_SCOPE));
      System.out.println("ENGINE2'S ENGINE SCOPE BINDINGS");
      dumpBindings(engine2.getBindings(ScriptContext.ENGINE_SCOPE));
      System.out.println("ENGINE'S ENGINE SCOPE BINDINGS");
      dumpBindings(engine.getBindings(ScriptContext.ENGINE_SCOPE));
   }
   public static void dumpBindings(Bindings bindings)
   {
      if (bindings == null)
         System.out.println("  No bindings");
      else
         for (String key: bindings.keySet())
            System.out.println("  "+key+": "+bindings.get(key));
      System.out.println();
   }
}
