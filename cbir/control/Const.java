package control;

import java.sql.*;

import model.DBC;

public class Const {
	private static int divide=6;
	private Connection conn=null;
	private Statement st=null;
	
	public int getDivide() {
		return divide;
	}

	public void setDivide() {
		try
		{
			conn=DBC.getConnect();
			st=conn.createStatement();
			ResultSet rt=st.executeQuery("select dvdfac from factor");
			if(rt.next())
			{
				divide=rt.getInt(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
