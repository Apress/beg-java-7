package ca.tutortutor.lib;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Library
{
   @WebMethod void addBook(String isbn, String title);
   @WebMethod String getTitle(String isbn);
}
