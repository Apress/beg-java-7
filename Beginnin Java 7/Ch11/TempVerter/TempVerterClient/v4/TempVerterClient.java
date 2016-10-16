import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.namespace.QName;

import javax.xml.ws.Service;

import ca.tutortutor.tv.TempVerter;

class TempVerterClient
{
   public static void main(String[] args) throws Exception
   {
      Authenticator auth;
      auth = new Authenticator()
      {
         @Override
         protected PasswordAuthentication getPasswordAuthentication()
         {
            return new PasswordAuthentication("x", new char[] { 'y' });
         }
      };
      Authenticator.setDefault(auth);
      URL url = new URL("http://localhost:9901/TempVerter?wsdl");
      QName qname = new QName("http://tv.tutortutor.ca/",
                              "TempVerterImplService");
      Service service = Service.create(url, qname);
      qname = new QName("http://tv.tutortutor.ca/", "TempVerterImplPort");
      TempVerter tv = service.getPort(qname, TempVerter.class);
//      TempVerter tv = service.getPort(TempVerter.class);
      System.out.println(tv.c2f(37.0));
      System.out.println(tv.f2c(212.0));
   }
}
