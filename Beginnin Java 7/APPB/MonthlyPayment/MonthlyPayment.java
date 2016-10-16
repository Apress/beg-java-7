import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

class MonthlyPayment
{
   public static void main(String[] args)
   {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByExtension("js");
      // Script variables intrate, principal, and months must be defined (via
      // the put() method) prior to evaluating this script.
      String calcMonthlyPaymentScript = 
         "intrate = intrate/1200.0;"+
         "payment = principal*intrate*(Math.pow(1+intrate, months)/"+
         "                            (Math.pow(1+intrate,months)-1));";
      try
      {
         engine.put("principal", 20000.0);
         System.out.println("Principal = "+engine.get("principal"));
         engine.put("intrate", 6.0);
         System.out.println("Interest Rate = "+engine.get("intrate")+"%");
         engine.put("months", 360);
         System.out.println("Months = "+engine.get("months"));
         engine.eval(calcMonthlyPaymentScript);
         System.out.printf("Monthly Payment = %.2f\n",
                             engine.get("payment"));
      }
      catch (ScriptException se)
      {
         System.err.println(se.getMessage());
      }
   }
}
