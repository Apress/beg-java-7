import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class MinimalHTTPServer
{
   public static void main(String[] args) throws IOException
   {
      HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
      server.createContext("/echo", new Handler());
      server.start();
   }
}
class Handler implements HttpHandler
{
   @Override
   public void handle(HttpExchange xchg) throws IOException
   {
      Headers headers = xchg.getRequestHeaders();
      Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
      StringBuffer response = new StringBuffer();
      for (Map.Entry<String, List<String>> entry: entries)
         response.append(entry.toString()+"\n");
      xchg.sendResponseHeaders(200, response.length());
      OutputStream os = xchg.getResponseBody();
      os.write(response.toString().getBytes());
      os.close();
   }
}
