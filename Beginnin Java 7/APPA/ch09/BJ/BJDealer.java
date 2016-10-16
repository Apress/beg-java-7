import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class BJDealer
{
   final static int PORTNO = 10000;
   public static void main(String[] args)
   {
      System.out.println("Blackjack server starting...");
      try
      {
         ServerSocket ss = new ServerSocket(PORTNO);
         while (true)
            new ConnectionThread(ss.accept()).start();
      }
      catch (IOException ioe)
      {
         System.out.println("I/O error: "+ioe.getMessage());
      }
   }
}
class ConnectionThread extends Thread
{
   private Socket s;
   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   private List<Card> deck, dhand, phand;
   // Index of top card on deck to retrieve. Assume that a retrieved card is
   // removed from the deck (although this doesn't actually happen) and that
   // topCard refers to the next card on the deck -- the card below the
   // previous top card.
   private int topCard;
   ConnectionThread(Socket s)
   {
      this.s = s;
      deck = Card.newDeck();
      dhand = new ArrayList<Card>();
      phand = new ArrayList<Card>();
   }
   void doDeal(ObjectOutputStream oos) throws IOException
   {
      // Shuffle the deck if the dealer had less than 20 cards in previous
      // round.
      if (dhand.size() < 20)
         Collections.shuffle(deck);
      // Begin dealing cards with the first card on the deck.
      topCard = 0;
      // Reset player's hand to nothing.
      phand.clear();
      // Deal first two cards to player.
      phand.add(deck.get(topCard++));
      phand.add(deck.get(topCard++));
      // Show these cards to player.
      oos.writeObject(phand.get(0));
      oos.writeObject(phand.get(1));
      // Reset dealer's hand to nothing.
      dhand.clear();
      // Deal first two cards to dealer.
      dhand.add(deck.get(topCard++));
      dhand.add(deck.get(topCard++));
      // Show dealer's first card to player.
      oos.writeObject(dhand.get(0));
      // Evaluate player's hand for Blackjack (21). ACE considered to be worth
      // 11.
      int pvalue1 = phand.get(0).getValue();
      if (pvalue1 == Card.Rank.ACE.getValue())
         pvalue1 = 11;
      else
      if (pvalue1 == Card.Rank.JACK.getValue() ||
          pvalue1 == Card.Rank.QUEEN.getValue() ||
          pvalue1 == Card.Rank.KING.getValue())
         pvalue1 = 10;
      int pvalue2 =  phand.get(1).getValue();
      if (pvalue2 == Card.Rank.ACE.getValue())
         pvalue2 = 11;
      else
      if (pvalue2 == Card.Rank.JACK.getValue() ||
          pvalue2 == Card.Rank.QUEEN.getValue() ||
          pvalue2 == Card.Rank.KING.getValue())
         pvalue2 = 10;
      int psum = pvalue1+pvalue2;
      // Evaluate dealer's hand for Blackjack (21). ACE considered to be worth
      // 11.
      int dvalue1 = dhand.get(0).getValue();
      if (dvalue1 == Card.Rank.ACE.getValue())
         dvalue1 = 11;
      else
      if (dvalue1 == Card.Rank.JACK.getValue() ||
          dvalue1 == Card.Rank.QUEEN.getValue() ||
          dvalue1 == Card.Rank.KING.getValue())
         dvalue1 = 10;
      int dvalue2 = dhand.get(1).getValue();
      if (dvalue2 == Card.Rank.ACE.getValue())
         dvalue2 = 11;
      else
      if (dvalue2 == Card.Rank.JACK.getValue() ||
          dvalue2 == Card.Rank.QUEEN.getValue() ||
          dvalue2 == Card.Rank.KING.getValue())
         dvalue2 = 10;
      int dsum = dvalue1+dvalue2;
      // If dealer has Blackjack and player has Blackjack...
      if (dsum == 21 && psum == 21)
      {
         // Indicate a push -- no one wins.
         oos.writeObject("Status: Dealer and player tie.");
         // Show dealer's second card to player.
         oos.writeObject(dhand.get(1));
      }
      else // If dealer has Blackjack and player does not have Blackjack...
      if (dsum == 21 && psum != 21)
      {
         // Indicate dealer wins. Blackjack!
         oos.writeObject("Status: Blackjack! Dealer wins.");
         // Show dealer's second card to player.
         oos.writeObject(dhand.get(1));
      }
      else // If player has Blackjack and dealer does not have Blackjack...
      if (psum == 21 && dsum != 21)
      {
         // Indicate player wins. Blackjack!
         oos.writeObject("Status: Blackjack! Player wins.");
         // Show dealer's second card to player.
         oos.writeObject(dhand.get(1));
      }
      else
         oos.writeObject(""); // Indicate not-end-of-round.
   }
   void doHit(ObjectOutputStream oos) throws IOException
   {
      // Deal card to player.
      phand.add(deck.get(topCard++));
      // Show this card to player.
      oos.writeObject(phand.get(phand.size()-1));
      // Total player's cards in optimal manner. ACE considered 1 or 11.
      int psum = 0;
      Iterator<Card> iter = phand.iterator();
      while (iter.hasNext())
      {
         int value = iter.next().getValue();
         if (value == Card.Rank.ACE.getValue())
            value = 11;
         else
         if (value == Card.Rank.JACK.getValue() ||
             value == Card.Rank.QUEEN.getValue() ||
             value == Card.Rank.KING.getValue())
            value = 10;
         psum += value;
      }
      if (psum > 21)
      {
         psum = 0;
         iter = phand.iterator();
         while (iter.hasNext())
         {
            int value = iter.next().getValue();
            if (value == Card.Rank.JACK.getValue() ||
                value == Card.Rank.QUEEN.getValue() ||
                value == Card.Rank.KING.getValue())
               value = 10;
            psum += value;
         }
      }
      // Dealer automatically wins when player goes over 21.
      if (psum > 21)
      {
         // Indicate dealer wins.
         oos.writeObject("Status: Player > 21. Dealer wins.");
         // Show dealer's second card to player.
         oos.writeObject(dhand.get(1));
      }
      else
         oos.writeObject(""); // Indicate not-end-of-round.
   }
   void doStand(ObjectOutputStream oos) throws IOException
   {
      // Show dealer's second card to player.
      oos.writeObject(dhand.get(1));
      // The dealer keeps taking hits while sum < 17. When sum >= 17, dealer
      // stands.
      while (true)
      {
         // Sum dealer's cards where ACE is always considered 11.
         int dsum = 0;
         Iterator<Card> iter = dhand.iterator();
         while (iter.hasNext())
         {
            int value = iter.next().getValue();
            if (value == Card.Rank.ACE.getValue())
               value = 11;
            else
            if (value == Card.Rank.JACK.getValue() ||
                value == Card.Rank.QUEEN.getValue() ||
                value == Card.Rank.KING.getValue())
               value = 10;
            dsum += value;
         }
         if (dsum < 17)
         {
            // Dealer takes a hit. Deal another card to dealer.
            dhand.add(deck.get(topCard++));
            continue;
         }
         break;
      }
      // Evaluate player's hand. ACE is either 1 or 11 -- whichever is optimal.
      int psum = 0;
      Iterator<Card> iter = phand.iterator();
      while (iter.hasNext())
      {
         int value = iter.next().getValue();
         if (value == Card.Rank.ACE.getValue())
            value = 11;
         else
         if (value == Card.Rank.JACK.getValue() ||
             value == Card.Rank.QUEEN.getValue() ||
             value == Card.Rank.KING.getValue())
            value = 10;
         psum += value;
      }
      if (psum > 21)
      {
          psum = 0;
          iter = phand.iterator();
          while (iter.hasNext())
          {
             int value = iter.next().getValue();
             if (value == Card.Rank.JACK.getValue() ||
                 value == Card.Rank.QUEEN.getValue() ||
                 value == Card.Rank.KING.getValue())
                value = 10;
             psum += value;
          }
      }
      // Evaluate dealer's hand. ACE is either 1 or 11 -- whichever is
      // optimal.
      int dsum = 0;
      iter = dhand.iterator();
      while (iter.hasNext())
      {
         int value = iter.next().getValue();
         if (value == Card.Rank.ACE.getValue())
            value = 11;
         else
         if (value == Card.Rank.JACK.getValue() ||
             value == Card.Rank.QUEEN.getValue() ||
             value == Card.Rank.KING.getValue())
            value = 10;
         dsum += value;
      }
      if (dsum > 21)
      {
          dsum = 0;
          iter = dhand.iterator();
          while (iter.hasNext())
          {
             int value = iter.next().getValue();
             if (value == Card.Rank.JACK.getValue() ||
                 value == Card.Rank.QUEEN.getValue() ||
                 value == Card.Rank.KING.getValue())
                value = 10;
             dsum += value;
          }
      }
      // Figure out whom (if any) won this round.
      if (psum > 21 && dsum > 21)
         oos.writeObject("Status: Dealer > 21 and player > 21.");
      else
      if (psum > 21)
         oos.writeObject("Status: Player > 21. Dealer wins.");
      else
      if (dsum > 21)
         oos.writeObject("Status: Dealer > 21. Player wins.");
      else
      if (dsum > psum)
         oos.writeObject("Status: Dealer wins.");
      else
      if (dsum < psum)
         oos.writeObject("Status: Player wins.");
      else
         oos.writeObject("Status: Player and dealer tie.");
      // Inform client of all cards in dealer's hand. This lets the player
      // see that the server isn't cheating.
      oos.writeInt(dhand.size());
      for (int i = 0; i < dhand.size(); i++)
         oos.writeObject(dhand.get(i));
   }
   // Allow connection thread to run until client sends a Deal command -- or
   // the client automatically terminates the connection.
   @Override
   public void run()
   {
      try (ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
           ObjectInputStream ois = new ObjectInputStream(s.getInputStream()))
      {
         oos.flush();
         do
         {
            String cmd = (String) ois.readObject();
            switch (cmd)
            {
               case "Deal" : doDeal(oos); break;
               case "Hit"  : doHit(oos); break;
               case "Stand": doStand(oos); break;
               case "Quit" : return;
            }
         }
         while (true);
      }
      catch (ClassNotFoundException cnfe)
      {
         cnfe.printStackTrace();
      }
      catch (IOException ioe)
      {
         ioe.printStackTrace();
      }
   }
}
