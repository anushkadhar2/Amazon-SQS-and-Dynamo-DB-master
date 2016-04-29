

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;


import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class imgRead {
	
	private static final double FRAME_RATE = 50;
	
	private static final int SECONDS_TO_RUN_FOR = 20;
	
	private static final String outputFilename = "myVideo.mp4";
	
	private static Dimension screenBounds;
	static ArrayList im=new ArrayList();
	final static File folder = new File("Sample/");

	public imgRead() {
		try {

		final IMediaWriter writer = ToolFactory.makeWriter(outputFilename);
		listFilesForFolder(folder);
		screenBounds = Toolkit.getDefaultToolkit().getScreenSize();

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, screenBounds.width/2, screenBounds.height/2);

		long startTime = System.nanoTime();	
	
		
		for (int index = 0; index < im.size(); index++) {
	
			System.out.println("g g   -  " +im.get(index).toString());
			File sourceimage = new File(im.get(index).toString());
			BufferedImage screen = ImageIO.read(sourceimage);

			BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);

			writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
			try {
				Thread.sleep((long) (1000 / FRAME_RATE));
			} 
			catch (InterruptedException e) {
				// ignore
			}
			
		}

		writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
       
		im.add("Sample/"+fileEntry.getName().toString());;
          //  System.out.println(fileEntry.getName());
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
	
	private static BufferedImage getDesktopScreenshot() {
		try {
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(screenBounds);
			return robot.createScreenCapture(captureSize);
		} 
		catch (AWTException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
