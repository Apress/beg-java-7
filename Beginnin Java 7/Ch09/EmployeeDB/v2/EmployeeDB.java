import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

class EmployeeDB
{
   public static void main(String[] args)
   {
      String url = "jdbc:derby:employee;create=true";
      try (Connection con = DriverManager.getConnection(url))
      {
         try (Statement stmt = con.createStatement())
         {
            String sql = "CREATE TABLE EMPLOYEES(ID INTEGER, NAME VARCHAR(30))";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO EMPLOYEES VALUES(?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql))
            {
               String[] empNames = {"John Doe", "Sally Smith"};
               for (int i = 0; i < empNames.length; i++)
               {
                  pstmt.setInt(1, i+1);
                  pstmt.setString(2, empNames[i]);
                  pstmt.executeUpdate();
               }
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
            while (rs.next())
               System.out.println(rs.getInt("ID")+" "+rs.getString("NAME"));
            sql = "DROP TABLE EMPLOYEES";
            stmt.executeUpdate(sql);
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
}
