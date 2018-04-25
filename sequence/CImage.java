package sequence.hadoop

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

public class CImage {

   public static void main(String[] args) {

	BufferedImage bufferedImage;
		
	try {
			
	  //read image file
	  bufferedImage = ImageIO.read(new File("./w1.jpg"));

	  // create a blank, RGB, same width and height, and a white background
	  BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
			bufferedImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	  //newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	  newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null);

	  // write to jpeg file

	  ByteArrayOutputStream bout = new ByteArrayOutputStream();
	  System.out.println(bout.toByteArray());
	  ImageIO.write(newBufferedImage, "png", bout);

	  //BufferedImage img = ImageIO.read(new ByteArrayInputStream(bout.toByteArray()));
	  OutputStream out = null;

		try {
		    out = new BufferedOutputStream(new FileOutputStream("w1.png"));
		    out.write(bout.toByteArray());
		} finally {
		    if (out != null) out.close();
		}
	  System.out.println("Done : " + bout.toByteArray());
			
	} catch (IOException e) {

	  e.printStackTrace();

	}

   }

}