class Horse
{
   void describe()
   {
      // Code that outputs a description of a horse's appearance and behaviors.
   }
}
class Bird
{
   void describe()
   {
      // Code that outputs a description of a bird's appearance and behaviors.
   }
}
class FlyingHorse extends Horse, Bird
{
   public static void main(String[] args)
   {
      FlyingHorse pegasus = new FlyingHorse();
      pegasus.describe();
   }
}