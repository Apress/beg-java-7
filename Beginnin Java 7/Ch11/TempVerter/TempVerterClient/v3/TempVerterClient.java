import java.net.URL;

import java.util.List;

import javax.xml.namespace.QName;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import javax.xml.ws.handler.Handler;

import ca.tutortutor.tv.TempVerter;

class TempVerterClient
{
   public static void main(String[] args) throws Exception
   {
      URL url = new URL("http://localhost:9901/TempVerter?wsdl");
      QName qname = new QName("http://tv.tutortutor.ca/",
                              "TempVerterImplService");
      Service service = Service.create(url, qname);
      qname = new QName("http://tv.tutortutor.ca/", "TempVerterImplPort");
      TempVerter tv = service.getPort(qname, TempVerter.class);
//      TempVerter tv = service.getPort(TempVerter.class);
      BindingProvider bp = (BindingProvider) tv;
      Binding binding = bp.getBinding();
      List<Handler> hc = binding.getHandlerChain();
      hc.add(new SOAPLoggingHandler());
      binding.setHandlerChain(hc);
      System.out.println(tv.c2f(37.0)+"\n");
      System.out.println(tv.f2c(212.0)+"\n");
   }
}
