public class Author implements java.io.Serializable
{
   private String name;
   public Author() {}
   Author(String name) { setName(name); }
   String getName() { return name; }
   void setName(String name) { this.name = name; }
}
