import java.lang.ref.SoftReference;

class Image
{
   private byte[] image;
   private Image(String name)
   {
      image = new byte[1024*1024*100];
   }
   static Image getImage(String name)
   {
      return new Image(name);
   }
}
class ImageCache
{
   public static void main(String[] args)
   {
      Image image = Image.getImage("large.png");
      System.out.println("caching image");
      SoftReference<Image> cache = new SoftReference<>(image);
      image = null;
      byte[] b = new byte[1024];
      while (cache.get() != null)
      {
         System.out.println("image is still cached");
         b = new byte[b.length*10];
      }
      System.out.println("image is no longer cached");
      b = null;
      System.out.println("reloading and recaching image");
      cache = new SoftReference<>(Image.getImage("large.png"));
      int counter = 0;
      while (cache.get() != null && ++counter != 7)
         System.out.println("image is still cached");
   }
}
