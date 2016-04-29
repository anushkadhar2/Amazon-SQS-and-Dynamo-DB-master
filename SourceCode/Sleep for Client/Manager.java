package alteration;


import java.net.Socket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.net.URI;

import java.net.*;


class Manag
{
	static List<String> var1;
    static List<String> var2;      
	public static void main(String[] args) throws Exception
	{
		class start_receive extends Thread implements Runnable
	{
		public start_receive()
	    {

	  	}
	        public void run()
	    	 {
	    	 	try{

	    	 	     card_check();
	       	         }
	       	       catch(Exception et){ } catch (Throwable e) {
					
					e.printStackTrace();
				}
	    	 }

	public void card_check() throws Throwable 
	{


			ServerSocket server = null;
	        try
	        	{
	        	
	            server = new ServerSocket(54505); 
	       		}
	        	catch (IOException e11)
	        	{
	            System.err.println("Not able to listen port: " );
	            System.err.println(e11);
	            System.exit(1);
	    	    }

	        	 Socket client = null;
	      		 while(true)
	        	 {
	          		  		try
	            	{
	            		System.out.println("Listening now!!!!");
	            		client = server.accept();
	            		 System.out.println("Accepted!!!!!!!");
	            			 File f=new File("Finalresult.txt");
                            if(!f.exists())
                            {
                                System.out.println("Create File call made!!!!");
                                f.createNewFile();
                            }
                             DataInputStream input_stream_data = new DataInputStream(client.getInputStream());
						     FileOutputStream file_output_stream = new FileOutputStream(f);
					    	 int i;
    					 	 while ((i = input_stream_data.read()) > -1)
    						 {
    								file_output_stream.write(i);
   						 	 }
   						 	 System.out.println("Finished");
   							  file_output_stream.flush();
				              file_output_stream.close();
				              input_stream_data.close();
				              client.close();
	            	}
	            	catch(Exception m)
	            	{
	            		m.printStackTrace();
	            	}
	        	 }
	}
	}
		
	
		if(args.length==0)
		{        
        System.out.println("Please enter Arguments");
		}
		else
		{
		String str_1=args[0];
		String str_2=args[1];
		String h;
		int p;
	 	Thread rt1=new Thread(new start_receive());
		rt1.start();
		
			read_File(str_2+".txt");
			 URI uri = new URI("my://" + str_1); 
  h = uri.getHost();
  p = uri.getPort();
  
       			Socket s1 = new Socket(h,p);  
       			String[] temp = new String[var1.size()];
				temp = var1.toArray(temp); 
			
				String info="FileRaw";
				ObjectOutputStream os2 = new ObjectOutputStream(s1.getOutputStream());
                os2.writeObject(info);
                os2.flush();
                
                ObjectOutputStream os1 = new ObjectOutputStream(s1.getOutputStream());
                os1.writeObject(temp);
                os1.flush();
              
                ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
                String outstr=in.readObject().toString();
                System.out.println("From "+s1.getInetAddress()+" :"+outstr);  
                	
                	
                     
                			  s1.close();
		}
	 
	

	}
public static void read_File(String filname) throws FileNotFoundException,IOException
{
	
      
        

BufferedReader reader = new BufferedReader(new FileReader(
		filname));
        var1= new ArrayList<String>();
      
	int i=0;
	while (true) {
	    String _line = reader.readLine();
	    if (_line == null) {
		break;
	    }
	   
            var1.add(i+" "+_line);
         
           i++;
	}

	
	reader.close();
}

}