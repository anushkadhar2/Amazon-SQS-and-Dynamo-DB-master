
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
classDoingWork {
     public static void main(String args[])
        throws InterruptedException, IOException {
        String importantInfo[] = {"1 sleep 1000","2 sleep 1000","3 sleep 1000","4 sleep 1000","5 sleep 1000","6 sleep 1000","7 sleep 1000","8 sleep 1000",
        "9 sleep 1000","10 sleep 1000","11 sleep 1000","12 sleep 1000","13 sleep 1000"};
         for (String importantInfo1 : importantInfo) {
             String[] dummy = importantInfo1.split(" ");
             String jid=dummy[0];
             String jname=dummy[1];
             String job=dummy[2];
             System.out.println(jid);
             Thread t =new Thread( new dowork(jid,job));
             t.start();
         }
    }

}
 public final class dowork extends Thread implements Runnable
  {
    	BufferedWriter bw;
		File f;
        String jid;
        String job;
        public dowork(String id, String rjob) throws IOException
        {
            	jid=id;
                job=rjob;
        }
      @Override
        public void run()
        {
            working(jid,job);
        }
        public void working(String id,String wjob)
        {
            try
            {
                                    f=new File("c:/result.txt");
                                    if(!f.exists())
                                    {
                                    System.out.println("Calling Create File");
                                    f.createNewFile();
                                    }
				     				Thread.sleep(Integer.parseInt(wjob));
                                    FileWriter fileWritter = new FileWriter(f,true);
                                    bw=new BufferedWriter(fileWritter);
                                    System.out.println("Success"+id);
                                    bw.write("ID "+ id +" Success");
      				     			bw.newLine();
                                    bw.close();
            }
            catch(IOException | InterruptedException | NumberFormatException e)
            {
            e.printStackTrace();
            }
        }
  }
