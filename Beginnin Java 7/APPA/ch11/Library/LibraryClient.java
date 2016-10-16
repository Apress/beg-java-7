import java.net.URL;

import javax.xml.namespace.QName;

import javax.xml.ws.Service;

import ca.tutortutor.lib.Library;

class LibraryClient
{
   public static void main(String[] args) throws Exception
   {
      URL url = new URL("http://localhost:9903/Library?wsdl");
      QName qname = new QName("http://lib.tutortutor.ca/",
                              "LibraryImplService");
      Service service = Service.create(url, qname);
      qname = new QName("http://lib.tutortutor.ca/", "LibraryImplPort");
      Library lib = service.getPort(qname, Library.class);
//      Library lib = service.getPort(Library.class);
      lib.addBook("9781430234135", "Android Recipes");
      System.out.println(lib.getTitle("9781430234135"));
   }
}
