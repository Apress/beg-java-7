import java.util.Random;

class Die
{
   public static void main(String[] args)
   {
      Random r = new Random();
      int die = r.nextInt(6)+1;
      System.out.println(die);
   }
}
