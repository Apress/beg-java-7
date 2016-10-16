import java.io.IOException;
import java.io.PrintStream;

import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import javax.xml.ws.handler.MessageContext;

import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext>
{
   private static PrintStream out = System.out;
   @Override
   public Set<QName> getHeaders()
   {
      return null;
   }
   @Override
   public void close(MessageContext messageContext)
   {
   }
   @Override
   public boolean handleFault(SOAPMessageContext soapmc)
   {
      log(soapmc); 
      return true;
   }
   @Override
   public boolean handleMessage(SOAPMessageContext soapmc)
   {
      log(soapmc);
      return true;
   }
   private void log(SOAPMessageContext soapmc)
   {
      Boolean outboundProperty = (Boolean)
         soapmc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
      if (outboundProperty.booleanValue())
         out.println("Outbound message:");
      else
         out.println("Inbound message:");
      SOAPMessage soapm = soapmc.getMessage();
      try
      {
         soapm.writeTo(out);
         out.println("\n");
      }
      catch (IOException|SOAPException e)
      {
         out.println("Handler exception: "+e);
      }
   }
}
