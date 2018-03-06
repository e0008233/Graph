/*
* CSE 132A PA2.java
* Author: Zhang Hao
* Date:   3 Mar 2018
* 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class PA2 {
	public static void main(String[] args) {
		Connection conn = null; // Database connection.

		try {
			// Load the JDBC class.
			Class.forName("org.sqlite.JDBC");

			
			conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
			
			Statement dbStatemrnt = conn.createStatement();

			// Drop all the table that might have been existed before 
		
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Same_Flights;");
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Previous;"); 
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Current;");
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Connected;");

			dbStatemrnt.executeUpdate(
					"CREATE TABLE Current(Airline char(32), Origin char(32), Destination char(32), Stops int);");
			dbStatemrnt.executeUpdate(
					"CREATE TABLE Same_Flights(Airline char(32), Origin char(32), Destination char(32));");
			dbStatemrnt.executeUpdate(
					"CREATE TABLE Previous(Airline char(32), Origin char(32), Destination char(32), Stops int);");

			// Insert tables with appropriate information
			dbStatemrnt.executeUpdate(
					"INSERT INTO Current(Airline, Origin, Destination) SELECT Airline, Origin, Destination FROM Flight;");
			dbStatemrnt.executeUpdate(
					"INSERT INTO Same_Flights(Airline, Origin, Destination) SELECT Airline, Origin, Destination FROM Flight;");

			
			ResultSet resultset = dbStatemrnt.executeQuery("SELECT COUNT(*) AS Size FROM Same_Flights;");
		
			
			int flightNum = resultset.getInt("Size");
			int stopNum   = 0;
			
			while(flightNum > 0){
				if(stopNum > 0){
					dbStatemrnt.executeUpdate("DELETE FROM Previous;");
					dbStatemrnt.executeUpdate("INSERT INTO Previous SELECT * FROM Current;");
					dbStatemrnt.executeUpdate("INSERT INTO Current SELECT s.Airline, s.Origin, f.Destination,"+stopNum+
							" FROM Same_Flights s, Flight f" +
							" WHERE s.Destination = f.Origin AND" +
							" s.Airline = f.Airline AND" +
							" s.Origin <> f.Destination;");
					dbStatemrnt.executeUpdate("DELETE FROM Same_Flights;");
					dbStatemrnt.executeUpdate(
							"INSERT INTO Same_Flights SELECT Airline, Origin, Destination" + 
									" FROM Current" + 
									" EXCEPT SELECT Airline, Origin, Destination" + 
							" FROM Previous;");

				}
				else{
					dbStatemrnt.executeUpdate("INSERT INTO Current SELECT *," + stopNum +" FROM Flight;");
					dbStatemrnt.executeUpdate("DELETE FROM Current WHERE Current.Stops IS NULL;");
				}

				resultset = dbStatemrnt.executeQuery("SELECT COUNT(*) AS Size FROM Same_Flights;");
				flightNum = resultset.getInt("Size");
				stopNum++;
			}

			
			// Create the table Connected
			
			dbStatemrnt.executeUpdate("CREATE TABLE Connected(Airline char(32), Origin char(32), Destination char(32), Stops int);");
			dbStatemrnt.executeUpdate("INSERT INTO Connected" + 
					" SELECT Airline, Origin, Destination, MIN(Stops)" + 
					" FROM Current" +
					" GROUP BY Airline, Origin, Destination" + 
					" ORDER BY Airline;");

			// Drop all the computational tables
		
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Previous;");
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Current;");
			dbStatemrnt.executeUpdate("DROP TABLE IF EXISTS Same_Flights;");

			// Close the ResultSet and Statement objects.
			resultset.close();
			dbStatemrnt.close();
		} catch (Exception e) {
			throw new RuntimeException("There was a runtime problem!", e);
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(
						"Cannot close the connection!", e);
			}
		}
	}
}