package control;
import javax.media.jai.*;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.*;
import java.text.*;
import java.util.*;

public class ColorVector implements Runnable{
	private int arrh[]={0,1,2,3,4,5,6},arri[]={0,1,2},arrv[]={0,1,2};
	private int divide;
	private LinkedList ltcv=new LinkedList();
	private String f;
	
	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public LinkedList getLt() {
		return ltcv;
	}

	public void setLt(LinkedList lt) {
		this.ltcv = lt;
	}
	

	public void setDivide() {
		(new Const()).setDivide();
	}

	public void run()
	{
		setDivide();
		divide=(new Const()).getDivide();
		readPict();
	}
	
	//…Ë÷√IHS
	public void readPict()
	{
		RenderedOp fi=JAI.create("fileload", f);
		IHSColorSpace hc=IHSColorSpace.getInstance();
		ColorModel ihscolor=new ComponentColorModel(hc,new int[]{8,8,8},false,false,java.awt.Transparency.OPAQUE,DataBuffer.TYPE_BYTE);ParameterBlock pb =
		     (new ParameterBlock()).addSource(fi).add(ihscolor);
		pb.add(hc);
		RenderedOp node =  JAI.create("ColorConvert", pb);
		int width=node.getWidth();
		int height=node.getHeight();
	 	SampleModel sm=node.getSampleModel();
		int nbands=sm.getNumBands();
		Raster inputRaster=node.getData();		
		int []pixels=new int[nbands*height*width];
		inputRaster.getPixels(0,0,width,height,pixels);
		 /*BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		    // Create a compatible ColorModel.
		    ColorModel colorModel = PlanarImage.createColorModel(sampleModel);
		    // Create a WritableRaster.
		    WritableRaster raster = image.getRaster();
		    int a[]=new int[3];
		     for(int i=0;i<height;i++)
		     {
		    	 for(int j=0;j<width;j++)
		    	 {
		    		 a[0]=pixels[3*i*width+j*3];
		    		 a[1]=pixels[3*i*width+j*3+1];
		    		 a[2]=pixels[3*i*width+j*3+2];raster.setPixel(i, j, a);
		    	 }
		     }
		    //image.setData(raster);
		    try{
		    	javax.imageio.ImageIO.write(image,"jpg",new java.io.File("argbpattern3.jpg"));

		    }
		    catch(Exception w){w.printStackTrace();}*/
		int []apixel= new int[nbands*(height/divide)*(width/divide)];//top left
		int []bpixel= new int[nbands*(height/divide)*(width*(divide-2)/divide)];//top
		int []cpixel= new int[nbands*(height*(divide-2)/divide)*(width*(divide-2)/divide)];//midd
		int []dpixel= new int[nbands*(height/divide)*(width-(width*(divide-1)/divide))];//top right
		int []epixel= new int[nbands*(height*(divide-2)/divide)*(width/divide)];//left
		int []fpixel= new int[nbands*(height*(divide-2)/divide)*(width-((divide-1)*width/divide))];//right
		int []gpixel= new int[nbands*(height-((divide-1)*height/divide))*(width/divide)];//b l
		int []hpixel= new int[nbands*(height-((divide-1)*height/divide))*(width*(divide-2)/divide)];//b	//br
		int []vpixel= new int[nbands*(height-((divide-1)*height/divide))*(width-((divide-1)*width/divide))];
		inputRaster.getPixels(0,0,(width/divide),(height/divide),apixel);
		setList(ltcv,getIHS((width/divide),(height/divide),nbands,apixel));
		
		inputRaster.getPixels((width/divide),0,(width*(divide-2)/divide),(height/divide),bpixel);
		setList(ltcv,getIHS((width*(divide-2)/divide),(height/divide),nbands,bpixel));

		inputRaster.getPixels((width*(divide-1)/divide),0,width-(width*(divide-1)/divide),(height/divide),dpixel);
		setList(ltcv,getIHS(width-(width*(divide-1)/divide),(height/divide),nbands,dpixel));
		
		inputRaster.getPixels(0,(height/divide),(width/divide),(height*(divide-2)/divide),epixel);
		setList(ltcv,getIHS((width/divide),(height*(divide-2)/divide),nbands,epixel));
		
		inputRaster.getPixels((width/divide),(height/divide),(width*(divide-2)/divide),(height*(divide-2)/divide),cpixel);
		setList(ltcv,getIHS((width*(divide-2)/divide),(height*(divide-2)/divide),nbands,cpixel));
		
		inputRaster.getPixels((width*(divide-1)/divide),(height/divide),width- (width*(divide-1)/divide),(height*(divide-2)/divide),fpixel);
		setList(ltcv,getIHS(width-(width*(divide-1)/divide),(height*(divide-2)/divide),nbands,fpixel));
	
		inputRaster.getPixels(0,(height*(divide-1)/divide),(width/divide),height-(height*(divide-1)/divide),gpixel);
		setList(ltcv,getIHS((width/divide),height-(height*(divide-1)/divide),nbands,gpixel));

		inputRaster.getPixels((width/divide),(height*(divide-1)/divide),(width*(divide-2)/divide),height-(height*(divide-1)/divide),hpixel);
		setList(ltcv,getIHS((width*(divide-2)/divide),height-(height*(divide-1)/divide),nbands,hpixel));

		inputRaster.getPixels((width*(divide-1)/divide),(height*(divide-1)/divide),width-(width*(divide-1)/divide),height-(height*(divide-1)/divide),vpixel);
		setList(ltcv,getIHS(width-(width*(divide-1)/divide),height-(height*(divide-1)/divide),nbands,vpixel));

	}
	
