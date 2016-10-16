class Nodes
{
   public static void main(String[] args)
   {
      Node top = new Node();
      top.name = "node 1";
      top.next = new Node();
      top.next.name = "node 2";
      top.next.next = new Node();
      top.next.next.name = "node 3";
      top.next.next.next = null;
      Node temp = top;
      while (temp != null)
      {
         System.out.println(temp.name);
         temp = temp.next;
      }
   }
}
