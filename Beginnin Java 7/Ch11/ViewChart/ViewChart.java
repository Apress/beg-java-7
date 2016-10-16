import java.io.InputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class ViewChart
{
   final static String BASEURI = "https://chart.googleapis.com/chart?";
   public static void main(String[] args)
   {
      String qs = "cht=p3&chs=450x200&chd=t:60,40&chl=Q1%20(60%)|Q2%20(40%)";
      ImageIcon ii = doGet(qs);
      if (ii != null)
      {
         JFrame frame = new JFrame("ViewChart");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new JLabel(ii));
         frame.pack();
         frame.setResizable(false);
         frame.setVisible(true);
      }
   }
   static ImageIcon doGet(String qs)
   {
      try
      {
         URL url = new URL(BASEURI+qs);
         HttpURLConnection httpurlc;
         httpurlc = (HttpURLConnection) url.openConnection();
         httpurlc.setRequestMethod("GET");
         httpurlc.setDoInput(true);
         if (httpurlc.getResponseCode() == 200)
         {
            InputStream is = httpurlc.getInputStream();
            byte[] bytes = new byte[10000];
            int b, i = 0;
            while ((b = is.read()) != -1)
            {
               bytes[i++] = (byte) b;
               if (i == bytes.length)
               {
                  byte[] bytes2 = new byte[bytes.length*2];
                  System.arraycopy(bytes, 0, bytes2, 0, i);
                  bytes = bytes2;
               }
            }
            byte[] bytes2 = new byte[i];
            System.arraycopy(bytes, 0, bytes2, 0, i);
            return new ImageIcon(bytes2);
         }
         throw new IOException("HTTP Error: "+httpurlc.getResponseCode());
      }
      catch (IOException e)
      {
         JOptionPane.showMessageDialog(null, e.getMessage(), "ViewChart",
                                       JOptionPane.ERROR_MESSAGE);
         return null;
      }
   }
}
