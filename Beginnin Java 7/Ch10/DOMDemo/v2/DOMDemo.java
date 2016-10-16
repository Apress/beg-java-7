import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

class DOMDemo
{
   public static void main(String[] args)
   {
      try
      {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.newDocument();
         // Create the root element.
         Element root = doc.createElement("movie");
         doc.appendChild(root);
         // Create name child element and add it to the root.
         Element name = doc.createElement("name");
         root.appendChild(name);
         // Add a text element to the name element.
         Text text = doc.createTextNode("Le Fabuleux Destin d'Amélie Poulain");
         name.appendChild(text);
         // Create language child element and add it to the root.
         Element language = doc.createElement("language");
         root.appendChild(language);
         // Add a text element to the language element.
         text = doc.createTextNode("français");
         language.appendChild(text);
         System.out.println("Version = "+doc.getXmlVersion());
         System.out.println("Encoding = "+doc.getXmlEncoding());
         System.out.println("Standalone = "+doc.getXmlStandalone());
         System.out.println();
         NodeList nl = doc.getChildNodes();
         for (int i = 0; i < nl.getLength(); i++)
         {
            Node node = nl.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
               dump((Element) node);
         }
      }
      catch (FactoryConfigurationError fce)
      {
         System.err.println("FCE: "+fce);
      }
      catch (ParserConfigurationException pce)
      {
         System.err.println("PCE: "+pce);
      }
   }
   static void dump(Element e)
   {
      System.out.println("Element: "+e.getNodeName()+", "+e.getLocalName()+
                         ", "+e.getPrefix()+", "+e.getNamespaceURI());
      NodeList nl = e.getChildNodes();
      for (int i = 0; i < nl.getLength(); i++)
      {
         Node node = nl.item(i);
         if (node instanceof Element)
            dump((Element) node);
         else
         if (node instanceof Text)
            System.out.println("Text: "+((Text) node).getWholeText());
      }
   }
}
