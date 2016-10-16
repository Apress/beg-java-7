import java.util.HashSet;
import java.util.Set;

class HashSetDemo
{
   public static void main(String[] args)
   {
      Set<String> ss = new HashSet<>();
      String[] fruits = {"apples", "pears", "grapes", "bananas", "kiwis",
                         "pears", null};
      for (String fruit: fruits)
         ss.add(fruit);
      dump("ss:", ss);
   }
   static void dump(String title, Set<String> ss)
   {
      System.out.print(title+" ");
      for (String s: ss)
         System.out.print(s+" ");
      System.out.println();
   }
}
