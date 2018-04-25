// //package sequence.hadoop;

// import javax.activation.MimetypesFileTypeMap;
// import java.io.File;
// import java.awt.Graphics2D;
// import javax.imageio.ImageIO;
// import java.awt.image.BufferedImage;
// import java.awt.RenderingHints;
// import java.math.BigInteger;
// import javax.imageio.ImageReader;
// import java.io.IOException;
// import java.util.Random;
// import javax.imageio.ImageReader;
// import java.util.Iterator;
// import javax.imageio.stream.ImageInputStream;
// import java.awt.image.Raster;
// //import package


// class cpimg{

//     public static final int COLOR_TYPE_RGB = 1;
//     public static final int COLOR_TYPE_CMYK = 2;
//     public static final int COLOR_TYPE_YCCK = 3;
//     private int colorType = COLOR_TYPE_RGB;
//     private boolean hasAdobeMarker = false;

// 	final static int WIDTH = 8, HEIGHT = 8;
// 	public static void main(String[] args) {
// 		if(args.length != 2) {
// 			System.out.println("[Usage: ] $ java cpimg img1 img2");
// 		}

// 		else{
			
// 			String hash1 = avhash(args[0]);
// 			String hash2 = avhash(args[1]);

// 			int dist = hamming(hash1, hash2);
// 			System.out.println(String.format(
// 				"hash(%s) = %s\nhash(%s) = %s\nhamming distance = %d\nsimilarity = %d%%", args[0], hash1, args[1], hash2, dist, (64 - dist) * 100 / 64));
// 		}
// 	}

// 	public void cpimg(){

// 	}

// 	public int getResultCompare(String pathi1, String pathi2){
// 			String hash1 = avhash(pathi1);
// 			String hash2 = avhash(pathi2);
// 	 		int dist = hamming(hash1, hash2);
// 	 		return (WIDTH*HEIGHT - dist) * 100 / (WIDTH*HEIGHT);

// 	}

	

// 	public static String avhash(String path){
// 		BigInteger result = new BigInteger("0");
// 		File f = new File(path);
		
// 		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
//     	mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp webp hdr bat bpg svg");
		
// 		String mimetype= mimetypesFileTypeMap.getContentType(f);
// 		String type = mimetype.split("/")[0];
//         if(type.equals("image")){
//             System.out.println("It's an image");
//             BufferedImage inputimage = null;
//             try{

//                 try{

//                     inputimage = readImage(f);
//                     //final ImageInfo imageInfo = Imaging.getImageInfo(f);
//                     //if(imageInfo.getColorType() == ImageInfo.COLOR_TYPE_CMYK){
                        
//                     //}
//                     //else {
                        
//                     //}   
//                 }catch(Exception e){
//                     // //Catch image encoding with CMYK
//                     // Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
//                     // ImageReader reader = null;
//                     // //Find a suitable ImageReader
//                     // while(readers.hasNext()) {
//                     //     reader = (ImageReader)readers.next();
//                     //     if(reader.canReadRaster()) {
//                     //         break;
//                     //     }
//                     // }
//                     // //Stream the image file (the original CMYK image)
//                     // ImageInputStream input = ImageIO.createImageInputStream(f); 
//                     // reader.setInput(input);
//                     // //Read the image raster
//                     // Raster raster = reader.readRaster(0, null);
//                     // //Create a new RGB image
//                     // inputimage = new BufferedImage(raster.getWidth(), raster.getHeight(), 
//                     // BufferedImage.TYPE_INT_ARGB_PRE);
//                     //     //Fill the new image with the old raster
//                     // inputimage.getRaster().setRect(raster);


//                 }
                
//             	System.out.println(path+"---"+inputimage.getWidth()+"--"+inputimage.getHeight());
//             	// for(int i = 0; i<inputimage.getWidth();i++){
//             	// 	for(int j=0; j<inputimage.getHeight();j++) {
//             	// 	System.out.println(inputimage.getRGB(i,j));
//              //        if(j == 3)break;
            		
