import java.io.IOException;

import java.net.InetSocketAddress;

import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import ca.tutortutor.tv.TempVerterImpl;

class TempVerterPublisher
{
   public static void main(String[] args) throws IOException
   {
      HttpServer server = HttpServer.create(new InetSocketAddress(9901), 0);
      HttpContext context = server.createContext("/TempVerter");
      BasicAuthenticator auth;
      auth = new BasicAuthenticator("myAuth")
      {
         @Override
         public boolean checkCredentials(String username, String password)
         {
            return username.equals("x") && password.equals("y");
         }
      };
      context.setAuthenticator(auth);
      Endpoint endpoint = Endpoint.create(new TempVerterImpl());
      endpoint.publish(context);
      server.start();
   }
}
