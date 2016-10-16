class Image
{
   private int width, height;
   private byte[] image;

   Image()
   {
      this(null);
   }
   Image(String filename)
   {
      this(filename, null);
   }
   Image(String filename, String imageType)
   {
      if (filename != null)
      {
         System.out.println("reading "+filename);
         if (imageType != null)
            System.out.println("interpreting "+filename+" as storing a "+
                               imageType+" image");
         image = new byte[(int) (Math.random()*100000)];
         width = (int) (Math.random()*1024);
         height = (int) (Math.random()*768);
         return;
      }
      width = -1;
      height = -1;
   }
   int getWidth()
   {
      return width;
   }
   int getHeight()
   {
      return height;
   }
   byte[] getImage()
   {
      return image;
   }
   int getSize()
   {
      return image == null ? 0 : image.length;
   }
   public static void main(String[] args)
   {
      Image image = new Image();
      System.out.println("Image = "+image.getImage());
      System.out.println("Size = "+image.getSize());
      System.out.println("Width = "+image.getWidth());
      System.out.println("Height = "+image.getHeight());
      System.out.println();
      image = new Image("image.png");
      System.out.println("Image = "+image.getImage());
      System.out.println("Size = "+image.getSize());
      System.out.println("Width = "+image.getWidth());
      System.out.println("Height = "+image.getHeight());
      System.out.println();
      image = new Image("image.png", "PNG");
      System.out.println("Image = "+image.getImage());
      System.out.println("Size = "+image.getSize());
      System.out.println("Width = "+image.getWidth());
      System.out.println("Height = "+image.getHeight());
   }
}
