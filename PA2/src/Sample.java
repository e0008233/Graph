//// CSE 132A Winter 2018, PA 2
////
//// Name: Zhang Hao
//// PID: A15369522
//// Lab Id: cs132abe
////
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.*;
//
//public class PA2
//{
//  public static void main(String[] args) throws ClassNotFoundException
//  {
//    // load the sqlite-JDBC driver using the current class loader
//    Class.forName("org.sqlite.JDBC");
//    System.out.println("hello");
//    Connection connection = null;
//    Set<String> airlines = new HashSet<String>();
//    Hashtable<String,Graph> airlineMaps = new  Hashtable<String,Graph>();
//    String test = "Aberdeen";
//    try
//    {
//      // create a database connection
//      connection = DriverManager.getConnection("jdbc:sqlite:D:/UCSD/CSE132A/PA2/data/pa2.db");
//      Statement statement = connection.createStatement();
//      statement.setQueryTimeout(30);  // set timeout to 30 sec.
//     
//    
//      ResultSet rs = statement.executeQuery("select * from flight");
//      while(rs.next())
//      {
//        // read the result set
//    	String airName = rs.getString("airline");
//    	String origin= rs.getString("origin");
//    	String destination= rs.getString("destination");
//    	if (test.equals(origin)) {
//    		 System.out.println("airline = " + origin.length());
//    	}
//    	if (!airlines.contains(airName)){ 			  			 
//    		airlineMaps.put(airName, new Graph(airName));
//    	}
//    	Graph airlineMap = airlineMaps.get(airName);
//    	if (!airlineMaps.get(airName).vertices.contains(origin)) {
//    		airlineMaps.get(airName).vertices.put(origin, new Vertex(origin));	
//    	}
//    	if (!airlineMaps.get(airName).vertices.contains(destination)) {
//    		airlineMaps.get(airName).vertices.put(destination, new Vertex(destination));	
//    	}
//    	
//    	airlineMaps.get(airName).vertices.get(origin).edges.add(destination);
//    	
////        System.out.println("airline = " + airName);
////        System.out.println("origin = " + origin);
////        System.out.println("destination = " + destination);
//    	Graph test2 = airlineMaps.get("Delta");
//        Set<String> keys = test2.vertices.keySet();
//        for(String key: keys){
//            System.out.println("Value of "+key+" is: "+test2.vertices.get(key).id);
//        }
//    	
//      }
//      System.out.println("-----------------");
//      Graph test2 = airlineMaps.get("Delta");
//      Set<String> keys = test2.vertices.keySet();
//      for(String key: keys){
//          System.out.println("Value of "+key+" is: "+test2.vertices.get(key).id);
//      }
//    }
//    catch(SQLException e)
//    {
//      // if the error message is "out of memory", 
//      // it probably means no database file is found
//      System.err.println(e.getMessage());
//    }
//    finally
//    {
//      try
//      {
//        if(connection != null)
//          connection.close();
//      }
//      catch(SQLException e)
//      {
//        // connection close failed.
//        System.err.println(e);
//      }
//    }
//
////    Graph test2 = airlineMaps.get("Delta");
////    Set<String> keys = test2.vertices.keySet();
////    for(String key: keys){
////        System.out.println("Value of "+key+" is: "+test2.vertices.get(key).id);
////    }
////    
//    
////    Vertex test3		= test2.vertices.get(test);
////    System.out.println(test3);	
////    Iterator<String> it	= test3.edges.iterator();
////    while(it.hasNext()){
////        System.out.println("city = " + it.next());
////     }
////  }
//
//  }
//  static class Vertex {
//		public static String id;
//		public static boolean visited;
//		public static Set<String> edges = new HashSet<String>();
//		public Vertex(String airlineName){
//			id=airlineName;
//			visited=false;
//			
//		}
//	}
//  static class Graph {
//
//		public static String name;
//		public Hashtable<String,Vertex> vertices;
//		public Graph(String airline) {
//			name = airline;
//			vertices = new Hashtable<String,Vertex>(); 
//		}
//		
//
//	}
//}
