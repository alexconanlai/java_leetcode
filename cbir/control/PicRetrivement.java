package control;

import java.sql.*;
import java.util.*;
import java.text.*;

import model.DBC;

public class PicRetrivement {
	//private LinkedList ltcd=null;	//����ͼ����ɫ����
	//private LinkedList ltsd=null;	//����ͼ����״����
	//private LinkedList ltd=null;	//����ͼ�����
	private List lt=null;				//�����޸�lt	
	private String path="";			
	private int clid=0;		
	float wa,wb;
	//�Ƚ���ɫ����״���� b=0-����δ����������b=1-��������������
	public List getCorShpSimPic(List ltc,String par,int b)
	{
		lt=new ArrayList();				//�����޸�lt
		int l=ltc.size();
		for(int i=0;i<l;i++)
		{
			lt.add(i, ltc.get(i));
		}
		List lta=new ArrayList();	//ȫ������
		List ltp=new ArrayList();	//һ������
		double d=0;               	//��������
		int rcot=0;					//��¼��
		List ltdt=new ArrayList();	//�淵������
		//double sd=0;
		if(lt!=null&&(par!=null||!par.equals("")))
		{
			try
			{
				Connection conn=DBC.getConnect();
				Statement st=conn.createStatement();
				ResultSet rt=st.executeQuery("select id,path,"+par+",simlar,lastvec from picture");	//��ѯ��������
				while(rt.next())
				{
					//d=getDt(lt,rt.getString(par));
					path=rt.getString("path");
					ltp.add(rt.getInt("id"));
					ltp.add(path);
					ltp.add(rt.getString(par));
					ltp.add(rt.getInt("simlar"));
					lta.add(ltp);
					ltp=null;
					ltp=new ArrayList();
					rcot++;
				}
				//�ڲ���һ��
				insideToUnify(lt,lta,rcot);
				for(int co=0;co<rcot;co++)
				{
					d=getDt(lt,(ArrayList)((ArrayList)lta.get(co)).get(2));
					//path=rt.getString("path");
					//ltp.add(rt.getInt("id"));
					((ArrayList)lta.get(co)).set(2, d);	
				}
				if(b==1)
				{
					getPicDt(lta);
					for(int i=0;i<20;i++)
					{
						ltp.add(((ArrayList)lta.get(i)).get(0));
						ltp.add(((ArrayList)lta.get(i)).get(1));
						ltp.add(((ArrayList)lta.get(i)).get(2));
						ltp.add(((ArrayList)lta.get(i)).get(3));
						ltdt.add(ltp);
						ltp=null;
						ltp=new ArrayList();
					}
					clid=Integer.parseInt(((ArrayList)lta.get(0)).get(0).toString());
					lta=null;
					lta=ltdt;
					ltp=null;
					lt=null;
				}
				st.close();
				conn.close();
				ltp=null;
				lt=null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
	    	}
		}
		//lt=lttpc;
		return lta;
	}
	