//             	// }if(i == 3)break;
//              //    }


//             	BufferedImage outimage = new BufferedImage(
//             		WIDTH,
//             		HEIGHT,
//             		BufferedImage.TYPE_BYTE_GRAY);
            	
//             	Graphics2D g2d = outimage.createGraphics();
//             	g2d.drawImage(inputimage,0,0,WIDTH,HEIGHT,null);
//             	RenderingHints rh = new RenderingHints(
//              	RenderingHints.KEY_ANTIALIASING,
//              	RenderingHints.VALUE_ANTIALIAS_ON);
//             	g2d.setRenderingHints(rh);
//             	g2d.dispose();
            	
//             	Random rand = new Random();
// 				int  n = rand.nextInt(50) + 1;
//             	ImageIO.write(outimage, "PNG", new File(n+".png"));
            	
//             	int sumpixelvalue = 0, c=0;
//             	int[] arrpixelvalue = new int[WIDTH*HEIGHT];
//             	outimage.getRGB(0,0,WIDTH,HEIGHT,arrpixelvalue,0,WIDTH); //= new int[WIDTH];

//             	for (int row = 0; row < HEIGHT; row++) 
//          			for (int col = 0; col < WIDTH; col++) 
//          			{
//          				arrpixelvalue[c] = outimage.getRaster().getSample(row, col, 0);
//          				sumpixelvalue += arrpixelvalue[c];
//          				System.out.print(arrpixelvalue[c]+", ");
//          				c++;
            			
//          			}
//          		System.out.println("\n");

         		
//             	double avg = sumpixelvalue / (WIDTH*HEIGHT*1.0);
				
// 				BigInteger bigvalue[]=new BigInteger[arrpixelvalue.length];
//             	for(int i = 0; i < WIDTH*HEIGHT; i++){
//             		bigvalue[i] = arrpixelvalue[i] < avg ? BigInteger.ZERO : BigInteger.ONE;
            		
//             	}
//             	System.out.println("\n");



//             	for (int i = 0; i < WIDTH*HEIGHT; i++)
//             		result = result.or(bigvalue[i].shiftLeft(i));

//             }
//             catch(Exception e){
//                 System.out.println(e);
//             }
//         }
//         else {
//             System.out.println("It's NOT an image");
//             return "";
//         }


//         return result.toString();
// 	}

// 	private static int hamming(String h1, String h2){
// 		int h = 0;

// 		long d = new BigInteger(h1).xor(new BigInteger(h2)).longValue();
// 		while(d!=0){
// 			h+=1;
// 			d &= d-1;
// 		}
// 		return h;
// 	}

//     public BufferedImage readImage(File file) throws IOException, ImageReadException {
//         colorType = COLOR_TYPE_RGB;
//         hasAdobeMarker = false;

//         ImageInputStream stream = ImageIO.createImageInputStream(file);
//         Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
//         while (iter.hasNext()) {
//             ImageReader reader = iter.next();
//             reader.setInput(stream);

//             BufferedImage image;
//             ICC_Profile profile = null;
//             try {
//                 image = reader.read(0);
//             } catch (IIOException e) {
//                 colorType = COLOR_TYPE_CMYK;
//                 checkAdobeMarker(file);
//                 profile = Sanselan.getICCProfile(file);
//                 WritableRaster raster = (WritableRaster) reader.readRaster(0, null);
//                 if (colorType == COLOR_TYPE_YCCK)
//                     convertYcckToCmyk(raster);
//                 if (hasAdobeMarker)
//                     convertInvertedColors(raster);
//                 image = convertCmykToRgb(raster, profile);
//             }

//             return image;
//         }

//         return null;
//     }

