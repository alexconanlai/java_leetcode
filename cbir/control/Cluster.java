package control;

import java.util.*;

public class Cluster {
	private int id;	//����id
	private double avg[];	//����ƽ��ֵ
	private List ltv=new ArrayList();	//���������
	private int adc=0; 	
	
	public Cluster(int idp)
	{
		id=idp;
	}
	
	 
	
	//ȡ��id
	public int getId()
	{
		return id;
	}
	
	//��������ݿ�
	public void addDB(Cluster c)
	{
		if(c!=null)
		{
			int did=c.getId();
			//String davg
		}
	}
	
	//��������
	public void addVec(List lt)
	{
		if(lt!=null)
		{
			ltv.add(lt.get(0));	//����id
			ltv.add(lt.get(1));	//����ֵ
		}
	}
	
	//ɾ������
	public void delVec()
	{
		ltv=null;
		ltv=new ArrayList();
	}
	
	//ȡ������
	public List getVec()
	{
		return ltv;
	}
	
	//����ƽ��ֵ
	public void setAvg(List lt)
	{
		int r=ltv.size()/2;	//������
		if(r>0&&lt==null)
		{
			
			//String c[]=(String[])ltv.get(1);	//�������ݴ�С
			int vid=0;	//����id
			double sum=0;	//һ�������ĺ�
			double arrv[][]=null;
			String arrvr[]=(String[])ltv.get(1); 	//�������ݴ�С
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
			//����ƽ��ֵ 
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
	
	//ȡ��ƽ��ֵ 
	public double[] getAvg()
	{
		return avg;
	}
}
