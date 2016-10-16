import javax.xml.ws.Endpoint;

import ca.tutortutor.tv.TempVerterImpl;

class TempVerterPublisher
{
   public static void main(String[] args)
   {
      Endpoint.publish("http://localhost:9901/TempVerter",
                       new TempVerterImpl());
   }
}