	 //∑µªÿIHS
	public String[] getIHS(int width,int height,int nbands,int []pixels)
	{
		double his_eigenvector[]=new double[63];	
		int pix[]=new int[width*height];
		int offset;
		for(int h=0;h<height;h++)
		{
			for(int w=0;w<width;w++)
			 {
				 offset=h*width*nbands+w*nbands;
				System.out.println(h+" "+w);
				 if(0<=pixels[offset]&&pixels[offset]<255*.2)
				 {
					 if(0<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]<255*.2)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[0]+arri[0];
					 else if(255*.2<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]<.7*255)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[1]+arri[0];
					 else
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[2]+arri[0];
				 }
				 if(255*.2<=pixels[offset]&&pixels[offset]<.7*255)
				 {
					 if(0<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]<255*.2)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[0]+arri[1];
					 else if(255*.2<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]<.7*255)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[1]+arri[1];
					 else
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[2]+arri[1];
				 }
				 if(.7*255<=pixels[offset]&&pixels[offset]<=255)
				 {
					 if(0<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]< 255*.2)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[0]+arri[2];
					 else if(255*.2<=pixels[offset+nbands-1]&&pixels[offset+nbands-1]<.7*255)
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[1]+arri[2];
					 else
						 pix[h*width+w]=getHue(pixels[offset+nbands-2])*9+3*arrv[2]+arri[2];
				 }
			 }
		}	
		for(int c=0;c<width*height;c++)
		{
			his_eigenvector[pix[c]]=1+his_eigenvector[pix[c]];// outputRaster.setPixels(0,0,width,height,pixels);
		}//int sum=0;
		DecimalFormat df=new DecimalFormat("0.000000");
		//LinkedList lt=new LinkedList();
		String ftpix[]=new String[63];
		for(int ho=0;ho<63;ho++)
		{ 
			//System.out.print(df.format(his_eigenvector[ho]/(width*height))+" ");//		 TiledImage ti= new TiledImage(node,1,1);
			ftpix[ho]=df.format(his_eigenvector[ho]/(width*height));
		}
	   return ftpix;	
	}
		
	public int getHue(int ang)
	{
		double c=255.0/360.0;
		if((0<=ang&&ang<23*c)||(315*c<=ang&&ang<=255))
			return arrh[0];
		else if(23*c<=ang&&ang<50*c)
			return arrh[1];
		else if(50*c<=ang&&ang<75*c)
			return arrh[2];
		else if(c*75<=ang&&ang<c*155)
			return arrh[3];
		else if(c*155<=ang&&ang<c*195)
			return arrh[4];
		else if(c*195<=ang&&ang<c*275)
			return arrh[5];
		else if(c*275<=ang&&ang<315*c)
			return arrh[6];
		return 0;
	}
	
	public void setList(List lt,String vector[])
	{
		for(int c=0;c<vector.length;c++)
		{
			lt.add(vector[c]);
		}
	}
}
