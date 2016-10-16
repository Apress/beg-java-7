import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

class Card implements Serializable
{
   enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
   enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
               JACK, QUEEN, KING;
               int getValue()
               {
                  return ordinal()+1;
               }
             }
   private Suit suit;
   private Rank rank;
   private static final List<Card> initialDeck = new ArrayList<Card>();
   Card(Suit suit, Rank rank)
   {
      this.suit = suit;
      this.rank = rank;
   }
   Rank getRank()
   {
      return rank;
   }
   Suit getSuit()
   {
      return suit;
   }
   int getValue()
   {
      return rank.ordinal()+1;
   }
   static
   {
      for (Suit suit: Suit.values())
         for (Rank rank: Rank.values())
            initialDeck.add(new Card(suit, rank));
   }
   static List<Card> newDeck() // Return a new unshuffled deck.
   {
      // Copy initial deck to new deck.
      List<Card> deck = new ArrayList<Card>(initialDeck);
      return deck;
   }
}
