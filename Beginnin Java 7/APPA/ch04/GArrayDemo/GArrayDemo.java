import ca.tutortutor.collections.GArray;

class GArrayDemo
{
   public static void main(String[] args)
   {
      GArray<String> ga = new GArray<>(10);
      System.out.println("Size = "+ga.size());
      ga.set(3, "ABC");
      System.out.println("Size = "+ga.size());
      ga.set(22, "XYZ");
      System.out.println("Size = "+ga.size());
      System.out.println(ga.get(3));
      System.out.println(ga.get(22));
      System.out.println(ga.get(20));
      ga.set(20, "PQR");
      System.out.println(ga.get(20));
      System.out.println("Size = "+ga.size());
   }
}
