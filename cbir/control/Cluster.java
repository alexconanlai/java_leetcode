package control;

import java.util.*;

public class Cluster {
	private int id;	//聚类id
	private double avg[];	//向量平均值
	private List ltv=new ArrayList();	//聚类的向量
	private int adc=0; 	
	
	public Cluster(int idp)
	{
		id=idp;
	}
	
	 
	
	//取得id
	public int getId()
	{
		return id;
	}
	
	//增添进数据库
	public void addDB(Cluster c)
	{
		if(c!=null)
		{
			int did=c.getId();
			//String davg
		}
	}
	
	//增添向量
	public void addVec(List lt)
	{
		if(lt!=null)
		{
			ltv.add(lt.get(0));	//向量id
			ltv.add(lt.get(1));	//向量值
		}
	}
	
	//删除向量
	public void delVec()
	{
		ltv=null;
		ltv=new ArrayList();
	}
	
	//取得向量
	public List getVec()
	{
		return ltv;
	}
	
	//计算平均值
	public void setAvg(List lt)
	{
		int r=ltv.size()/2;	//向量数
		if(r>0&&lt==null)
		{
			
			//String c[]=(String[])ltv.get(1);	//向量数据大小
			int vid=0;	//向量id
			double sum=0;	//一行向量的和
			double arrv[][]=null;
			String arrvr[]=(String[])ltv.get(1); 	//向量数据大小
			arrv=new double[r][arrvr.length];
			for(int i=0;i<r;i++)			
			{
				//vid=Integer.parseInt(((ArrayList)lt.get(i)).get(0).toString());
				//ltv.add(vid);
			//	if(i<r-1)
			//	{
				arrvr=(String[])ltv.get((i*2)+1);
				for(int j=0;j<arrvr.length;j++)
				{
					arrv[i][j]=Double.parseDouble(arrvr[j]);
				}
			//	}
			//	else
			//	{
			//		arrvr=lt.get(1).toString().split(",");
			//		for(int j=0;j<c;j++)
			//		{
			//			arrv[i][j]=Double.parseDouble(arrvr[j]);
			//		}
			//	}
			}
			avg=new double[arrvr.length];
			//计算平均值 
			for(int j=0;j<arrvr.length;j++)
			{
				for(int i=0;i<r;i++)
				{
					sum+=arrv[i][j];
				}
				avg[j]=sum/r;
				sum=0;
			}
		}
		else if(r==0&&lt!=null)
		{
			String arrlt[]=(String[])lt.get(1);
			avg=new double[arrlt.length];
			for(int i=0;i<arrlt.length;i++)
			{
				avg[i]=Double.parseDouble(arrlt[i]);//System.out.print(i+"\n");
			}
		}

	}
	
	//取得平均值 
	public double[] getAvg()
	{
		return avg;
	}
}
