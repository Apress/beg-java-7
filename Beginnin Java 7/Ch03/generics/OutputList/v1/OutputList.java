import java.util.ArrayList;
import java.util.List;

class OutputList
{
   public static void main(String[] args)
   {
      List<String> ls = new ArrayList<>();
      ls.add("first");
      ls.add("second");
      ls.add("third");
      outputList(ls);
   }
   static void outputList(List<Object> list)
   {
      for (int i = 0; i < list.size(); i++)
         System.out.println(list.get(i));
   }
}
