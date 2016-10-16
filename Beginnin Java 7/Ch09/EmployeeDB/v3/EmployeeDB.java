import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDB
{
   public static void main(String[] args)
   {
      String url = "jdbc:derby:employee;create=true";
      try (Connection con = DriverManager.getConnection(url))
      {
         try (Statement stmt = con.createStatement())
         {
            String sql = "CREATE PROCEDURE FIRE(IN ID INTEGER)"+
                         "   PARAMETER STYLE JAVA"+
                         "   LANGUAGE JAVA"+
                         "   DYNAMIC RESULT SETS 0"+
                         "   EXTERNAL NAME 'EmployeeDB.fire'";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE EMPLOYEES(ID INTEGER, NAME VARCHAR(30), "+
                                          "FIRED BOOLEAN)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO EMPLOYEES VALUES(1, 'John Doe', false)";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO EMPLOYEES VALUES(2, 'Sally Smith', false)";
            stmt.executeUpdate(sql);
            dump(stmt.executeQuery("SELECT * FROM EMPLOYEES"));
            try (CallableStatement cstmt = con.prepareCall("{ call FIRE(?)}"))
            {
               cstmt.setInt(1, 2);
               cstmt.execute();
            }
            dump(stmt.executeQuery("SELECT * FROM EMPLOYEES"));
            sql = "DROP TABLE EMPLOYEES";
            stmt.executeUpdate(sql);
            sql = "DROP PROCEDURE FIRE";
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
   static void dump(ResultSet rs) throws SQLException
   {
      while (rs.next())
         System.out.println(rs.getInt("ID")+" "+rs.getString("NAME")+
                            " "+rs.getBoolean("FIRED"));
      System.out.println();
   }
   public static void fire(int id) throws SQLException
   {
      Connection con = DriverManager.getConnection("jdbc:default:connection");
      String sql = "UPDATE EMPLOYEES SET FIRED=TRUE WHERE ID="+id;
      try (Statement stmt = con.createStatement())
      {
         stmt.executeUpdate(sql);
      }
   }
}
