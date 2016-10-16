import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.BevelBorder;

public class BJPlayer extends JFrame
{
   final static int PORTNO = 10000;
   final static String HOST = "localhost";
   static Socket s;
   static ObjectInputStream ois;
   static ObjectOutputStream oos;
   List<Card> dhand, phand;
   // Cards are displayed on the following panel. Player cards are displayed
   // on top and dealer cards are displayed half-way down.
   CardPanel pnlCards;
   JButton btnDeal, btnExit, btnHit, btnStand;
   JLabel lblStatus;
   BJPlayer(String title)
   {
      super(title);
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      addWindowListener(new WindowAdapter()
                        {
                           @Override
                           public void windowClosing(WindowEvent we)
                           {
                              try
                              {
                                 oos.writeObject("Quit");
                                 ois.close();
                                 oos.close();
                                 s.close();
                               }
                               catch (IOException ioe)
                               {
                               }
                               dispose();
                            }
                         });
      dhand = new ArrayList<Card>();
      phand = new ArrayList<Card>();
      pnlCards = new CardPanel(dhand, phand);
      pnlCards.setBorder(new BevelBorder(BevelBorder.LOWERED));
      getContentPane().add(pnlCards, BorderLayout.NORTH);
      btnDeal = new JButton("Deal");
      ActionListener al;
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 dhand.clear();
                 phand.clear();
                 pnlCards.repaint();
                 try
                 {
                    oos.writeObject("Deal");
                    phand.add((Card) ois.readObject());
                    phand.add((Card) ois.readObject());
                    dhand.add((Card) ois.readObject());
                    pnlCards.repaint();
                    // Retrieve status message or not-end-of-round message,
                    // and update GUI status.
                    String status = (String) ois.readObject();
                    lblStatus.setText(status);
                    // If not-end-of-round, disable Deal button and make sure
                    // Hit and Stand buttons are enabled. Otherwise, do the
                    // reverse.
                    if (status.equals(""))
                    {
                       btnDeal.setEnabled(false);
                       btnHit.setEnabled(true);
                       btnStand.setEnabled(true);
                    }
                    else
                    {
                       // Retrieve dealer's second card.
                       dhand.add((Card) ois.readObject());
                       pnlCards.repaint();
                       btnDeal.setEnabled(true);
                       btnHit.setEnabled(false);
                       btnStand.setEnabled(false);
                    }
                 }
                 catch (ClassNotFoundException cnfe)
                 {
                    showMessage(cnfe.toString());
                 }
                 catch (IOException ioe)
                 {
                    showMessage(ioe.toString());
                 }
               }
           };
      btnDeal.addActionListener(al);
      btnExit = new JButton("Exit");
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 try
                 {
                    oos.writeObject("Quit");
                 }
                 catch (IOException ioe)
                 {
                    showMessage(ioe.toString());
                 }
                 dispose();
              }
           };
      btnExit.addActionListener(al);
      btnHit = new JButton("Hit");
      btnHit.setEnabled(false);
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 try
                 {
                    oos.writeObject("Hit");
                    // Retrieve player's next-dealt card.
                    phand.add((Card) ois.readObject());
                    pnlCards.repaint();
                    // Retrieve status message or not-end-of-round message,
                    // and update GUI status.
                    String status = (String) ois.readObject();
                    lblStatus.setText(status);
                    // If not-end-of-round, disable Deal button and make sure
                    // Hit and Stand buttons are enabled. Otherwise, do the
                    // reverse.
                    if (status.equals(""))
                    {
                       btnDeal.setEnabled(false);
                       btnHit.setEnabled(true);
                       btnStand.setEnabled(true);
                    }
                    else
                    {
                       // Retrieve dealer's second card.
                       dhand.add((Card) ois.readObject());
                       pnlCards.repaint();
                       btnDeal.setEnabled(true);
                       btnHit.setEnabled(false);
                       btnStand.setEnabled(false);
                    }
                 }
                 catch (ClassNotFoundException cnfe)
                 {
                    showMessage(cnfe.toString());
                 }
                 catch (IOException ioe)
                 {
                    showMessage(ioe.toString());
                 }
              }
           };
      btnHit.addActionListener(al);
      btnStand = new JButton("Stand");
      btnStand.setEnabled(false);
      al = new ActionListener()
           {
              @Override
              public void actionPerformed(ActionEvent ae)
              {
                 try
                 {
                    oos.writeObject("Stand");
                    // Retrieve dealer's second card.
                    dhand.add((Card) ois.readObject());
                    pnlCards.repaint();
                    // Retrieve status message and update GUI status.
                    // Round is over at this point.
                    String status = (String) ois.readObject();
                    lblStatus.setText(status);
                    // Retrieve all of the dealer's cards, to ensure that the
                    // dealer isn't cheating.
                    int count = ois.readInt();
                    dhand.clear();
                    for (int i = 0; i < count; i++)
                       dhand.add((Card) ois.readObject());
                    pnlCards.repaint();
                    // End of round. Make sure Deal button is enabled and the
                    // Hit and Stand buttons are disabled.
                    btnDeal.setEnabled(true);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                  }
                  catch (ClassNotFoundException cnfe)
                  {
                     showMessage(cnfe.toString());
                  }
                  catch (IOException ioe)
                  {
                     showMessage(ioe.toString());
                  }
              }
           };
      btnStand.addActionListener(al);
      lblStatus = new JLabel("Status:");
      JPanel pnlLeft = new JPanel();
      pnlLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
      pnlLeft.add(lblStatus);
      JPanel pnlRight = new JPanel();
      pnlRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
      pnlRight.add(btnDeal);
      pnlRight.add(btnHit);
      pnlRight.add(btnStand);
      pnlRight.add(btnExit);
      JPanel pnlControl = new JPanel();
      pnlControl.setLayout(new GridLayout(1, 2));
      pnlControl.setBorder(new BevelBorder(BevelBorder.LOWERED));
      pnlControl.add(pnlLeft);
      pnlControl.add(pnlRight);
      getContentPane().add(pnlControl, BorderLayout.SOUTH);
      pack();
      setResizable(false);
      setVisible(true);
   }
   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         try
                         {
                            s = new Socket(HOST, PORTNO);
                            oos = new ObjectOutputStream(s.getOutputStream());
                            oos.flush();
                            ois = new ObjectInputStream(s.getInputStream());
                            new BJPlayer("Blackjack Player");
                         }
                         catch (IOException ioe)
                         {
                            showMessage(ioe.toString());
                            try
                            {
                               if (ois != null)
                                  ois.close();
                               if (oos != null)
                                  oos.close();
                               if (s != null)
                                  s.close();
                            }
                            catch (IOException ioe2)
                            {
                            }
                            System.exit(0);
                         }
                      }
                   };
      EventQueue.invokeLater(r);
   }
   static void showMessage(String message)
   {
      JOptionPane.showMessageDialog(null, message, "Blackjack Player",
                                    JOptionPane.INFORMATION_MESSAGE);
   }
}
class CardPanel extends JComponent
{
   private static final int WIDTH = 500;
   private static final int HEIGHT = 500;
   final static int CARD_HEIGHT = 75;
   final static int CARD_WIDTH = 60;
   final static int CARD_SEP = 10;
   private List<Card> dhand, phand;
   CardPanel(List<Card> dhand, List<Card> phand)
   {
      this.dhand = dhand;
      this.phand = phand;
   }
   public Dimension getPreferredSize()
   {
      return new Dimension(WIDTH, HEIGHT);
   }
   @Override
   public void paint(Graphics g)
   {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(Color.BLACK);
      // Draw player's cards.
      int col = 5;
      int row = 5;
      for (int i = 0; i < phand.size(); i++)
      {
         drawCard(phand.get(i), g, col, row);
         col += CARD_WIDTH+CARD_SEP;
         if (col+CARD_WIDTH > WIDTH)
         {
            col = 0;
            row += CARD_HEIGHT/2;
         }
      }
      // Draw dealer's cards.
      col = 5;
      row = HEIGHT/2;
      for (int i = 0; i < dhand.size(); i++)
      {
         drawCard(dhand.get(i), g, col, row);
         col += CARD_WIDTH+CARD_SEP;
         if (col+CARD_WIDTH > WIDTH)
         {
            col = 0;
            row += CARD_HEIGHT/2;
         }
      }
   }
   void drawCard(Card card, Graphics g, int x, int y)
   {
      if (card.getSuit() == Card.Suit.DIAMONDS ||
          card.getSuit() == Card.Suit.HEARTS)
         g.setColor(Color.RED);
      else
         g.setColor(Color.BLACK);
      g.drawRoundRect(x, y, CARD_WIDTH, CARD_HEIGHT, 8, 8);
      FontMetrics fm = g.getFontMetrics();
      int height = fm.getHeight();
      String v;
      switch (card.getRank())
      {
         case ACE  : v = "A"; break;
         case JACK : v = "J"; break;
         case QUEEN: v = "Q"; break;
         case KING : v = "K"; break;
         default   : v = ""+card.getRank().getValue();
      }
      int width = fm.stringWidth(v);
      g.drawString(v, x+5, y+height);
      g.drawString(v, x+CARD_WIDTH-width-5, y+CARD_HEIGHT-5);
      String symbol = "";
      switch (card.getSuit())
      {
         case CLUBS   : symbol = "C"; break;
         case DIAMONDS: symbol = "D"; break;
         case HEARTS  : symbol = "H"; break;
         case SPADES  : symbol = "S";
      }
      width = fm.stringWidth(symbol);
      g.drawString(symbol, x+(CARD_WIDTH-width)/2, y+CARD_HEIGHT/2+height/4);
   }
}