//     public void checkAdobeMarker(File file) throws IOException, ImageReadException {
//         JpegImageParser parser = new JpegImageParser();
//         ByteSource byteSource = new ByteSourceFile(file);
//         @SuppressWarnings("rawtypes")
//         ArrayList segments = parser.readSegments(byteSource, new int[] { 0xffee }, true);
//         if (segments != null && segments.size() >= 1) {
//             UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
//             byte[] data = app14Segment.bytes;
//             if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e')
//             {
//                 hasAdobeMarker = true;
//                 int transform = app14Segment.bytes[11] & 0xff;
//                 if (transform == 2)
//                     colorType = COLOR_TYPE_YCCK;
//             }
//         }
//     }

//     public static void convertYcckToCmyk(WritableRaster raster) {
//         int height = raster.getHeight();
//         int width = raster.getWidth();
//         int stride = width * 4;
//         int[] pixelRow = new int[stride];
//         for (int h = 0; h < height; h++) {
//             raster.getPixels(0, h, width, 1, pixelRow);

//             for (int x = 0; x < stride; x += 4) {
//                 int y = pixelRow[x];
//                 int cb = pixelRow[x + 1];
//                 int cr = pixelRow[x + 2];

//                 int c = (int) (y + 1.402 * cr - 178.956);
//                 int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
//                 y = (int) (y + 1.772 * cb - 226.316);

//                 if (c < 0) c = 0; else if (c > 255) c = 255;
//                 if (m < 0) m = 0; else if (m > 255) m = 255;
//                 if (y < 0) y = 0; else if (y > 255) y = 255;

//                 pixelRow[x] = 255 - c;
//                 pixelRow[x + 1] = 255 - m;
//                 pixelRow[x + 2] = 255 - y;
//             }

//             raster.setPixels(0, h, width, 1, pixelRow);
//         }
//     }

//     public static void convertInvertedColors(WritableRaster raster) {
//         int height = raster.getHeight();
//         int width = raster.getWidth();
//         int stride = width * 4;
//         int[] pixelRow = new int[stride];
//         for (int h = 0; h < height; h++) {
//             raster.getPixels(0, h, width, 1, pixelRow);
//             for (int x = 0; x < stride; x++)
//                 pixelRow[x] = 255 - pixelRow[x];
//             raster.setPixels(0, h, width, 1, pixelRow);
//         }
//     }

//     public static BufferedImage convertCmykToRgb(Raster cmykRaster, ICC_Profile cmykProfile) throws IOException {
//         if (cmykProfile == null)
//             cmykProfile = ICC_Profile.getInstance(JpegReader.class.getResourceAsStream("/ISOcoated_v2_300_eci.icc"));
//         ICC_ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
//         BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
//         WritableRaster rgbRaster = rgbImage.getRaster();
//         ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
//         ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
//         cmykToRgb.filter(cmykRaster, rgbRaster);
//         return rgbImage;
//     }
// }

//java -cp commons-imaging-1.0-20130811.122228-3.jar:sanselan-0.97-incubator.jar:. cpimg dataset/25.png dataset/25.gif

//package sequence.hadoop;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
//import package
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.jpeg.JpegImageParser;
import org.apache.commons.imaging.formats.jpeg.decoder.JpegDecoder;
import org.apache.commons.imaging.formats.jpeg.segments.UnknownSegment;
import org.apache.sanselan.Sanselan;

class cpimg{

    public static final int COLOR_TYPE_RGB = 1;
    public static final int COLOR_TYPE_CMYK = 2;
    public static final int COLOR_TYPE_YCCK = 3;
    private static int colorType = COLOR_TYPE_RGB;
    private static boolean hasAdobeMarker = false;