	//�Ƚ��ۺ�����
	public List getSimPic(List ltcv,List ltsv,float w1,float w2)
	{
		List ltc=new ArrayList();   //��ɫ����
		List lts=new ArrayList();	//��״����
		List ltdt=new ArrayList();	//��������
		List lta=new ArrayList();	//ȫ������
		List ltp=new ArrayList();	//һ������
		double cd=0;				//��ɫ����
		double sd=0;				//��״����
		if(ltcv!=null&&ltsv!=null)
		{
			ltc=getCorShpSimPic(ltcv,"colorvec",0);
			lts=getCorShpSimPic(ltsv,"shapevec",0);
			int lc=ltc.size();
			int ls=lts.size();
			//System.out.print(lc+" "+ls);
			//�ⲿ��һ��
			outsideToUnify(ltc);
			outsideToUnify(lts);
			for(int ct=0;ct<lc&&ct<ls;ct++)
			{
				if(((ArrayList)ltc.get(ct)).get(0).toString().equals(((ArrayList)lts.get(ct)).get(0).toString()))//Ϊ��ͬͼ������
				{
					ltp.add(((ArrayList)ltc.get(ct)).get(0));
					ltp.add(((ArrayList)ltc.get(ct)).get(1));
					ltp.add(w1*Double.parseDouble(((ArrayList)ltc.get(ct)).get(2).toString())+w2*Double.parseDouble(((ArrayList)lts.get(ct)).get(2).toString()));
					ltp.add(((ArrayList)ltc.get(ct)).get(3));
					lta.add(ltp);
					ltp=null;
					ltp=new ArrayList();
				}
			}/*try 
			{
				Connection conn=DBC.getConnect();
				Statement st = conn.createStatement();
				ResultSet rt = st.executeQuery("select id,path,colorvec,shapevec from picture"); // ��ѯ��������
				while (rt.next()) {
					cd = getDt(ltcv, rt.getString("colorvec"));
					sd = getDt(ltsv, rt.getString("shapevec"));
					path = rt.getString("path");
					ltp.add(rt.getInt("id"));
					ltp.add(path);
					ltp.add();
					lta.add(ltp);
					ltp = null;
					ltp = new ArrayList();
				}*/
				getPicDt(lta);
				int l=lta.size();
				for (int i = 0; i < 20&&i < l; i++) {
					ltp.add(((ArrayList)lta.get(i)).get(0));//id
					ltp.add(((ArrayList)lta.get(i)).get(1));//path
					ltp.add(((ArrayList)lta.get(i)).get(2));//lgt
					ltp.add(((ArrayList)lta.get(i)).get(3));//sim
					ltdt.add(ltp);
					ltp=null;
					ltp=new ArrayList();
				}
				clid=Integer.parseInt(((ArrayList)lta.get(0)).get(0).toString());
				ltc=null;
				lts=null;ltp=null;
				//st.close();
				//conn.close();
		//	}
			/*catch (Exception e)
			{
				e.printStackTrace();
			}*/
		}
		return ltdt;
	}
	
//	�Ĳ�ѯ�������ۺ����� 
	public List[] chageVec(double w[],double wv[],List ltr,List ltcsv,int csv,String par)
	{
		if(ltcsv!=null&&ltr!=null)
		{
			int l=ltcsv.size();
			String avec[]=null;
			double n[]=new double[l],y[]=new double[l];
			List ltre[]=new List[2]; 
			try
			{
				String tpv="";
				Connection conn=DBC.getConnect();
				Statement st=conn.createStatement();
				ResultSet rt;
				for(int d=0;d<20;d++)
				{
					if(Integer.parseInt(((ArrayList)ltr.get(d)).get(3).toString())==-1)
					{
						if(csv==1)
						{
							rt=st.executeQuery("select "+par+" from picture where id="+Integer.parseInt(((ArrayList)ltr.get(d)).get(0).toString()));
							if(rt.next())
							{
								tpv=rt.getString(par);
								avec=tpv.split(",");
							}
						}
						else
						{
							rt=st.executeQuery("select colorvec,shapevec from picture where id="+Integer.parseInt(((ArrayList)ltr.get(d)).get(0).toString()));
							if(rt.next())
							{
								tpv=rt.getString("colorvec")+rt.getString("shapevec");
								avec=tpv.split(",");
							}
						}
						for(int h=0;h<l&&avec!=null;h++)
						{
							n[h]+=Double.parseDouble(avec[h]);
						}
					}
					else if(Integer.parseInt(((ArrayList)ltr.get(d)).get(3).toString())==1)
					{
						if(csv==1)
						{
							rt=st.executeQuery("select "+par+" from picture where id="+Integer.parseInt(((ArrayList)ltr.get(d)).get(0).toString()));
							if(rt.next())
							{
								tpv=rt.getString(par);
								avec=tpv.split(",");
							}
						}
						else
						{
							rt=st.executeQuery("select colorvec,shapevec from picture where id="+Integer.parseInt(((ArrayList)ltr.get(d)).get(0).toString()));
							if(rt.next())
							{
								tpv=rt.getString("colorvec")+rt.getString("shapevec");
								avec=tpv.split(",");
							}
						}
						for(int h=0;h<l&&avec!=null;h++)
						{
							y[h]+=Double.parseDouble(avec[h]);
						}
					}
					avec=null;
				}
				for(int h=0;h<l;h++)//
				{
					y[h]=y[h]/20;
					n[h]=n[h]/20;
				}
	
				for(int ct=0;ct<l;ct++)
				{
					ltcsv.set(ct, Double.parseDouble(ltcsv.get(ct).toString())+wv[1]*y[ct]-wv[0]*n[ct]);
				}
				List lvt;
				if(csv==1)	//��������
				{
					lvt=getCorShpSimPic(ltcsv,par,csv);
				}
				else 	//�ۺϼ���
				{
					List ltc=new ArrayList();
					List lts=new ArrayList();
					for(int ct=0;ct<l;ct++)
					{
						if(ct<63*9)
							ltc.add(ltcsv.get(ct));
						else
							lts.add(ltcsv.get(ct-63*9));
					}
					wa=(float)w[0];
					wb=(float)w[1];
					lvt=getSimPic(ltc,lts,wa,wb);
				}
				st.close();
				conn.close();
				ltre[0]=lvt;	// ���ؽ������
				ltre[1]=ltcsv;	// �޸ĵĲ�ѯ����
				return ltre;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	//ȡ�þ���
	public double getDt(List ltcv,List ltdcv)
	{
		if(ltcv!=null&&ltdcv!=null)
		{
			int l=ltdcv.size();
			//Double arrdc[]=new Double[l];	//ͼ�����������
			double td=0;	//�ܾ���
			double tmp=0;	
			//for(int i=0;i<l;i++)
			//{
				//arrdc[i]=Double.parseDouble(ltdcv.get(i).toString());
			//}			
			//����롣
			for(int i=0;i<l;i++)
			{
				tmp=Double.parseDouble(ltcv.get(i).toString())-Double.parseDouble(ltdcv.get(i).toString());
				td=td+Math.pow(tmp, 2);System.out.print(i+ "\n");
			}
			td=Math.sqrt(td);
			return td;
		}
		else
			return 0;
	}
	
	//�ڲ���һ��
	public void insideToUnify(List ltc,List ltdb,int cot)
	{
		if(ltc!=null&&ltdb!=null)
		{
			//List lttp=ltdb;
			List ltv=new ArrayList();	
			DecimalFormat d=new DecimalFormat("0.000000");
			int l=ltc.size();
			for(int co=0;co<l;co++)
			{
			//	if(co<((1*l/9))||((2*l/9)<=co&&co<(l/3))||((2*l/3)<=co&&co<(7*l/9))||((8*l/9)<=co&&co<l))
				{
					ltc.set(co,d.format((Double.parseDouble(ltc.get(co).toString()))));
				}
			//	else if(((1*l/9)<=co&&co<(2*l/9))||((l/3)<=co&&co<(4*l/9))||((5*l/9)<=co&&co<(2*l/3))||((7*l/9)<=co&&co<(8*l/9)))
				{
			//		ltc.set(co,d.format(Double.parseDouble(ltc.get(co).toString())/3));
				}
			//	else
				{
			//		ltc.set(co,d.format(Double.parseDouble(ltc.get(co).toString())/2));
				}
			}
			String arrtpr[]=null;				//����������
			//List ltcol=new ArrayList();
			//String  ltd="";				//���޸ĵ�����
			double col[][]=new double[cot][l];	//������
			double avg=0;					//ƽ��ֵ
			double formalos=0;				//��׼��
			double tp=0;					//ltv������
			double sum=0;					//��
			for(int i=0;i<cot;i++)			//����
			{
				arrtpr=((ArrayList)ltdb.get(i)).get(2).toString().split(",");
				for(int j=0;j<l;j++)		//����
				{
					//if(j<((1*l/9))||((2*l/9)<=j&&j<(l/3))||((2*l/3)<=j&&j<(7*l/9))||((8*l/9)<=j&&j<l))
					{
						col[i][j]=Double.parseDouble(d.format(Double.parseDouble(arrtpr[j])));
					}
				//	else if((((1*l/9))<=j&&j<((2*l/9)))||(((l/3))<=j&&j<((4*l/9)))||((5*l/9)<=j&&j<(2*l)/3)||((7*l/9)<=j&&j<(8*l/9)))
				//	{
				//		col[i][j]=Double.parseDouble(d.format(Double.parseDouble(arrtpr[j])/3));
				//	}
				//	else
					{
				//		col[i][j]=Double.parseDouble(d.format(Double.parseDouble(arrtpr[j])/2));
					}
				System.out.print(i+" "+j+"\n");
				}
				arrtpr=null;
			}
			//arrtpr=null;
			for(int i=0;i<l;i++)			//����
			{
				sum=Double.parseDouble(ltc.get(i).toString());
				tp=sum;
				for(int j=0;j<cot;j++)		//����
				{
					sum=sum+col[j][i];
				}
				avg=sum/(cot+1);
				sum=Math.pow(Double.parseDouble(ltc.get(i).toString())-avg, 2);
				for(int k=0;k<cot;k++)
				{
					sum=sum+Math.pow(col[k][i]-avg, 2);
				}
				formalos=Math.sqrt(sum/cot);
				if(formalos==0)
					ltc.set(i, 0);
				else
					ltc.set(i, (tp-avg)/formalos);
				for(int c=0;c<cot;c++)
				{
					if(formalos==0)
					{
						col[c][i]=0;
						continue;
					}
					col[c][i]=(col[c][i]-avg)/formalos;
				}
			}
			for(int i=0;i<cot+1;i++)			//����
			{//��Ȩֵ
				if(i==0)
				{
					for(int co=0;co<l;co++)
					{
						if(co<((1*l/9))||((2*l/9)<=co&&co<(l/3))||((2*l/3)<=co&&co<(7*l/9))||((8*l/9)<=co&&co<l))
						{
							ltc.set(co,d.format((Double.parseDouble(ltc.get(co).toString()))/10));
						}
						else if(((1*l/9)<=co&&co<(2*l/9))||((l/3)<=co&&co<(4*l/9))||((5*l/9)<=co&&co<(2*l/3))||((7*l/9)<=co&&co<(8*l/9)))
						{
							ltc.set(co,d.format(2*Double.parseDouble(ltc.get(co).toString())/5));
						}
						else
						{
							ltc.set(co,d.format(Double.parseDouble(ltc.get(co).toString())/2));
						}
					}
				}
				else
				{
					for(int j=0;j<l;j++)		//����
					{
						if(j<((1*l/9))||((2*l/9)<=j&&j<(l/3))||((2*l/3)<=j&&j<(7*l/9))||((8*l/9)<=j&&j<l))
						{
							col[i-1][j]=col[i-1][j]/10;
						}
						else if((((1*l/9))<=j&&j<((2*l/9)))||(((l/3))<=j&&j<((4*l/9)))||((5*l/9)<=j&&j<(2*l)/3)||((7*l/9)<=j&&j<(8*l/9)))
						{
							col[i-1][j]=2*col[i-1][j]/5;
						}
						else
						{
							col[i-1][j]=col[i-1][j]/2;
						}
					}
				}
			}
			for(int i=0;i<cot;i++)
			{
				for(int c=0;c<l;c++)
				{
					ltv.add(col[i][c]);
				}//System.out.print(i+"\n");
				((ArrayList)ltdb.get(i)).set(2,ltv);
				ltv=null;//ltd="";//ltd.delete(0, ltd.length());
				ltv=new ArrayList();//ltd=new StringBuffer("");
			}
			col=null;
			d=null;
			//ltdb=lttp;
		}
	}
	
	//�ⲿ��һ��
	public void outsideToUnify(List ltdb)
	{
		if(ltdb!=null)
		{
			int l=ltdb.size();
			String arrtpr[]=null;				//����������
			String ltd="";					//���޸ĵ�����
			double col[]=new double[l];	//������
			double avg=0;					//ƽ��ֵ
			double formalos=0;				//��׼��
			double tp=0;					//ltdb������
			double sum=Double.parseDouble(((ArrayList)ltdb.get(0)).get(2).toString());		//��
			col[0]=sum;
			for(int i=0;i<l-1;i++)			//����
			{
				col[i+1]=Double.parseDouble(((ArrayList)ltdb.get(i+1)).get(2).toString());
				sum=sum+col[i+1];
			}
			avg=sum/l;
			sum=Math.pow(Double.parseDouble(((ArrayList)ltdb.get(0)).get(2).toString())-avg, 2);
			for(int k=0;k<l-1;k++)
			{
				sum=sum+Math.pow(Double.parseDouble(((ArrayList)ltdb.get(k+1)).get(2).toString())-avg, 2);
			}
			formalos=Math.sqrt(sum/(l-1));
			for(int c=0;c<l;c++)
			{
				if(formalos==0)
					((ArrayList)ltdb.get(c)).set(2, 0);
				else
					((ArrayList)ltdb.get(c)).set(2, (col[c]-avg)/formalos);
			}
		}
	}
	
//	��������
	public void svDB(int c)
	{
		//if(lt!=null)
		//{
			/*String vlt="";
			int l=lt.size();
			for(int ct=0;ct<l;ct++)
			{
				vlt+=lt.get(ct).toString()+",";
			}*/
			try
			{
				Connection conn=DBC.getConnect();
				Statement st=conn.createStatement();
				//st.executeUpdate("update picture set lastvec='"+vlt+",csv="+c+" where id="+clid);
				st.executeUpdate("update picture set simlar=0");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		//}
	}
	//����ͼ�����
	public void getPicDt(List lt)
	{
		if(lt!=null)
		{
			 qksort(lt,0,lt.size()-1);
		}
		//return lt;
	}
	
	//��������
	public void qksort(List lt,int b,int c)
	{
		int k=0;
		if(lt!=null&&b<c)
		{
			k=qkpass(lt,b,c);
			qksort(lt,b,k-1);
			qksort(lt,k+1,c);
		}
	}
	
	//ȡ������
	public int qkpass(List lt,int f,int l)
	{
		int i=f,j=l;
		double k=Double.parseDouble(((ArrayList)lt.get(f)).get(2).toString());
		ArrayList lttp=(ArrayList)lt.get(f);
		while(i<j)
		{
			while(i<j&&Double.parseDouble(((ArrayList)lt.get(j)).get(2).toString())>=k)
			{
				j--;
			}
			if(i!=j)
			{
				lt.set(i, (ArrayList)lt.get(j));
			}
			while(i<j&&Double.parseDouble(((ArrayList)lt.get(i)).get(2).toString())<=k)
			{
				i++;
			}
			if(i!=j)
			{
				lt.set(j, (ArrayList)lt.get(i));
			}
		}
		lt.set(i, lttp);
		return i;
	}
}
