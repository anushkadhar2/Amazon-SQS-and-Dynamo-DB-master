import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.net.URI;

import javax.swing.JOptionPane;


public class ImageClient {
	public static int tot=0;
	static ArrayList im;
	 static File folder;
	 static Socket sockp;
	 static	String host;
	 static	int port;
	public static void main(String[] args )
	{
		
		try {
					if(args.length==0)
		{        
        System.out.println("Enter Arguments");
		}
		else
		{
		String str1=args[0];
		String str2=args[1];
	
	 
		
		
			 URI uri = new URI("my://" + str1); // may throw URISyntaxException
		  host = uri.getHost();
		  port = uri.getPort();
			folder = new File(str2);
	        System.out.println("path  "+folder.toString());
			im=new ArrayList();
			listFilesForFolder(folder);
			callfun();
		
	
		}
      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void callfun()
	{
		try
		{
			System.out.println("-------------client------------------------------");
		String infos="Finish";
		sockp = new Socket(host, 50506);
    	String info="Finish";
    	ObjectOutputStream osp = new ObjectOutputStream( sockp.getOutputStream() );
		osp.writeObject(info);
		osp.flush();
    	 DataInputStream dis = new DataInputStream(sockp.getInputStream());
	     FileOutputStream fout = new FileOutputStream("Received.mp4");
    	 int j;
    	 while ((j = dis.read()) > -1)
    	 {
				fout.write(i1);
	 	 }
		  fout.flush();
          fout.close();
          dis.close();
		}
		catch(Exception m)
		{
			m.printStackTrace();
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
       
		im.add(folder.toString()+"/"+fileEntry.getName().toString());;
		//System.out.println(folder.toString()+"/"+fileEntry.getName().toString());
    	sockp = new Socket(host, port);
    	String info="FileTransfer";
    	ObjectOutputStream osp = new ObjectOutputStream( sockp.getOutputStream() );
		osp.writeObject(info);
		osp.flush();
		
		ObjectOutputStream osp1 = new ObjectOutputStream( sockp.getOutputStream() );
		osp1.writeObject(fileEntry.getName().toString());
		osp1.flush();
		
    	InputStream is = new FileInputStream(folder.toString()+"/"+fileEntry.getName().toString());
		BufferedInputStream bis = new BufferedInputStream(is);
		OutputStream os =sockp.getOutputStream();
      	BufferedOutputStream bos = new BufferedOutputStream(os);		     
      	byte[] buffer =  new byte[1024];
      	int rt,ct=0;
       	while( (rt = bis.read(buffer))>-1)
      	{	      					
              	bos.write(buffer, 0, readCount);
      	}
      	bis.close();		      				
      	bos.close();
      	sockp.close();
      	
          //  System.out.println(fileEntry.getName());
        }
    }
    	}
    	catch(Exception fex){
    		fex.printStackTrace();
    	}
}
	

}
