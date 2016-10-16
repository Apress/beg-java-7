class ViewerDemo
{
   public static void main(String[] args)
   {
      if (args.length != 1)
      {
         System.err.println("usage  : java ViewerDemo filetype");
         System.err.println("example: java ViewerDemo EXE");
         return;
      }
      try
      {
         Class<?> clazz = Class.forName("Viewer"+args[0]);
         Viewer viewer = (Viewer) clazz.newInstance();
         viewer.view(null, Viewer.ViewMode.HEX);
      }
      catch (ClassNotFoundException cnfe)
      {
         System.err.println("Class not found: "+cnfe.getMessage());
      }
      catch (IllegalAccessException iae)
      {
         System.err.println("Illegal access: "+iae.getMessage());
      }
      catch (InstantiationException ie)
      {
         System.err.println("Unable to instantiate loaded class");
      }
   }
}
