
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;


import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

import java.security.*;
public class ScheduleImage extends Thread implements Runnable {
	public static String command[],time[];
    public static File f;
    public static BufferedWriter bw;
	private static final double FRAME_RATE = 50;
	
	private static final int SECONDS_TO_RUN_FOR = 20;
	
	private static final String outputFilename = "myVideo123456.mp4";
	
	private static Dimension screenBounds;
	static ArrayList im=new ArrayList();
	static File folder = new File("Sample/");

	 public static void main(String args[])
	 {
		 class Actions extends Thread implements Runnable
			{
				private Socket socket;
				private String time[];
			   BufferedWriter bw;
			   File f;
			   int ln=0;
			   int tot=0;

				Actions()
			    {
			   
			    
			    }
			    public void run()
			    {
			    	try{
			    	 	 regis();
			       	    }
			       	    catch(Exception et){et.printStackTrace(); }
			    }
			public void regis()
			{
				String msg;

			    try
				{
			    	 ServerSocket server = null;
			        
			             server = new ServerSocket(50506); /* start listening on the port */
			             File dir=new File("Sample/");
			             if(!dir.exists()){
			             dir.mkdir();}        
			     
				        
			           	 Socket client = null;
			       		 while(true)
			         	 {
			         	 	String s="";
			           		try
			             	{
					 	         client = server.accept();
					 	        
					 	         ObjectInputStream obcmd = new ObjectInputStream(client.getInputStream());
					             String File =obcmd.readObject().toString();
					             if(File.equalsIgnoreCase("Finish"))
						            {
					            	 new imgRead() ;
					            	    	 File index = new File("Sample/");
					            	 String[]entries = index.list();
					            	 for(String ss: entries){
					            	     File currentFile = new File(index.getPath(),ss);
					            	     currentFile.delete();
					            	 }
					            	 
					             	InputStream is = new FileInputStream("myVideo.mp4");
					        		BufferedInputStream bis = new BufferedInputStream(is);
					        		OutputStream os =client.getOutputStream();
					              	BufferedOutputStream bos = new BufferedOutputStream(os);		     
					              	byte[] buffer =  new byte[1024];
					              	int readCount,ct=0; 
					               	while( (readCount = bis.read(buffer))>-1)
					              	{	      					
					                      	bos.write(buffer, 0, readCount);
					              	}
					              	bis.close();		      				
					              	bos.close();
					             
			             	}
			             	}
			           		catch(Exception m)
			           		{
			           			m.printStackTrace();
			           		}
			         	 }
				 

			    }
			 catch(Exception ex){ 
				 ex.printStackTrace();
			 }
			}
			public BufferedImage convertToType1(BufferedImage sourceImage, int targetType) {
				
				BufferedImage image;

				// if the source image is already the target type, return the source image
				if (sourceImage.getType() == targetType) {
					image = sourceImage;
				}
				// otherwise create a new image of the target type and draw the new image
				else {
					image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
					image.getGraphics().drawImage(sourceImage, 0, 0, null);
				}

				return image;
				
			}
			
			}
			
			 Thread t = new Thread(new Actions());
       	   t.start();

			
 		 ServerSocket server = null;
         try
         	{
             server = new ServerSocket(50510); /* start listening on the port */
             File dir=new File("Sample/");
             if(!dir.exists()){
             dir.mkdir();}
           
	        
           	 Socket client = null;
       		 while(true)
         	 {
         	 	String s="";
           		try
             	{
		         
		 	         client = server.accept();
		 	        
		 	         ObjectInputStream obcmd = new ObjectInputStream(client.getInputStream());
		             String File =obcmd.readObject().toString();
		             
		             
		            if(File.equalsIgnoreCase("FileTransfer"))
		            {
		       	             
		            		ObjectInputStream obtime = new ObjectInputStream(client.getInputStream());
		            		String fname = (String)obtime.readObject();
		            
                         	 DataInputStream dis = new DataInputStream(client.getInputStream());
						     FileOutputStream fout = new FileOutputStream("Sample/"+fname);
					    	 int i1;
					    	 while ((i1 = dis.read()) > -1)
					    	 {
 								fout.write(i1);
						 	 }
							  fout.flush();
				              fout.close();
				              dis.close();
				              client.close();
           				
		           
		            }
		         
		           
		 
		             
	             } catch (Exception e)
	             {
	             	e.printStackTrace();
	                 System.err.println("Accept failed.");
	                 System.err.println(e);
	                 System.exit(1);
	             }
           	   
         	 }
            }
      	catch (Exception e)
      	{
      		e.printStackTrace();
          System.err.println("Could not listen on port: " );
       
  	    }
       		 
	 }
		public static void listFilesForFolder(final File folder) {
			try
			{
		
				//	dos.close();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	         listFilesForFolder(fileEntry);
	        }
	        else
	        {
	        	im.add(folder.toString()+"/"+fileEntry.getName().toString());
	            System.out.println(folder.toString()+"/"+fileEntry.getName());
	        }
	    }
	    	}
	    	catch(Exception fex){
	    		fex.printStackTrace();
	    	}
	}
		
		public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
			
			BufferedImage image;

			// if the source image is already the target type, return the source image
			if (sourceImage.getType() == targetType) {
				image = sourceImage;
			}
			// otherwise create a new image of the target type and draw the new image
			else {
				image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
				image.getGraphics().drawImage(sourceImage, 0, 0, null);
			}

			return image;
			
		}
}
