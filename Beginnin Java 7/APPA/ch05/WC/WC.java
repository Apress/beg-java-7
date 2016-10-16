import java.io.IOException;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

class WC
{
   public static void main(String[] args) throws IOException
   {
      int ch;
      Map<String, Integer> map = new TreeMap<>();
      // Read each character from standard input until a letter
      // is read. This letter indicates the start of a word.
      while ((ch = System.in.read()) != -1)
      {
         // If character is a letter then start of word detected.
         if (Character.isLetter((char) ch))
         {
            // Create StringBuffer object to hold word letters.
            StringBuffer sb = new StringBuffer();
            // Place first letter character into StringBuffer object.
            sb.append((char) ch);
            // Place all subsequent letter characters into StringBuffer
            // object.
            do
            {
               ch = System.in.read();
               if (Character.isLetter((char) ch))
                  sb.append((char) ch);
               else
                  break;
            }
            while (true);
            // Insert word into map.
            String word = sb.toString();
            if (map.get(word) == null)
                map.put(word, 1);
            else
                map.put(word, map.get(word)+1);
         }
      }
      // Output map in ascending order.
      Iterator i = map.entrySet().iterator();
      while (i.hasNext())
         System.out.println(i.next());
   }
}
