import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

class LibraryClient
{
   final static String LIBURI = "http://localhost:9902/library";
   public static void main(String[] args) throws Exception
   {
      String book1 = "<?xml version=\"1.0\"?>"+
                     "<book isbn=\"0201548550\" pubyear=\"1992\">"+
                     "  <title>"+
                     "    Advanced C+"+
                     "  </title>"+
                     "  <author>"+
                     "    James O. Coplien"+
                     "  </author>"+
                     "  <publisher>"+
                     "    Addison Wesley"+
                     "  </publisher>"+
                     "</book>";
      doPost(book1);
      String book2 = "<?xml version=\"1.0\"?>"+
                     "<book isbn=\"9781430210450\" pubyear=\"2008\">"+
                     "  <title>"+
                     "    Beginning Groovy and Grails"+
                     "  </title>"+
                     "  <author>"+
                     "    Christopher M. Judd"+
                     "  </author>"+
                     "  <author>"+
                     "    Joseph Faisal Nusairat"+
                     "  </author>"+
                     "  <author>"+
                     "    James Shingler"+
                     "  </author>"+
                     "  <publisher>"+
                     "    Apress"+
                     "  </publisher>"+
                     "</book>";
      doPost(book2);
      doGet(null);
      doGet("0201548550");
      doGet("9781430210450");
      String book1u = "<?xml version=\"1.0\"?>"+
                      "<book isbn=\"0201548550\" pubyear=\"1992\">"+
                      "  <title>"+
                      "    Advanced C++"+
                      "  </title>"+
                      "  <author>"+
                      "    James O. Coplien"+
                      "  </author>"+
                      "  <publisher>"+
                      "    Addison Wesley"+
                      "  </publisher>"+
                      "</book>";
      doPut(book1u);
      doGet("0201548550");
      doDelete("0201548550");
      doGet(null);
   }
   static void doDelete(String isbn) throws Exception
   {
      URL url = new URL(LIBURI+((isbn != null) ? "?isbn="+isbn : ""));
      HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
      httpurlc.setRequestMethod("DELETE");
      httpurlc.setDoInput(true);
      InputStreamReader isr;
      isr = new InputStreamReader(httpurlc.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      StringBuilder xml = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null)
         xml.append(line);
      System.out.println(xml);
      System.out.println();
   }
   static void doGet(String isbn) throws Exception
   {
      URL url = new URL(LIBURI+((isbn != null) ? "?isbn="+isbn : ""));
      HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
      httpurlc.setRequestMethod("GET");
      httpurlc.setDoInput(true);
      InputStreamReader isr;
      isr = new InputStreamReader(httpurlc.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      StringBuilder xml = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null)
         xml.append(line);
      System.out.println(xml);
      System.out.println();
   }
   static void doPost(String xml) throws Exception
   {
      URL url = new URL(LIBURI);
      HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
      httpurlc.setRequestMethod("POST");
      httpurlc.setDoOutput(true);
      httpurlc.setDoInput(true);
      httpurlc.setRequestProperty("Content-Type", "text/xml");
      OutputStream os = httpurlc.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
      osw.write(xml);
      osw.close();
      if (httpurlc.getResponseCode() == 200)
      {
         InputStreamReader isr;
         isr = new InputStreamReader(httpurlc.getInputStream());
         BufferedReader br = new BufferedReader(isr);
         StringBuilder sb = new StringBuilder();
         String line;
         while ((line = br.readLine()) != null)
            sb.append(line);
         System.out.println(sb.toString());
      }
      else
         System.err.println("cannot insert book: "+httpurlc.getResponseCode());
      System.out.println();
   }
   static void doPut(String xml) throws Exception
   {
      URL url = new URL(LIBURI);
      HttpURLConnection httpurlc = (HttpURLConnection) url.openConnection();
      httpurlc.setRequestMethod("PUT");
      httpurlc.setDoOutput(true);
      httpurlc.setDoInput(true);
      httpurlc.setRequestProperty("Content-Type", "text/xml");
      OutputStream os = httpurlc.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
      osw.write(xml);
      osw.close();
      if (httpurlc.getResponseCode() == 200)
      {
         InputStreamReader isr;
         isr = new InputStreamReader(httpurlc.getInputStream());
         BufferedReader br = new BufferedReader(isr);
         StringBuilder sb = new StringBuilder();
         String line;
         while ((line = br.readLine()) != null)
            sb.append(line);
         System.out.println(sb.toString());
      }
      else
         System.err.println("cannot update book: "+httpurlc.getResponseCode());
      System.out.println();
   }
}
