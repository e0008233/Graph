import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PA2
{
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");

    System.out.println("hello");
    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:D:/UCSD/CSE132A/PA2/data/pa2.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      

      ResultSet rs = statement.executeQuery("select * from flight");
      while(rs.next())
      {
        // read the result set
    	String airName = rs.getString("airline");
    	String origin= rs.getString("origin");
    	String destination= rs.getString("destination");
//        System.out.println("airline = " + airName);
//        System.out.println("origin = " + origin);
//        System.out.println("destination = " + destination);
    	
      }
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}
class Vertex {
	public String id;
	public boolean visited;
	public Set<String> edges = new HashSet<String>();
	public Vertex(String airlineName){
		id=airlineName;
		visited=false;
		
	}
}
class Graph {

	public Hashtable<String,Vertex> vertices;
	public Graph() {
		vertices = new Hashtable<String,Vertex>(); 
	}


};