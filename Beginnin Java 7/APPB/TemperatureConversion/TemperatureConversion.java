import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

class TemperatureConversion
{
   public static void main(String[] args) throws ScriptException
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("rhino");
      String script = "function c2f(degrees)"+
                      "{"+
                      "   return degrees*9.0/5.0+32;"+
                      "}"+
                      " "+
                      "function f2c(degrees)"+
                      "{"+
                      "   return (degrees-32)*5.0/9.0;"+
                      "}"+
                      " "+
                      "function convertTemperature(degrees, toCelsius)"+
                      "{"+
                      "   if (toCelsius)"+
                      "      return f2c(degrees);"+
                      "   else"+
                      "      return c2f(degrees);"+
                      "}";
      engine.eval(script);
      Invocable invocable = (Invocable) engine;
      TempConversion tc = invocable.getInterface(TempConversion.class);
      if (tc == null)
          System.err.println("Unable to obtain TempConversion interface");
      else
      {
          System.out.println("37 degrees Celsius = "+
                              tc.convertTemperature(37.0, false)+
                              " degrees Fahrenheit");
          System.out.println("212 degrees Fahrenheit = "+
                              tc.convertTemperature(212.0, true)+
                              " degrees Celsius");
      }
   }
}
interface TempConversion
{
   double convertTemperature(double degrees, boolean toCelsius);
}
