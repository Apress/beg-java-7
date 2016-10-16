import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import javax.xml.namespace.QName;

class LibraryClientSAAJ
{
   final static String ENDPOINT = "http://localhost:9903/Library";
   public static void main(String[] args) throws Exception
   {
      SOAPConnectionFactory soapcf = SOAPConnectionFactory.newInstance();
      SOAPConnection soapc = soapcf.createConnection();
      MessageFactory mf = MessageFactory.newInstance();
      SOAPMessage soapm = mf.createMessage();
      SOAPPart soapp = soapm.getSOAPPart();
      SOAPEnvelope soape = soapp.getEnvelope();
      SOAPBody soapb = soape.getBody();
      soape.getHeader().detachNode();
      soape.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
      soape.addNamespaceDeclaration("xsi",
                                  "http://www.w3.org/2001/XMLSchema-instance");
      QName name = new QName("http://lib.tutortutor.ca/", "addBook", "tns");
      SOAPElement soapel = soapb.addBodyElement(name);
      soapel.addChildElement("arg0").addTextNode("9781430234135")
            .setAttribute("xsi:type", "xsd:string");
      soapel.addChildElement("arg1").addTextNode("Android Recipes")
            .setAttribute("xsi:type", "xsd:string");
      soapm.writeTo(System.out);
      System.out.println();
      System.out.println();
      SOAPMessage response = soapc.call(soapm, ENDPOINT);
      response.writeTo(System.out);
      System.out.println();
      System.out.println();
      soapm = mf.createMessage();
      soapm = mf.createMessage();
      soapp = soapm.getSOAPPart();
      soape = soapp.getEnvelope();
      soapb = soape.getBody();
      soape.getHeader().detachNode();
      soape.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
      soape.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-Instance");
      name = new QName("http://lib.tutortutor.ca/", "getTitle", "tns");
      soapel = soapb.addBodyElement(name);
      soapel.addChildElement("arg0").addTextNode("9781430234135")
            .setAttribute("xsi:type", "xsd:string");
      soapm.writeTo(System.out);
      System.out.println();
      System.out.println();
      response = soapc.call(soapm, ENDPOINT);
      response.writeTo(System.out);
   }
}
