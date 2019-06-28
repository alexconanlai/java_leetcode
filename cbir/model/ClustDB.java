package model;

import java.sql.*;
import java.util.*;
import java.text.*;
import model.DBC;
import control.Cluster;

public class ClustDB {
	private List lto=new ArrayList();	//����һ������
	private List lta=new ArrayList();	//����ȫ������
	private int clct=2,l=0;		//�������
	private	Cluster clu[];	//����
	
	//��������
	public void clustData()
	{
		double cdt[]=null;
		String arrt[]=null;		//������������
		try
		{
			Connection conn=DBC.getConnect();
			Statement st=conn.createStatement();
			ResultSet rt=st.executeQuery("select id,colorvec,shapevec,clusterid from picture");
			int ct=0;
			DecimalFormat df=new DecimalFormat("0.000000");
			while(rt.next())
			{
				lto.add(rt.getInt("id"));
				arrt=(rt.getString("colorvec")).split(",");
				
				/*l=arrt.length;
				
				for(int co=0;co<l;co++)
				{
					if(co<((1*l/9))||((2*l/9)<=co&&co<(l/3))||((2*l/3)<=co&&co<(7*l/9))||((8*l/9)<=co&&co<l))
					{
						arrt[co]=df.format(Double.parseDouble(arrt[co])/10);//ltc.set(co,d.format((Double.parseDouble(ltc.get(co).toString()))/10));
					}
					else if(((1*l/9)<=co&&co<(2*l/9))||((l/3)<=co&&co<(4*l/9))||((5*l/9)<=co&&co<(2*l/3))||((7*l/9)<=co&&co<(8*l/9)))
					{
						arrt[co]=df.format(2*Double.parseDouble(arrt[co])/5);
					}
					else
					{
						arrt[co]=df.format(Double.parseDouble(arrt[co])/2);
					}
				}*/
				lto.add(arrt);
				lto.add(rt.getInt("clusterid"));
				lta.add(lto);
				lto=null;
				lto=new ArrayList();
				ct++;
			}double max=0,r=0,vin=Integer.MAX_VALUE;
			double vd[]=null,vav[][]=null;	//һ�־���:vd���������ھ��룬vav�����������
			int clth=(int)Math.sqrt(ct),clct=0;
			double arrcn[]=new double[clth+1];	//ÿ�ξ���ľ���
			cdt=new double[clth+1];
			/*for(int c=clth;c<=clth;c++) 	//ȫ������
			{
				setClust(c,lta,lta.size());
				vd=new double[c+1];
				for(int d=0;d<c;d++)
				{
					vd[d]=getLth(clu[d]);
				}
				vav=new double[c][c];//[c*(c-1)/2];
				for(int a=0;a<c-1;a++)
				{
					for(int b=a;b<c;b++)
					{
						vav[a][b]=getClt(clu[a],clu[b]);	//��֮��
					}
				}
				
				 r=0;
				for(int a=0;a<c;a++)
				{
					max=0;
					for(int b=0;b<c;b++)
					{
						if(a!=b)
						{
							if(a>b)
							{
								if(max<(vd[a]+vd[b])/vav[b][a])
									max=(vd[a]+vd[b])/vav[b][a];
							}
							else
							{
								if(max<(vd[a]+vd[b])/vav[a][b])
									max=(vd[a]+vd[b])/vav[a][b] ;
							}
						}
					}
					r+=max;//r�ĺ�
				}			
				if(vin>r/c)
				{
					vin=r/c;clct=c;
				}
				arrcn[c]=r/c;System.out.println(r/c);
				vd=null;
				vav=null;}*/
			//clct=getMin(arrcn);
			//��������ݽ���36
			setClust(13,lta,lta.size());
			//st.execute("delete from cluster");
			//st.close();
			conn.close();
			for(int c=0;c<13;c++)//�޸ľ�������
			{
				svDB(clu[c],conn,st);
			}//System.out.println(vin);
			st.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//���о���
	public void setClust(int c,List lt,int rad)
	{
		clu=new Cluster[c];
		int l=lt.size();
		String cic[]=new String[l];	//�ϴ���������id����
		int t=0;
		int ct=0;
		int rd=0;
		/*int b[]=new int[rad];
		while(ct<c)
		{
			rd=(new Random()).nextInt(rad-1);
			if(b[rd]==0)
			{
				b[rd]=rd;
				ct++;
			}
		}ct=0;
		int ita[] = new int[c];
		for(int i=0;i<rad&&ct<c;i++)
		{
			if(b[i]!=0)
			{
				ita[ct]=i;
				ct++;
			}
		}*/
		for(int i=0;i<c;i++)
		{
			//clu[i].addVec((ArrayList)lt.get(i));	
			clu[i]=new Cluster(i);
			clu[i].setAvg((ArrayList)lt.get(i));//clu[i].setAvg((ArrayList)lt.get(ita[i]));	//�����ֵ
		}
		clust(c,lt,clu);	//������
		for(;;)
		{
			//ltcp=(ArrayList)((ArrayList)lt).clone();
			//int lc=ltcp.size();
			//cic=new String[l];
			for(int i=0;i<l;i++)	//�ϴξ���id
			{
				cic[i]=((ArrayList)lt.get(i)).get(2).toString();
			}
			for(int i=0;i<c;i++)	//��ξ���
			{
				clu[i].setAvg(null);	//��ξ���ƽ��ֵ
				clu[i].delVec();
			}
			clust(c,lt,clu);
			//ltcp=(ArrayList)((ArrayList)lt).clone();
			a:for(t=0;t<l;t++)
			{
				if(!(cic[t].equals(((ArrayList)lt.get(t)).get(2).toString())))
				{
					break a;
				}
			}
			if(t==l)	//�������
			{
			
				break;
			}
			/*for(int i=0;i<c;i++)	//�´ξ���
			{
				clu[i].setAvg(null);	//��ξ���ƽ��ֵ
				clu[i].delVec();
			}		*/
		}		
	}
	
	//һ�ξ���
	public void clust(int c,List lt,Cluster clr[])
	{
		double dcl[]=new double[c];	//����ľ��� 
		int l=lt.size();	//������
		List ltcl=new ArrayList();	//�����������	
		
		for(int i=0;i<l;i++)
		{
			List ltp=(ArrayList)lt.get(i);
			for(int j=0;j<c;j++)
			{
				dcl[j]=getDit(ltp,clr[j]);
			}
			int ind=getMin(dcl);
			ltp.set(2, ind);
			lt.set(i, ltp);		//�޸���������ֵ
			clr[ind].addVec(ltp);	//��������
		}
	}
	//����������ݺ�ͼ��ľ���id
	public void svDB(Cluster clt,Connection conn,Statement st)
	{
		String cv="";//��ɫ����
		try
		{
			//Connection conn=DBC.getConnect();
			//Statement st=conn.createStatement();
			ResultSet rt;
			double d[]=clt.getAvg();
			for(int c=0;c<d.length;c++)
			{
				DecimalFormat df=new DecimalFormat("0.000000");
				cv+=df.format(d[c])+",";
			}
			List lt=clt.getVec();
			int l=lt.size();
			for(int c=0;c<l/2;c++)
			{	
				st.executeUpdate("update picture set clusterid="+clt.getId()+" where id="+lt.get(c*2));
			}
			//st.executeQuery("delete from cluster");
			//if(!rt.next())
			//{
				st.execute("insert into cluster (avg,clusterid,num) values ('"+cv+"',"+clt.getId()+","+((List)clt.getVec()).size()/2+")");
			//}
			//else
			{
				//st.executeUpdate("update cluster set avg='"+cv+"',num="+((List)clt.getVec()).size()/2+" where clusterid="+clt.getId());
			}
			
			//st.close();
			//conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//ȡ�þ���id�������ľ���
	public List getDt(List lt)
	{
		if(lt!=null)
		{
			int r=lt.size();
			int c=((String[])((ArrayList)lt.get(0)).get(1)).length;
			double d[]=new double[3];
			double sum=0;
			List ltvd=new ArrayList();
			String strv[][]=new String[r][c];
			double darr[][]=new double[r][c];
			
			for(int k=0;k<r;k++)
			{
				strv[k]=((String[])((ArrayList)lt.get(k)).get(1));
			}
			
			for(int k=0;k<r;k++)	//ת����Ϊ����
			{
				for(int o=0;o<c;o++)
				{
					darr[k][o]=Double.parseDouble(strv[k][o]);
				}
			}
			//for(int i=0;i<r-1;i++)
			//{
				for(int j=1;j<r;j++)
				{
					for(int k=0;k<c;k++)
					{
						sum+=Math.pow( darr[0][k]-darr[j][k], 2);
					}
					d[0]=Double.parseDouble(((ArrayList)lt.get(j)).get(0).toString());
					d[1]=sum;//Double.parseDouble(((ArrayList)lt.get(j)).get(0).toString());
					//d[i*(r-j+1)+2][2]=sum;
					ltvd.add(d);					
				}
			//}
			return ltvd;
		}
		else
			return null;
	}
	
	//ȡ��min����
	public int getMin(double d[])
	{
		if(d!=null)
		{
			int ind=0;
			double min=d[0];
			for(int c=0;c<d.length;c++)
			{
				if(min>d[c])
				{
					min=d[c];
					ind=c;
				}
			}
			return ind;
		}
		return -1;
	}
	//ȡ�þ����ھ����
	public double getLth(Cluster clt )
	{
		if(clt!=null)
		{
			List lt=clt.getVec();
			int r=lt.size()/2;
			int c=((String[])lt.get(1)).length;
			double sum=0;
			double darr[][]=new double[r][c];
			double davg[]=clt.getAvg();
			String strv[][]=new String[r][c];
			String tarr[]=(String[])lt.get(1);//��������
			for(int k=0;k<r;k++)
			{
				strv[k]=(String[])lt.get((k*2)+1);
			}
			for(int k=0;k<r;k++)	//ת����Ϊ����
			{
				for(int o=0;o<tarr.length;o++)
				{
					darr[k][o]=Double.parseDouble(strv[k][o]);
				}
			}
			double sumt=0;
			for(int k=0;k<r;k++)	//�������� 
			{
				//for(int o=k+1;o<r;o++)
				//{
				sumt=0;	
				for(int ct=0;ct<c;ct++)//
					{
						/*if(Double.parseDouble(lt.get(k*2).toString())==d[ct][0]&&Double.parseDouble(lt.get(o*2).toString())==d[ct+1][1])
						{
							sum+=d[ct+2][2];
						}*/
						sumt+=Math.pow(darr[k][ct]-davg[ct], 2);
					}
					sum+=Math.sqrt(sumt);
				//}
			}
			return sum/(((List)clt.getVec()).size()/2);
		}
		return -1;
	}
	//������֮��ľ���
	public double getClt(Cluster ca,Cluster cb)
	{
		if(ca!=null&&cb!=null)
		{
			double da[]=ca.getAvg();
			double db[]=cb.getAvg();
			double sum=0;
			for(int a=0;a<da.length;a++)
			{
				sum+=Math.pow((da[a]-db[a]), 2);
			}
			return	Math.sqrt(sum); 
		}
		else
			return 0;
	}
	//������ƽ��ֵ�ľ���
	public double getDit(List lta,Cluster c)
	{
		if(lta!=null&&c!=null)
		{
			double d=0;	//����
			double avg[]=c.getAvg();
			int cl=avg.length;
			String[] strt;	//��������
			strt=(String[])lta.get(1);
			for(int co=0;co<cl;co++)	
			{
				d += Math.pow(Double.parseDouble(strt[co])-avg[co], 2);	//�����
			}
			return d;
		}
		return 0;
	}
}


