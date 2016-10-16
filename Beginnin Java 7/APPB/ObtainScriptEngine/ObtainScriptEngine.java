import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

class ObtainScriptEngine
{
   public static void main(String[] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine1 = manager.getEngineByExtension("js");
      System.out.println(engine1);
      ScriptEngine engine2 =
         manager.getEngineByMimeType("application/javascript");
      System.out.println(engine2);
      ScriptEngine engine3 = manager.getEngineByName("rhino");
      System.out.println(engine3);
   }
}
