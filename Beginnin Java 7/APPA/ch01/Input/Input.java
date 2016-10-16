class Input
{
   public static void main(String[] args) throws java.io.IOException
   {
      int ch;
      while (true)
      {
         System.out.println("Press C or c to continue.");
         ch = System.in.read();
         if (ch == 'C' || ch == 'c')
            break;
      }
   }
}
