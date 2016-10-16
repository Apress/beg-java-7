class OutputReversedInt
{
   public static void main(String[] args)
   {
      int x = 876432094;
      int i = 0;
      while (x != 0)
      {
         System.out.print(x%10);
         x /= 10;
      }
      System.out.print('\n');
   }
}
