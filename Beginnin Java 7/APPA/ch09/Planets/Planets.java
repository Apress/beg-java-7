import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.border.Border;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

class Planets
{
   static String[] names, notes;
   static double[] diameters, masses, distances;
   static int[] moons;
   static ImageIcon[] iiPhotos;
   static JPanel createGUI()
   {
      JPanel pnlGUI = new JPanel();
      pnlGUI.setLayout(new BorderLayout());
      final SwingCanvas sc = new SwingCanvas(iiPhotos[0]);
      pnlGUI.add(sc, BorderLayout.NORTH);
      final JLabel lblNotes = new JLabel(notes[0], JLabel.CENTER);
      lblNotes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      pnlGUI.add(lblNotes, BorderLayout.CENTER);
      TableModel model = new AbstractTableModel()
      {
         @Override
         public int getColumnCount()
         {
            return 5;
         }
         @Override
         public String getColumnName(int column)
         {
            switch (column)
            {
               case 0: return "Name";
               case 1: return "Diameter (KM)";
               case 2: return "Mass (KG)";
               case 3: return "Distance (AU)";
               default: return "Moons";
            }
         }
         @Override
         public int getRowCount()
         {
            return names.length;
         }
         @Override
         public Object getValueAt(int row, int col)
         {
            switch (col)
            {
               case 0: return Character.toUpperCase(names[row].charAt(0))+
                       names[row].substring(1);
               case 1: return diameters[row];
               case 2: return masses[row];
               case 3: return distances[row];
               default: return moons[row];
            }
         }
      };
      final JTable table = new JTable(model);
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setRowSelectionInterval(0, 0);
      ListSelectionListener lsl;
      lsl = new ListSelectionListener()
            {
               @Override
               public void valueChanged(ListSelectionEvent lse)
               {
                  sc.setPhoto(iiPhotos[table.getSelectedRow()]);
                  lblNotes.setText(notes[table.getSelectedRow()]);
               }
            };
      table.getSelectionModel().addListSelectionListener(lsl);
      // The following scrollpane is need to ensure that table headers are
      // displayed. They aren't displayed when the table is added directly to
      // the content pane.
      JScrollPane sp = new JScrollPane(table);
      // Make sure that the table displays exactly 8 rows.
      Dimension size = sp.getViewport().getPreferredSize();
      size.width *= 1.7;
      size.height = 8*table.getRowHeight();
      table.setPreferredScrollableViewportSize(size);
      pnlGUI.add(sp, BorderLayout.SOUTH);
      return pnlGUI;
   }
   static void initDB()
   {
      String[] planets = { "mercury", "venus", "earth", "mars", "jupiter",
                           "saturn", "uranus", "neptune" };
      double[] diameters = { 4880, 12103.6, 12756.3, 6794, 142984, 120536,
                             51118, 49532 };
      double[] masses = { 3.3e23, 4.869e24, 5.972e24, 6.4219e23, 1.9e27,
                          5.68e26, 8.683e25, 1.0247e26 };
      double[] distances = { 0.38, 0.72, 1, 1.52, 5.2, 9.54, 19.218, 30.06 };
      int[] moons = { 0, 0, 1, 2, 64, 200, 27, 13 };
      String[] notes =
      {
         "closest planet to the sun",
         "covered with opaque layer of highly-reflective sulfuric clouds",
         "supports life",
         "tallest volcanoes in solar system",
         "gas giant, largest planet in solar system",
         "gas giant, most prominent rings",
         "gas giant, coldest planetary atmosphere of solar system planets",
         "gas giant, farthest planet from the sun"
      };
      String url = "jdbc:derby:planets;create=true";
      try (Connection con = DriverManager.getConnection(url))
      {
         try (Statement stmt = con.createStatement())
         {
            String sql = "CREATE TABLE PLANETS(NAME VARCHAR(30),"+
                                               "DIAMETER REAL,"+
                                               "MASS REAL,"+
                                               "DISTANCE REAL,"+
                                               "PHOTO BLOB,"+
                                               "MOONS INTEGER,"+
                                               "NOTES VARCHAR(100))";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO PLANETS VALUES(?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql))
            {
               for (int i = 0; i < planets.length; i++)
               {
                  pstmt.setString(1, planets[i]);
                  pstmt.setDouble(2, diameters[i]);
                  pstmt.setDouble(3, masses[i]);
                  pstmt.setDouble(4, distances[i]);
                  Blob blob = con.createBlob();
                  try (ObjectOutputStream oos =
                         new ObjectOutputStream(blob.setBinaryStream(1)))
                  {
                     ImageIcon photo = new ImageIcon(planets[i]+".jpg");
                     oos.writeObject(photo);
                  }
                  catch (IOException ioe)
                  {
                     System.err.println("unable to write "+planets[i]+".jpg");
                  }
                  pstmt.setBlob(5, blob);
                  pstmt.setInt(6, moons[i]);
                  pstmt.setString(7, notes[i]);
                  pstmt.executeUpdate();
                  blob.free();
               }
            }
         }
      }
      catch (SQLException sqlex)
      {
         while (sqlex != null)
         {
            System.err.println("SQL error : "+sqlex.getMessage());
            System.err.println("SQL state : "+sqlex.getSQLState());
            System.err.println("Error code: "+sqlex.getErrorCode());
            System.err.println("Cause: "+sqlex.getCause());
            sqlex = sqlex.getNextException();
         }
      }
   }
   static boolean loadDB()
   {
      String url = "jdbc:derby:planets;create=false";
      try (Connection con = DriverManager.getConnection(url))
      {
         try (Statement stmt = con.createStatement())
         {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PLANETS");
            rs.next();
            int size = rs.getInt(1);
            names = new String[size];
            diameters = new double[size];
            masses = new double[size];
            distances = new double[size];
            iiPhotos = new ImageIcon[size];
            moons = new int[size];
            notes = new String[size];
            rs = stmt.executeQuery("SELECT * FROM PLANETS");
            for (int i = 0; i < size; i++)
            {
               rs.next();
               names[i] = rs.getString(1);
               diameters[i] = rs.getDouble(2);
               masses[i] = rs.getDouble(3);
               distances[i] = rs.getDouble(4);
               Blob blob = rs.getBlob(5);
               try (ObjectInputStream ois =
                      new ObjectInputStream(blob.getBinaryStream()))
               {
                  iiPhotos[i] = (ImageIcon) ois.readObject();
               }
               catch (ClassNotFoundException|IOException cnfioe)
               {
                  System.err.println("unable to read "+names[i]+".jpg");
               }
               blob.free();
               moons[i] = rs.getInt(6);
               notes[i] = rs.getString(7);
            }
            return true;
         }
      }
      catch (SQLException sqlex)
      {
         while (sqlex != null)
         {
            System.err.println("SQL error : "+sqlex.getMessage());
            System.err.println("SQL state : "+sqlex.getSQLState());
            System.err.println("Error code: "+sqlex.getErrorCode());
            System.err.println("Cause: "+sqlex.getCause());
            sqlex = sqlex.getNextException();
         }
         return false;
      }
   }
   public static void main(String[] args)
   {
      if (args.length == 1 && args[0].equalsIgnoreCase("INITDB"))
      {
         initDB();
         return;
      }
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         if (!loadDB())
                            return;
                         JFrame f = new JFrame("Planets");
                         f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                         Border border;
                         border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
                         f.getRootPane().setBorder(border);
                         f.setContentPane(createGUI());
                         f.pack();
                         f.setResizable(false);
                         f.setVisible(true);
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
class SwingCanvas extends JComponent
{
   private ImageIcon iiPhoto;
   private Dimension d;
   SwingCanvas(ImageIcon iiPhoto)
   {
      d = new Dimension(504, 403); // Create object here to avoid unnecessary 
                                   // object creation should getPreferredSize()
                                   // be called more than once.
      this.iiPhoto = iiPhoto;
   }
   @Override
   public Dimension getPreferredSize()
   {
      return d;
   }
   @Override
   public void paint(Graphics g)
   {
      if (iiPhoto != null)
         g.drawImage(iiPhoto.getImage(), (getParent().getWidth()-504)/2, 0,
                     null);
   }
   void setPhoto(ImageIcon iiPhoto)
   {
      this.iiPhoto = iiPhoto;
      repaint();
   }
}
