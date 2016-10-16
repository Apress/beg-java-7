import java.util.List;

public class Book implements java.io.Serializable
{
   private String isbn;
   private String title;
   private String publisher;
   private String pubYear;
   private List<Author> authors;
   public Book() {} // Constructor and class must be public for instances to
                    // be treated as beans.
   Book(String isbn, String title, String publisher, String pubYear,
        List<Author> authors)
   {
      setISBN(isbn);
      setTitle(title);
      setPublisher(publisher);
      setPubYear(pubYear);
      setAuthors(authors);
   }
   List<Author> getAuthors() { return authors; }
   String getISBN() { return isbn; }
   String getPublisher() { return publisher; }
   String getPubYear() { return pubYear; }
   String getTitle() { return title; }
   void setAuthors(List<Author> authors) { this.authors = authors; }
   void setISBN(String isbn) { this.isbn = isbn; }
   void setPublisher(String publisher) { this.publisher = publisher; }
   void setPubYear(String pubYear) { this.pubYear = pubYear; }
   void setTitle(String title) { this.title = title; }
}
