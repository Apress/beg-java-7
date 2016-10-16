import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

class ChatServer
{
   private final static int PORT_NO = 8010;
   private ServerSocket listener;
   private List<Connection> clients;
   ChatServer() throws IOException
   {
      listener = new ServerSocket(PORT_NO);
      clients = new ArrayList<>();
      System.out.println("listening on port "+PORT_NO);
   }
   void runServer()
   {
      try
      {
         while (true)
         {
            Socket socket = listener.accept();
            System.out.println("accepted connection");
            Connection con = new Connection(socket);
            synchronized(clients)
            {
               clients.add(con);
               con.start();
               if (clients.size() == 1)
                  con.send("welcome...you're the first user");
               else
                  con.send("welcome...you're the latest of "+clients.size()+
                           " users");
            }
         }
      }
      catch (IOException ioe)
      {
         System.err.println("I/O error: "+ioe.getMessage());
         return;
      }
   }
   private class Connection extends Thread
   {
      private volatile BufferedReader br;
      private volatile PrintWriter pw;
      private String clientName;
      Connection(Socket s) throws IOException
      {
         br = new BufferedReader(new InputStreamReader(s.getInputStream()));
         pw = new PrintWriter(s.getOutputStream(), true);
      }
      @Override
      public void run()
      {
         String line;
         try
         {
            clientName = br.readLine();
            sendClientsList();
            while ((line = br.readLine()) != null)
               broadcast(clientName+": "+line);
         }
         catch (IOException ioe)
         {
            System.err.println("I/O error: "+ioe.getMessage());
         }
         finally
         {
            System.out.println(clientName+": "+"finished");
            synchronized(clients)
            {
               clients.remove(this);
               broadcast("now "+clients.size()+" users");
               sendClientsList();
            }
         }
      }
      private void broadcast(String message)
      {
         System.out.println("broadcasting "+message);
         synchronized(clients)
         {
            for (Connection con: clients)
               con.send(message);
         }
      }
      private void send(String message)
      {
         pw.println(message);
      }
      private void sendClientsList()
      {
         StringBuilder sb = new StringBuilder();
         synchronized(clients)
         {
            for (Connection con: clients)
            {
               sb.append(con.clientName);
               sb.append(" ");
            }
            broadcast("!"+sb.toString());
         }
      }
   }
   public static void main(String[] args)
   {
      try
      {
         System.out.println("ChatServer starting");
         new ChatServer().runServer();
      }
      catch (IOException ioe)
      {
         System.err.println("unable to create server socket");
      }
   }
}