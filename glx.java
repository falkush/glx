import java.awt.AWTException;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class glx {

	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
				
		int height=1080;
		int width=1920;
	
		int fskip=0; //included
		int endskip=7200;
		
		double dist=1000000000d;
		double ymax=1d;
		double lummin=1d; 
		
		
		double randtmp;
		double end=100000000;
		int i=0,pw,ph,ii;
		int currentpix;
	    
	    BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
	    
	    ///////////////////////////////////////////////////////////////////////////////////
		
		int nbframe=0;
		
		double zmax=(9d/16d)*ymax;
		double rad;
		
		double[] star = new double[3];
			
		double[] pos = new double[3];
		double[] dpos = new double[3];
		double[] dpos2 = new double[3];
		
		double rayon,x1,x2,x3,lamb;
		
		double tmpy,tmpz;
		double yc,zc;
		double dar;
		double rad2;

		double rsize;
		double ang,r2;
		
		int hor,vert;
		
		double[] lvec = new double[3];
		double num,denom,d;
		
		
		double[][] lum=new double[height][width];
		
		double[] x = new double[3];
		double[] y = new double[3];
		double[] z = new double[3];
		
		
		double[] pl = new double[3];
		double pll;
		
		double centx,centy;
		
		int signtmp;
		
		double tmp1;
		
		double[] starl = new double[3];
		
		double[] lvec2 = new double[3];
		double denom2;
		double d2;
		double yc2,zc2,tmpy2,tmpz2;
		
		
		x[0]=1;
		y[1]=1;
		z[2]=1;
		
		pos[0]=-2d;
		pos[1]=0.0000000001d;
		pos[2]=0.0000000001d;

	    
	    final byte[] pixels =((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	 
	    
	    for(nbframe=0;nbframe<fskip;nbframe++)
	    {
	    	for(i=0;i<3;i++)pos[i]-=x[i]*Math.pow(nbframe/5d, 5)/200000d;
	    }
	    
	   for(nbframe=fskip;nbframe<=endskip;nbframe++)
	   {		   		
				   lummin=hlum2(nbframe);
		   			
		    		for(ph=0;ph<height;ph++)
			 	    {
			 	    	for(pw=0;pw<width;pw++)
			 	    	{
			 	    		lum[ph][pw]=0;
			 	    	}
			 	    }
		    		
		    		Random rg = new Random(543543);
		    		
		    		
		    		signtmp=1;
		    		for(ii=0;ii<2;ii++)
		    		{
		    		for(tmp1=0;tmp1<end;tmp1++)
		    		{
		    			
		    			rsize=30000000000d;


		    				randtmp=Math.abs(rg.nextGaussian()*end/4);
		    				
		    				ang=3d*Math.PI/(end)*randtmp+(signtmp*Math.PI/2d);
		    				r2=100000000000d*randtmp/end;
		    				
		    				centx=r2*Math.cos(ang);
		    				centy=r2*Math.sin(ang);

		    			
		    			rayon=rg.nextGaussian()*rsize;
		    			
		    			x1=rg.nextGaussian();
		    			x2=rg.nextGaussian();
		    			x3=rg.nextGaussian();
		    			
		    			lamb=Math.sqrt(x1*x1+x2*x2+x3*x3);
		    			
			    		star[0]=rayon*x1/lamb;
			    		star[1]=rayon*x2/lamb+centx;
			    		star[2]=rayon*x3/lamb+centy;
			    		
			    		if(tmp1==0)
			    		{
			    			star[0]=0d;
			    			star[1]=0d;
			    			star[2]=0d;
			    		}
			    		
			    		
			    		for(i=0;i<3;i++) lvec[i]=star[i]-pos[i];
			    		
			    		num=x[0]*x[0]+x[1]*x[1]+x[2]*x[2];
			    		denom=lvec[0]*x[0]+lvec[1]*x[1]+lvec[2]*x[2];
			    		
			    		d=num/denom;
			    		
			    		if(d>0)
			    		{
			    			pl[0]=1;
				    		pl[1]=1;
				    		pl[2]=((double)(-1.0))*(lvec[0]+lvec[1])/lvec[2];
				    		
				    		pll=(double) Math.sqrt(2+pl[2]*pl[2]);
				    		
				    		
				    		starl[0]=star[0]+(pl[0]/pll);
				    		starl[1]=star[1]+(pl[1]/pll);
				    		starl[2]=star[2]+(pl[2]/pll);
				    		
				    		for(i=0;i<3;i++) lvec2[i]=starl[i]-pos[i];
				    		
				    		denom2=lvec2[0]*x[0]+lvec2[1]*x[1]+lvec2[2]*x[2];
				    		d2=num/denom2;
				    		
				    		for(i=0;i<3;i++) dpos2[i]=d2*lvec2[i]+pos[i];
				    		for(i=0;i<3;i++) dpos2[i]=dpos2[i]-(pos[i]+x[i]);
				    		
				    		yc2=dpos2[0]*y[0]+dpos2[1]*y[1]+dpos2[2]*y[2];
				    		zc2=dpos2[0]*z[0]+dpos2[1]*z[1]+dpos2[2]*z[2];
				    	
				    		
				    		tmpy2=(((yc2/ymax)*((double)width/(double)2)))+(width/2);
				    		tmpz2=(((zc2/zmax)*((double)height/(double)2)))+(height/2);
				    				
			    		
				    		for(i=0;i<3;i++) dpos[i]=d*lvec[i]+pos[i];
				    		for(i=0;i<3;i++) dpos[i]=dpos[i]-(pos[i]+x[i]);
				    		
				    		yc=dpos[0]*y[0]+dpos[1]*y[1]+dpos[2]*y[2];
				    		zc=dpos[0]*z[0]+dpos[1]*z[1]+dpos[2]*z[2];
				    	
				    		
				    		tmpy=(((yc/ymax)*((double)width/(double)2)))+(width/2);
				    		tmpz=(((zc/zmax)*((double)height/(double)2)))+(height/2);
				    		
				    		rad=(double)Math.sqrt((tmpy2-tmpy)*(tmpy2-tmpy) + (tmpz2-tmpz)*(tmpz2-tmpz));
				    		dar=(double)Math.sqrt((pos[0]-star[0])*(pos[0]-star[0]) +(pos[1]-star[1])*(pos[1]-star[1]) +(pos[2]-star[2])*(pos[2]-star[2]));
				    		
				    		

				    		if(rad<3000)
				    		{
				    			if(dar<5000)
				    			{
						    		if(tmpz-rad < height)
						    		{
						    			for(vert=(int)Math.max(0, tmpz-rad);vert<=(int)Math.min(height-1,tmpz+rad);vert++)
						    			{
						    				if(rad*rad-((vert-tmpz)*(vert-tmpz))<0) rad2=0;
						    				else rad2=(double)Math.sqrt(rad*rad-((vert-tmpz)*(vert-tmpz)));
						    				
						    				if(tmpy-rad2 < width)
						    				{
						    					for(hor=(int)Math.max(0, tmpy-rad2);hor<=(int)Math.min(width-1, tmpy+rad2);hor++)
						    					{
						    						if((-255d/dist)*dar+255>lummin) lum[vert][hor]+=(-255d/dist)*dar+255;
						    						else lum[vert][hor]+=lummin;
						    					}
						    				}
						    			}
						    		}
				    			}
				    			else if(tmpz<height && tmpy < width && tmpz>=0 && tmpy>=0)
				    			{
				    				if((-255d/dist)*dar+255>lummin) lum[(int)tmpz][(int)tmpy]+=(-255d/dist)*dar+255;
		    						else lum[(int)tmpz][(int)tmpy]+=lummin;
				    			}
				    		}
				    		
				    		
			    		}
		    		}
		    		signtmp=-1;
		    		}
		    		currentpix=0;
		    		if(nbframe>=125 && nbframe<=164)
	 	    		{
		    			 for(ph=0;ph<height;ph++)
					 	    {
					 	    	for(pw=0;pw<width;pw++)
					 	    	{
					 	    			if((ph==540 && pw==959)||(ph==540&&pw==960)) pixels[currentpix]=(byte)quickfix(nbframe);
					 	    			else pixels[currentpix]=(byte)(Math.min(lum[ph][pw],255)); 
						 	    		currentpix++;
					 	    	}
					 	    }
	 	    		}
		    		else
		    		{
		    			 for(ph=0;ph<height;ph++)
					 	    {
					 	    	for(pw=0;pw<width;pw++)
					 	    	{
						 	    		pixels[currentpix]=(byte)(Math.min(lum[ph][pw],255)); 
						 	    		currentpix++;
					 	    	}
					 	    }
		    		}
		    		
			 	   
			 	    
			 	    
			 	   for(i=0;i<3;i++)pos[i]-=x[i]*Math.pow(nbframe/5d, 5)/200000d;
			 	   
			 	   ImageIO.write(image, "bmp", new File("C:/glx/img"+String.format("%04d", nbframe)+".bmp/"));
				   System.out.println(nbframe);
	    	}
	}
	

	
	public static double hlum2(int nbframe)
	{
		if(nbframe<2004) return 3d;
		return 1834.31d*Math.exp(-3.20279*(double)nbframe/1000d);
	}
	
	public static double quickfix(int nbframe)
	{
		return 1072.3077d-6.53846d*(double)nbframe;
	}
	
	public static double coefa(double y1,double y2,double x1, double x2)
	{
		return (y2-y1)/(x2-x1);
	}
	
	public static double coefb(double y1,double x1,double coefa)
	{
		return (y1-coefa*x1);
	}
}