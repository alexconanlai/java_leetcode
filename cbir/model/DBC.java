package model;
import java.sql.*;


public class DBC {
	public static Connection getConnect()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");//com.mysql.jdbc.Driver
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/cbirdb","root","xcv");
			return conn;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
