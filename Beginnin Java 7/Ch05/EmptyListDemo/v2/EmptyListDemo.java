import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Birds
{
   private List<String> birds;
   Birds()
   {
      birds = Collections.emptyList();
   }
   Birds(String... birdNames)
   {
      birds = new ArrayList<String>();
      for (String birdName: birdNames)
         birds.add(birdName);
   }
   @Override
   public String toString()
   {
      return birds.toString();
   }
   public void setBirds(List<String> birds)
   {
      this.birds = birds;
   }
}

class EmptyListDemo
{
   public static void main(String[] args)
   {
      Birds birds = new Birds("Swallow", "Robin", "Bluejay", "Oriole");
      System.out.println(birds);
//      birds.setBirds(Collections.emptyList());
      birds.setBirds(Collections.<String>emptyList());
      System.out.println(birds);
   }
}
