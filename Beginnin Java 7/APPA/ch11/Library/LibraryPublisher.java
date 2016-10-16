import javax.xml.ws.Endpoint;

import ca.tutortutor.lib.LibraryImpl;

class LibraryPublisher
{
   public static void main(String[] args)
   {
      Endpoint.publish("http://localhost:9903/Library",
                       new LibraryImpl());
   }
}
