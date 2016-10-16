import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMResult;

import javax.xml.transform.stream.StreamSource;

import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;

import javax.xml.ws.handler.MessageContext;

import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
class Library implements Provider<Source>
{
   private final static String LIBFILE = "library.ser";
   @Resource
   private WebServiceContext wsContext;
   private Map<String, Book> library;
   Library()
   {
      try
      {
         library = deserialize();
      }
      catch (IOException ioe)
      {
         library = new HashMap<>();
      }
   }
   @Override
   public Source invoke(Source request)
   {
      if (wsContext == null)
         throw new RuntimeException("dependency injection failed on wsContext");
      MessageContext msgContext = wsContext.getMessageContext();
      switch ((String) msgContext.get(MessageContext.HTTP_REQUEST_METHOD))
      {
         case "DELETE": return doDelete(msgContext);
         case "GET"   : return doGet(msgContext);
         case "POST"  : return doPost(msgContext, request);
         case "PUT"   : return doPut(msgContext, request);
         default      : throw new HTTPException(405);
      }
   }
   private Source doDelete(MessageContext msgContext)
   {
      try
      {
         String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
         if (qs == null)
         {
            library.clear();
            serialize();
            StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
            xml.append("<response>all books deleted</response>");
            return new StreamSource(new StringReader(xml.toString()));
         }
         else
         {
            String[] pair = qs.split("=");
            if (!pair[0].equalsIgnoreCase("isbn"))
               throw new HTTPException(400);
            String isbn = pair[1].trim();
            library.remove(isbn);
            serialize();
            StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
            xml.append("<response>book deleted</response>");
            return new StreamSource(new StringReader(xml.toString()));
         }
      }
      catch (IOException ioe)
      {
         throw new HTTPException(500);
      }
   }
   private Source doGet(MessageContext msgContext)
   {
      String qs = (String) msgContext.get(MessageContext.QUERY_STRING);
      if (qs == null)
      {
         Set<String> keys = library.keySet();
         Iterator<String> iter = keys.iterator();
         StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
         xml.append("<isbns>");
         while (iter.hasNext())
            xml.append("<isbn>"+iter.next()+"</isbn>");
         xml.append("</isbns>");
         return new StreamSource(new StringReader(xml.toString()));
      }
      else
      {
         String[] pair = qs.split("=");
         if (!pair[0].equalsIgnoreCase("isbn"))
            throw new HTTPException(400);
         String isbn = pair[1].trim();
         Book book = library.get(isbn);
         if (book == null)
            throw new HTTPException(404);
         StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
         xml.append("<book isbn=\""+book.getISBN()+"\" "+
                    "pubyear=\""+book.getPubYear()+"\">");
         xml.append("<title>"+book.getTitle()+"</title>");
         for (Author author: book.getAuthors())
            xml.append("<author>"+author.getName()+"</author>");
         xml.append("<publisher>"+book.getPublisher()+"</publisher>");
         xml.append("</book>");
         return new StreamSource(new StringReader(xml.toString()));
      }
   }
   private Source doPost(MessageContext msgContext, Source source)
   {
      try
      {
         DOMResult dom = new DOMResult();
         Transformer t = TransformerFactory.newInstance().newTransformer();
         t.transform(source, dom);
         XPathFactory xpf = XPathFactory.newInstance();
         XPath xp = xpf.newXPath();
         NodeList books = (NodeList) xp.evaluate("/book", dom.getNode(),
                                                 XPathConstants.NODESET);
         String isbn = xp.evaluate("@isbn", books.item(0));
         if (library.containsKey(isbn))
            throw new HTTPException(400);
         String pubYear = xp.evaluate("@pubyear", books.item(0));
         String title = xp.evaluate("title", books.item(0)).trim();
         String publisher = xp.evaluate("publisher", books.item(0)).trim();
         NodeList authors = (NodeList) xp.evaluate("author", books.item(0),
                                                   XPathConstants.NODESET);
         List<Author> auths = new ArrayList<>();
         for (int i = 0; i < authors.getLength(); i++)
            auths.add(new Author(authors.item(i).getFirstChild()
                                        .getNodeValue().trim()));
         Book book = new Book(isbn, title, publisher, pubYear, auths);
         library.put(isbn, book);
         serialize();
      }
      catch (IOException | TransformerException e)
      {
         throw new HTTPException(500);
      }
      catch (XPathExpressionException xpee)
      {
         throw new HTTPException(400);
      }
      StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
      xml.append("<response>book inserted</response>");
      return new StreamSource(new StringReader(xml.toString()));
   }
   private Source doPut(MessageContext msgContext, Source source)
   {
      try
      {
         DOMResult dom = new DOMResult();
         Transformer t = TransformerFactory.newInstance().newTransformer();
         t.transform(source, dom);
         XPathFactory xpf = XPathFactory.newInstance();
         XPath xp = xpf.newXPath();
         NodeList books = (NodeList) xp.evaluate("/book", dom.getNode(),
                                                 XPathConstants.NODESET);
         String isbn = xp.evaluate("@isbn", books.item(0));
         if (!library.containsKey(isbn))
            throw new HTTPException(400);
         String pubYear = xp.evaluate("@pubyear", books.item(0));
         String title = xp.evaluate("title", books.item(0)).trim();
         String publisher = xp.evaluate("publisher", books.item(0)).trim();
         NodeList authors = (NodeList) xp.evaluate("author", books.item(0),
                                                   XPathConstants.NODESET);
         List<Author> auths = new ArrayList<>();
         for (int i = 0; i < authors.getLength(); i++)
            auths.add(new Author(authors.item(i).getFirstChild()
                                        .getNodeValue().trim()));
         Book book = new Book(isbn, title, publisher, pubYear, auths);
         library.put(isbn, book);
         serialize();
      }
      catch (IOException | TransformerException e)
      {
         throw new HTTPException(500);
      }
      catch (XPathExpressionException xpee)
      {
         throw new HTTPException(400);
      }
      StringBuilder xml = new StringBuilder("<?xml version=\"1.0\"?>");
      xml.append("<response>book updated</response>");
      return new StreamSource(new StringReader(xml.toString()));
   }
   private Map<String, Book> deserialize() throws IOException
   {
      try (BufferedInputStream bis
             = new BufferedInputStream(new FileInputStream(LIBFILE));
           XMLDecoder xmld = new XMLDecoder(bis))
      {
         @SuppressWarnings("unchecked")
         Map<String, Book> result = (Map<String, Book>) xmld.readObject();
         return result;
      }
   }
   private void serialize() throws IOException
   {
      try (BufferedOutputStream bos
             = new BufferedOutputStream(new FileOutputStream(LIBFILE));
           XMLEncoder xmle = new XMLEncoder(bos))
      {
         xmle.writeObject(library);
      }
   }
   public static void main(String[] args)
   {
      Endpoint.publish("http://localhost:9902/library", new Library());
   }
}
