import java.util.ArrayList;
import java.util.List;

class SafeVarargsDemo
{
   public static void main(String[] args)
   {
      List<String> list1 = new ArrayList<>();
      list1.add("A");
      list1.add("B");
      List<String> list2 = new ArrayList<>();
      list2.add("C");
      list2.add("D");
      list2.add("E");
      System.out.println(merge(list1, list2)); // Output: [A, B, C, D, E]
   }
   //@SafeVarargs
   static List<String> merge(List<String>... lists)
   {
      List<String> mergedLists = new ArrayList<>();
      for (int i = 0; i < lists.length; i++)
         mergedLists.addAll(lists[i]);
      return mergedLists;
   }
}
