package ca.tutortutor.lib;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

@WebService(endpointInterface = "ca.tutortutor.lib.Library")
public class LibraryImpl implements Library
{
   private Map<String, String> books = new HashMap<>();
   public void addBook(String isbn, String title)
   {
      books.put(isbn, title);
      System.out.println("Number of books in library: "+books.size());
   }
   public String getTitle(String isbn)
   {
      return books.get(isbn);
   }
}
