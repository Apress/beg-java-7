abstract class Viewer
{
   enum ViewMode { NORMAL, INFO, HEX };
   abstract void view(byte[] content, ViewMode vm);
}
