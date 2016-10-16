import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

class TestCompilationSpeed
{
   final static int MAX_ITERATIONS = 10000;
   public static void main(String[] args) throws Exception
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("JavaScript");
      String fact = "function fact(n)"+
                    "{"+
                    "   if (n == 0)"+
                    "      return 1;"+
                    "   else"+
                    "      return n*fact(n-1);"+
                    "};";
      long time = System.currentTimeMillis();
      for (int i = 0; i < MAX_ITERATIONS; i++)
         engine.eval(fact);
      System.out.println(System.currentTimeMillis()-time);
      Compilable compilable = null;
      if (engine instanceof Compilable)
      {
         compilable = (Compilable) engine;
         CompiledScript script = compilable.compile(fact);
         time = System.currentTimeMillis();
         for (int i = 0; i < MAX_ITERATIONS; i++)
            script.eval();
         System.out.println(System.currentTimeMillis()-time);
      }
   }
}
