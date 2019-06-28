package control;
import javax.media.jai.*;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.*;
import java.text.DecimalFormat;
import java.util.*;

public class ShapeVector implements Runnable{
	private int divide=(new Const()).getDivide();
	private LinkedList ltsv=new LinkedList();
	private String f;
	
	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public LinkedList getLt() {
		return ltsv;
	}

	public void setLt(LinkedList lt) {
		this.ltsv = lt;
	}

	public void run()
	{
		getPic();
	}
	
	//返回分块的矩阵， xpto：x坐标，ypto：y坐标，row：行数，col：列数，pix2：一张图全部的像素
	public String[]  getHumoment(int pix2[][],int xpto,int ypto,int row,int col)
	{
    	int pix[][]=new int[row][col];//部分像素
		for(int r=0;r<row;r++)
		{
			for(int c=0;c<col;c++)
			{
				pix[r][c]=pix2[xpto+r][ypto+c];
			}
		}
		/*for(int r=0;r<row;r++)
		{
			for(int c=0;c<col;c++)
			{
				System.out.print(pix[r][c]+" ");
			}System.out.println();
		}*/
		String humt[]={"0.000000","0.000000","0.000000","0.000000","0.000000","0.000000","0.000000"};
		if(getMoment(pix,0,0)==0)
			return humt;
		double hcenter=getMoment(pix,1,0)/getMoment(pix,0,0);
		double wcenter=getMoment(pix,0,1)/getMoment(pix,0,0);
		double mt02=getnormal(pix,0,2,hcenter,wcenter); 
		double mt20=getnormal(pix,2,0,hcenter,wcenter);
		double mt11=getnormal(pix,1,1,hcenter,wcenter);
		double mt12=getnormal(pix,1,2,hcenter,wcenter);
		double mt21=getnormal(pix,2,1,hcenter,wcenter);
		double mt03=getnormal(pix,0,3,hcenter,wcenter);
		double mt30=getnormal(pix,3,0,hcenter,wcenter);
		DecimalFormat df=new DecimalFormat("0.000000");
		humt[0]=df.format(mt20+mt02);
		humt[1]=df.format(Math.pow((mt20-mt02),2)+4*Math.pow(mt11,2));
		humt[2]=df.format(Math.pow((mt30-3*mt12),2)+Math.pow((mt03-3*mt21),2));
		humt[3]=df.format(Math.pow((mt30+mt12),2)+Math.pow((mt03+mt21),2));
		humt[4]=df.format((mt30-3*mt12)*(mt30+mt12)*(Math.pow((mt30+mt12),2)-3*Math.pow((mt03+mt21),2))+((3*mt21-mt03))*((mt03+mt21))*
				(3*Math.pow((mt30+mt12),2)-(Math.pow((mt03+mt21),2))));
		humt[5]=df.format((mt20-mt02)*(Math.pow((mt30+mt12),2)-Math.pow((mt03+mt21),2))+4* mt11*((mt30+mt12)*(mt03+mt21)));
		humt[6]=df.format((3*mt21-mt03)*(mt30+mt12)*(Math.pow((mt30+mt12),2)-3*Math.pow((mt03+mt21),2))+((3*mt12-mt30))*((mt03+mt21))*
				(3*Math.pow((mt30+mt12),2)-(Math.pow((mt03+mt21),2))));
		for(int c=0;c<7;c++)
		{
			System.out.println(humt[c]);
		}
		return humt;		
	}

	public double getMoment(int pix[][],int rp,int rq)
	{
		double moment=0.0;
		for(int h=0;h<pix.length;h++)
		{
			for(int w=0;w<pix[0].length;w++)
			{
					moment=Math.pow(h, rp)*Math.pow(w, rq)*pix[h][w]+moment;					
			}
		}
		return moment;
	}
	
	public double getCenterMoment(int pix[][],int rp,int rq,double hcenter,double wcenter)
	{
			
		double centermoment=0.0;
		for(int h=0;h<pix.length;h++)
		{
			for(int w=0;w<pix[0].length;w++)
			{
				centermoment=Math.pow(h-hcenter,rp)*Math.pow(w-wcenter,rq)*pix[h][w]+centermoment;
			}
		}
		return centermoment;
	}
	
	public double getnormal(int pix[][],int rp,int rq,double hcenter,double wcenter)
	{
		double mt00=this.getCenterMoment(pix,0,0,hcenter,wcenter);
		return this.getCenterMoment(pix, rp, rq,hcenter,wcenter)/Math.pow(mt00,(rp+rq+2)/2);
	}
	
