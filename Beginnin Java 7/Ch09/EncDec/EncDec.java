import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.net.URLEncoder;

class EncDec
{
   public static void main(String[] args) throws UnsupportedEncodingException
   {
      String encodedData = URLEncoder.encode("The string ü@foo-bar", "UTF-8");
      System.out.println(encodedData);
      System.out.println(URLDecoder.decode(encodedData, "UTF-8"));
   }
}
