package control;

import java.sql.*;
import java.util.*;
import java.text.*;
import model.DBC;

public class UpWgt {
	private double wgt;
	private Connection conn=null;
	private Statement st=null;
	
	
	public void upWgt(String strwgt)
	{
		try
		{
			wgt=Double.parseDouble(strwgt);
			conn=DBC.getConnect();
			st=conn.createStatement();
			st.execute("delete from factor");
			st.execute("insert into factor (dvdfac) values ("+wgt+")");
			st.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public double getWgt()
	{
		double wt=0;
		try
		{
			conn=DBC.getConnect();
			st=conn.createStatement();
			ResultSet rt=st.executeQuery("select dvdfac from factor");
			if(rt.next())
			{
				wt=rt.getDouble("1");
	
			}
			return wt;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