    final static int WIDTH = 8, HEIGHT = 8;

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("[Usage: ] $ java cpimg img1 img2");
        }

        else{
            
            String hash1 = avhash(args[0]);
            String hash2 = avhash(args[1]);

            int dist = hamming(hash1, hash2);
            System.out.println(String.format(
                "hash(%s) = %s\nhash(%s) = %s\nhamming distance = %d\nsimilarity = %d%%", args[0], hash1, args[1], hash2, dist, (64 - dist) * 100 / 64));
        }
    }

    public void cpimg(){

    }

    public int getResultCompare(String pathi1, String pathi2){
            String hash1 = avhash(pathi1);
            String hash2 = avhash(pathi2);
            int dist = hamming(hash1, hash2);
            return (WIDTH*HEIGHT - dist) * 100 / (WIDTH*HEIGHT);

    }

    

    public static String avhash(String path){
        BigInteger result = new BigInteger("0");
        File f = new File(path);
        
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp webp hdr bat bpg svg");
        
        String mimetype= mimetypesFileTypeMap.getContentType(f);
        String type = mimetype.split("/")[0];
        if(type.equals("image")){
            System.out.println("It's an image");
            BufferedImage inputimage = null;
            try{

                try{

                    inputimage = readImage(f);
                    //final ImageInfo imageInfo = Imaging.getImageInfo(f);
                    //if(imageInfo.getColorType() == ImageInfo.COLOR_TYPE_CMYK){
                        
                    //}
                    //else {
                        
                    //}   
                }catch(Exception e){
                    // //Catch image encoding with CMYK
                    // Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
                    // ImageReader reader = null;
                    // //Find a suitable ImageReader
                    // while(readers.hasNext()) {
                    //     reader = (ImageReader)readers.next();
                    //     if(reader.canReadRaster()) {
                    //         break;
                    //     }
                    // }
                    // //Stream the image file (the original CMYK image)
                    // ImageInputStream input = ImageIO.createImageInputStream(f); 
                    // reader.setInput(input);
                    // //Read the image raster
                    // Raster raster = reader.readRaster(0, null);
                    // //Create a new RGB image
                    // inputimage = new BufferedImage(raster.getWidth(), raster.getHeight(), 
                    // BufferedImage.TYPE_INT_ARGB_PRE);
                    //     //Fill the new image with the old raster
                    // inputimage.getRaster().setRect(raster);


                }
                
                System.out.println(path+"---"+inputimage.getWidth()+"--"+inputimage.getHeight());
                // for(int i = 0; i<inputimage.getWidth();i++){
                //  for(int j=0; j<inputimage.getHeight();j++) {
                //  System.out.println(inputimage.getRGB(i,j));
             //        if(j == 3)break;
                    
                // }if(i == 3)break;
             //    }


                BufferedImage outimage = new BufferedImage(
                    WIDTH,
                    HEIGHT,
                    BufferedImage.TYPE_BYTE_GRAY);
                
                Graphics2D g2d = outimage.createGraphics();
                g2d.drawImage(inputimage,0,0,WIDTH,HEIGHT,null);
                RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHints(rh);
                g2d.dispose();
                
                Random rand = new Random();
                int  n = rand.nextInt(50) + 1;
                ImageIO.write(outimage, "PNG", new File(n+".png"));
                
                int sumpixelvalue = 0, c=0;
                int[] arrpixelvalue = new int[WIDTH*HEIGHT];
                outimage.getRGB(0,0,WIDTH,HEIGHT,arrpixelvalue,0,WIDTH); //= new int[WIDTH];

                for (int row = 0; row < HEIGHT; row++) 
                    for (int col = 0; col < WIDTH; col++) 
                    {
                        arrpixelvalue[c] = outimage.getRaster().getSample(row, col, 0);
                        sumpixelvalue += arrpixelvalue[c];
                        System.out.print(arrpixelvalue[c]+", ");
                        c++;
                        
                    }
                System.out.println("\n");

                
                double avg = sumpixelvalue / (WIDTH*HEIGHT*1.0);
                
                BigInteger bigvalue[]=new BigInteger[arrpixelvalue.length];
                for(int i = 0; i < WIDTH*HEIGHT; i++){
                    bigvalue[i] = arrpixelvalue[i] < avg ? BigInteger.ZERO : BigInteger.ONE;
                    
                }
                System.out.println("\n");



                for (int i = 0; i < WIDTH*HEIGHT; i++)
                    result = result.or(bigvalue[i].shiftLeft(i));

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else {
            System.out.println("It's NOT an image");
            return "";
        }


        return result.toString();
    }

    private static int hamming(String h1, String h2){
        int h = 0;

        long d = new BigInteger(h1).xor(new BigInteger(h2)).longValue();
        while(d!=0){
            h+=1;
            d &= d-1;
        }
        return h;
    }

    public static BufferedImage readImage(File file) throws IOException, ImageReadException, org.apache.sanselan.ImageReadException {
        colorType = COLOR_TYPE_RGB;
        hasAdobeMarker = false;

        ImageInputStream stream = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
        while (iter.hasNext()) {
            ImageReader reader = iter.next();
            reader.setInput(stream);

            BufferedImage image;
            ICC_Profile profile = null;
            try {
                image = reader.read(0);
            } catch (IIOException e) {
                colorType = COLOR_TYPE_CMYK;
                checkAdobeMarker(file);
                profile = Sanselan.getICCProfile(file);
                WritableRaster raster = (WritableRaster) reader.readRaster(0, null);
                if (colorType == COLOR_TYPE_YCCK)
                    convertYcckToCmyk(raster);
                if (hasAdobeMarker)
                    convertInvertedColors(raster);
                image = convertCmykToRgb(raster, profile);
            }

            return image;
        }

        return null;
    }

    public static void checkAdobeMarker(File file) throws IOException, ImageReadException {
        JpegImageParser parser = new JpegImageParser();
        ByteSource byteSource = new ByteSourceFile(file);
        @SuppressWarnings("rawtypes")
        ArrayList segments = (ArrayList) parser.readSegments(byteSource, new int[] { 0xffee }, true);
        if (segments != null && segments.size() >= 1) {
            UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
            byte[] data = app14Segment.bytes;
            if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e')
            {
                hasAdobeMarker = true;
                int transform = app14Segment.bytes[11] & 0xff;
                if (transform == 2)
                    colorType = COLOR_TYPE_YCCK;
            }
        }
    }

    public static void convertYcckToCmyk(WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];
        for (int h = 0; h < height; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);

            for (int x = 0; x < stride; x += 4) {
                int y = pixelRow[x];
                int cb = pixelRow[x + 1];
                int cr = pixelRow[x + 2];

                int c = (int) (y + 1.402 * cr - 178.956);
                int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
                y = (int) (y + 1.772 * cb - 226.316);

                if (c < 0) c = 0; else if (c > 255) c = 255;
                if (m < 0) m = 0; else if (m > 255) m = 255;
                if (y < 0) y = 0; else if (y > 255) y = 255;

                pixelRow[x] = 255 - c;
                pixelRow[x + 1] = 255 - m;
                pixelRow[x + 2] = 255 - y;
            }

            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    public static void convertInvertedColors(WritableRaster raster) {
        int height = raster.getHeight();
        int width = raster.getWidth();
        int stride = width * 4;
        int[] pixelRow = new int[stride];
        for (int h = 0; h < height; h++) {
            raster.getPixels(0, h, width, 1, pixelRow);
            for (int x = 0; x < stride; x++)
                pixelRow[x] = 255 - pixelRow[x];
            raster.setPixels(0, h, width, 1, pixelRow);
        }
    }

    public static BufferedImage convertCmykToRgb(Raster cmykRaster, ICC_Profile cmykProfile) throws IOException {
        if (cmykProfile == null)
            cmykProfile = ICC_Profile.getInstance(JpegDecoder.class.getResourceAsStream("/ISOcoated_v2_300_eci.icc"));
        ICC_ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
        BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster rgbRaster = rgbImage.getRaster();
        ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
        ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
        cmykToRgb.filter(cmykRaster, rgbRaster);
        return rgbImage;
    }
}