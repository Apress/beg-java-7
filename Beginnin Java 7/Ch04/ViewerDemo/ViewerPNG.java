class ViewerPNG extends Viewer
{
   @Override
   void view(byte[] content, ViewMode vm)
   {
      switch (vm)
      {
         case NORMAL:
            System.out.println("outputting PNG content normally");
            break;
         case INFO:
            System.out.println("outputting PNG content informationally");
            break;
         case HEX:
            System.out.println("outputting PNG content in hexadecimal");
      }
   }
}
