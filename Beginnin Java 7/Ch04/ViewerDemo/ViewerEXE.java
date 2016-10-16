class ViewerEXE extends Viewer
{
   @Override
   void view(byte[] content, ViewMode vm)
   {
      switch (vm)
      {
         case NORMAL:
            System.out.println("outputting EXE content normally");
            break;
         case INFO:
            System.out.println("outputting EXE content informationally");
            break;
         case HEX:
            System.out.println("outputting EXE content in hexadecimal");
      }
   }
}