	//产生二值图和不变矩 
	public LinkedList getPic()
	{
		RenderedOp fi=JAI.create("fileload", f);
		IHSColorSpace hc=IHSColorSpace.getInstance();
		ColorModel ihscolor=new ComponentColorModel(hc,new int[]{8,8,8},false,false,java.awt.Transparency.OPAQUE,DataBuffer.TYPE_BYTE);ParameterBlock pb =
		     (new ParameterBlock()).addSource(fi).add(ihscolor);
		RenderedOp node =  JAI.create("ColorConvert", pb);
		int width=node.getWidth();
		int height=node.getHeight();
		final double tot=width*height;
	    SampleModel sm=node.getSampleModel();
	    int nbands=sm.getNumBands();
	    Raster inputRaster=node.getData();
	  
		int []pixels= new int[(int)(nbands*tot)];
		int pix[]=new int[(int)tot];inputRaster.getPixels(0,0,width,height,pixels);
		for(int h=0;h<height;h++)
		{
			for(int w=0;w<width;w++)
			{
				pix[h*width+w]=pixels[h*width*nbands+nbands*w];
			}
		}
		//取得亮度最大最小值
		int arrminmax[]=getArr(pix);
		int len=arrminmax[0]+1;
		int arrgr[]=new int[len];
		for(int i=0;i<tot;i++)
		{
			arrgr[pix[i]]=1+arrgr[pix[i]];
		}
		
		double gray=0.0,gray1=0.0,gray2=0.0;
		double p[]=new double[len];
		for(int i=1;i<=p.length;i++)
		{
			System.out.println(arrgr[i-1]);
			p[i-1]=arrgr[i-1]/tot;
			gray=gray+i*p[i-1];
		}double c=0;
		for(int j=0;j<len;j++){c=c+p[j];}System.out.println(c);
		double percent1=0.0,percent2=0.0,gr1avg=0.0,gr2avg=0.0;
		double conavg[]=new double[len];
		int threshold=1;
		int c1pix=0,c2pix=0;
		for(int t=2;t<=len;t++)
		{
			percent1=0.0;
			percent2=0.0;
			gray1=0.0;
			gray2=0.0;
			c1pix=0;
			c2pix=0;
			gr1avg=0.0;
			gr2avg=0.0;
			for(int i=1;i<=t;i++)
			{
				gray1=gray1+i*p[i-1];
				c1pix=c1pix+arrgr[i-1];
				percent1=percent1+p[i-1];
			}
			if(percent1==0)
			{
				conavg[t-2]=0;
				continue;
			}
			gr1avg=gray1/percent1;
			gray2=gray-gray1;
			c2pix=(int)tot-c1pix;
			percent2=1.0-percent1;
			gr2avg=gray2/percent2;
			conavg[t-2]=percent1*percent2*(gr1avg-gr2avg)*(gr1avg-gr2avg);
		}
		
		threshold=(int)getConAvg(conavg);
		//设置图像
		for(int i=0;i<tot;i++)
		{
			if(pix[i]<threshold)
				pix[i]=0;
			else pix[i]=1;
				
		}
		int pix2[][]=new int[height][width];
		for(int h=0;h<height;h++)
		{
			for(int w=0;w<width;w++)
			{
				pix2[h][w]=pix[h*width+w];
			}
		}//for(int h=0;h<height;h++)
		{
			//for(int w=0;w<width;w++)
		//	{
			//	System.out.print(pix2[h][w]);
		//	}System.out.println("\n");
		}
		//增添不变矩
		setList(ltsv,getHumoment(pix2,0,0,(int)(pix2.length/divide),(int)(pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,0,(int)(pix2[0].length/divide),(int)(pix2.length/divide),(int)((divide-2)*pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,0,(int)((divide-1)*pix2[0].length/divide),(int)(pix2.length/divide),pix2[0].length-(int)((divide-1)*pix2[0].length/divide)));//pix2.length
		setList(ltsv,getHumoment(pix2,(int)(pix2.length/divide),0,(int)((divide-2)*pix2.length/divide),(int)(pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,(int)(pix2.length/divide),(int)(pix2[0].length/divide),(int)((divide-2)*pix2.length/divide),(int)((divide-2)*pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,(int)(pix2.length/divide),(int)((divide-1)*pix2[0].length/divide),(int)((divide-2)*pix2.length/divide),pix2[0].length-(int)((divide-1)*pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,(int)((divide-1)*pix2.length/divide),0,pix2.length-(int)((divide-1)*pix2.length/divide),(int)(pix2[0].length)/divide));
		setList(ltsv,getHumoment(pix2,(int)((divide-1)*pix2.length/divide),(int)(pix2[0].length/divide),pix2.length-(int)((divide-1)*pix2.length/divide),(int)((divide-2)*pix2[0].length/divide)));
		setList(ltsv,getHumoment(pix2,(int)((divide-1)*pix2.length/divide),(int)((divide-1)*pix2[0].length/divide),pix2.length-(int)((divide-1)*pix2.length/divide),pix2[0].length-(int)((divide-1)*pix2[0].length/divide)));
	/*	for(int j=0;j<lt.size()/7;j++)
		{
			//Double l[]=(Double[])lt.get(j);
			for(int k=0;k<7;k++)
			{
				System.out.print(lt.get(j*7+k)+" ");
			}
			System.out.println();	
		}*/
		/*DataBufferInt d=new DataBufferInt(pix,height*width);
		 SampleModel sampleModel =
		       RasterFactory.createBandedSampleModel(DataBuffer.TYPE_BYTE,
		                                             width,
		                                             height,
		                                              1);
		 BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
		    // Create a compatible ColorModel.
		    ColorModel colorModel = PlanarImage.createColorModel(sampleModel);
		    // Create a WritableRaster.
		    Raster raster = RasterFactory.createWritableRaster(sampleModel,d,
		                                                       new  java.awt. Point(0,0));
		    image.setData(raster);
		    try{
		    	javax.imageio.ImageIO.write(image,"jpg",new java.io.File("argbpattern.jpg"));

		    }
		    catch(Exception w){w.printStackTrace();}*/
		return ltsv;
	}
	
	//取得最大最小值
	public int[] getArr(int arr[])
	{
		int max=arr[0],min=arr[1];
		for(int i=0;i<arr.length;i++)
		{
			if(max<arr[i])
				max=arr[i];
			if(min>arr[i])
				min=arr[i];
		}
		int arrminmax[]={max,min};
		return arrminmax; 
	}
	
	//取得阀值
	public double getConAvg(double arr[])
	{
		double max[]={0,0.0};
		for(int i=0;i<arr.length;i++)
		{
			if(max[1]<arr[i])
			{	
				max[0]=i;
				max[1]=arr[i];
			}
		}
		return max[0]; 
	}
	
	public void setList(List lt,String vector[])
	{
		for(int c=0;c<vector.length;c++)
		{
			lt.add(vector[c]);
		}
	}
}
