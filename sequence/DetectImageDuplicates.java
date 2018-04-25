package imagesdetectdup.hadoop;

import java.util.*; 
import java.io.IOException; 

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.ArrayWritable;

import java.awt.image.Raster;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.net.URI;
import javax.activation.MimetypesFileTypeMap;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.math.BigInteger;

public class DetectImageDuplicates {

  public static class TextArrayWritable extends ArrayWritable {
        public TextArrayWritable() {
            super(Text.class);
        }

        public TextArrayWritable(String[] strings) {
            super(Text.class);
            Text[] texts = new Text[strings.length];
            for (int i = 0; i < strings.length; i++) {
                texts[i] = new Text(strings[i]);
            }
            set(texts);
        }

        @Override
        public String toString(){
           return "[path file image:] "+this.toStrings()[0];
        }
  }
//Mapper class
  public static class ImageAhashMapper extends Mapper
  <Object, Text, Text, ArrayWritable> {
    
    final static int WIDTH = 8, HEIGHT = 8; 

//Map function
    public void map (Object key, Text value, 
      Context context) throws IOException, InterruptedException{
      try{
        String hash = avhash(value.toString()); //path image : get image and return hash value
        String[] arr_str = new String[] {
                    value.toString(), hash};


        System.out.println(hash);
        context.write(new Text(hash.substring(0,4)), new TextArrayWritable(arr_str));

      }catch(InterruptedException e){

      }
      //System.out.println(pair.getKey()+"----"+pair.getValue());
    }
    
    public static String avhash(String path) throws IOException,InterruptedException {
      BigInteger result = new BigInteger("0");
      //File f = new File(path);  

      Configuration conf = new Configuration();
      FileSystem fs = FileSystem.get(URI.create(path), conf); 

      MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp webp hdr bat bpg svg");
      
      String mimetype= mimetypesFileTypeMap.getContentType(path);
      String type = mimetype.split("/")[0];
          if(type.equals("image")){
              //System.out.println("It's an image");
              BufferedImage inputimage = null;
              try{

                FSDataInputStream fsDataInputStream = fs.open(new Path(path));

                try{
                  ImageInputStream input = ImageIO.createImageInputStream(fsDataInputStream); 

                  try{
                    inputimage = ImageIO.read(input);}catch(Exception e){
                      System.out.println("here: "+e);
                  }
                   
                }catch(Exception e){

                  //Catch image encoding with CMYK
                    
                    System.out.println(e);
                    Iterator readers = ImageIO.getImageReadersByFormatName("JPEG");
                    ImageReader reader = null;
                    //Find a suitable ImageReader
                    while(readers.hasNext()) {
                        reader = (ImageReader)readers.next();
                        if(reader.canReadRaster()) {
                            break;
                        }
                    }
                    //Stream the image file (the original CMYK image)
                    ImageInputStream input = ImageIO.createImageInputStream(fsDataInputStream); 
                    reader.setInput(input);
                    //Read the image raster
                    Raster raster = reader.readRaster(0, null);
                    //Create a new RGB image
                    inputimage = new BufferedImage(raster.getWidth(), raster.getHeight(), 
                    BufferedImage.TYPE_INT_ARGB_PRE);
                        //Fill the new image with the old raster
                    inputimage.getRaster().setRect(raster);
                }

                BufferedImage outimage = new BufferedImage(
                  WIDTH,
                  HEIGHT,
                  BufferedImage.TYPE_BYTE_GRAY);
                
                Graphics2D g2d = outimage.createGraphics();
                g2d.drawImage(inputimage,0,0,WIDTH,HEIGHT,null);
                RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//14829788209170334145 14829788209170334145
                g2d.setRenderingHints(rh);
                g2d.dispose();
                    
                int sumpixelvalue = 0, c=0;
                int[] arrpixelvalue = new int[WIDTH*HEIGHT];
                outimage.getRGB(0,0,WIDTH,HEIGHT,arrpixelvalue,0,WIDTH); //= new int[WIDTH];
                for (int row = 0; row < HEIGHT; row++) 
                for (int col = 0; col < WIDTH; col++) 
                {
                  arrpixelvalue[c] = outimage.getRaster().getSample(row, col, 0);
                  sumpixelvalue += arrpixelvalue[c];
                  //System.out.print(arrpixelvalue[c]+", ");
                  c++;
                    
                }
              //System.out.println("\n"); 

              
                double avg = sumpixelvalue / (WIDTH*HEIGHT*1.0);
          
                BigInteger bigvalue[]=new BigInteger[arrpixelvalue.length];
                for(int i = 0; i < WIDTH*HEIGHT; i++){
                  bigvalue[i] = arrpixelvalue[i] < avg ? BigInteger.ZERO : BigInteger.ONE;
                  
                }
                //System.out.println("\n"); 
  
  

                for (int i = 0; i < WIDTH*HEIGHT; i++)
                  result = result.or(bigvalue[i].shiftLeft(i));
              }
              catch(Exception e){ 

              }
          }
          else {
              System.out.println("It's NOT an image");
              return "";
          }
          //System.out.println("hash: ("+path+") = "+result.toString());
          return result.toString();
    }   
  }

  //Reducer class
  public static class ImageDupsReducer extends Reducer
  <Text, TextArrayWritable, Text, TextArrayWritable> {


    //Reduce function
    public void reduce(Text key, Iterable <TextArrayWritable> values,  Context context) 
      throws IOException, InterruptedException {
      
      //System.out.println("[with KEY: ]"+key.toString());
      TextArrayWritable first = new TextArrayWritable();
      
      // for(TextArrayWritable tem: values){
      //   System.out.println("path = "+tem.toStrings()[0]);
      //   System.out.println("hash = "+tem.toStrings()[1]);
      //   System.out.println("======================================");
        
      // }
      int count = 0;
      for(TextArrayWritable tem: values){
        first = tem;
        break;
      }
      for(TextArrayWritable last: values){
              if(first.toStrings()[0] != last.toStrings()[0]){
                String h1 = first.toStrings()[1];
                String h2 = last.toStrings()[1];
                if(h1!=null&&h2!=null){ 
                  int dist = hamming(h1, h2);
                  int percentSimilar = (64 - dist) * 100 / 64;

                  if(percentSimilar < 90) 
                    context.write(key, last);
                    count++;
                  }
                  else{
                    System.out.println("Chuoi Rong");
                }
              }
        }
        // System.out.println("path = "+first.toStrings()[0]);
        // System.out.println("hash = "+first.toStrings()[1]);
        // System.out.println("======================================");
      context.write(key, first);
      //System.out.println("Dem = "+count);
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
  }

  public static void main(String[] args) throws Exception{

    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "DetectImageDuplicates");
    job.setJarByClass(DetectImageDuplicates.class);
    job.setMapperClass(ImageAhashMapper.class);
    job.setCombinerClass(ImageDupsReducer.class);
    job.setReducerClass(ImageDupsReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(TextArrayWritable.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

/*

//Mapper class
  public static class ImageAhashMapper extends MapReduceBase implements Mapper
  <Object, Text, Text,Text> {
    
    final static int WIDTH = 8, HEIGHT = 8; 
//Map function
    public void map (Object key, Text value, 
      OutputCollector<Text, Text>output, Reporter reporter) throws IOException{
      String path = value.toString();
      String hash = "";
      try{
         hash= avhash(path);

      }catch(InterruptedException e){
      }
                  //Pair ___key__ = new Pair(value, hash);//left-value=path, right-key=hash
      //Map.Entry pair = new AbstractMap.SimpleEntry(uri, hash);
      output.collect(new Text(hash.substring(0,4)), new Text(path+","+hash));
    }
    
    public static String avhash(String path) throws IOException,InterruptedException {
      BigInteger result = new BigInteger("0");
      //File f = new File(path);  

      Configuration conf = new Configuration();
      FileSystem fs = FileSystem.get(URI.create(path), conf); 

      MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp webp hdr bat bpg svg");
      
      String mimetype= mimetypesFileTypeMap.getContentType(path);
      String type = mimetype.split("/")[0];
          if(type.equals("image")){
              //System.out.println("It's an image");
              BufferedImage inputimage = null;
              try{

                FSDataInputStream fsDataInputStream = fs.open(new Path(path));
                inputimage = ImageIO.read(ImageIO.createImageInputStream(fsDataInputStream)); 

                BufferedImage outimage = new BufferedImage(
                  WIDTH,
                  HEIGHT,
                  BufferedImage.TYPE_BYTE_GRAY);
                
                Graphics2D g2d = outimage.createGraphics();
                g2d.drawImage(inputimage,0,0,WIDTH,HEIGHT,null);
                RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);//14829788209170334145 14829788209170334145
                g2d.setRenderingHints(rh);
                g2d.dispose();
                    
                int sumpixelvalue = 0, c=0;
                int[] arrpixelvalue = new int[WIDTH*HEIGHT];
                outimage.getRGB(0,0,WIDTH,HEIGHT,arrpixelvalue,0,WIDTH); //= new int[WIDTH];
                for (int row = 0; row < HEIGHT; row++) 
                for (int col = 0; col < WIDTH; col++) 
                {
                  arrpixelvalue[c] = outimage.getRaster().getSample(row, col, 0);
                  sumpixelvalue += arrpixelvalue[c];
                  //System.out.print(arrpixelvalue[c]+", ");
                  c++;
                    
                }
              //System.out.println("\n"); 

              
                double avg = sumpixelvalue / (WIDTH*HEIGHT*1.0);
          
                BigInteger bigvalue[]=new BigInteger[arrpixelvalue.length];
                for(int i = 0; i < WIDTH*HEIGHT; i++){
                  bigvalue[i] = arrpixelvalue[i] < avg ? BigInteger.ZERO : BigInteger.ONE;
                  
                }
                //System.out.println("\n"); 
  
  

                for (int i = 0; i < WIDTH*HEIGHT; i++)
                  result = result.or(bigvalue[i].shiftLeft(i));
              }
              catch(Exception e){ 

              }
          }
          else {
              System.out.println("It's NOT an image");
              return "";
          }
          System.out.println("hash: ("+path+") = "+result.toString());
          return result.toString();
    }   
  }

  //Reducer class
  public static class ImageDupsReducer extends MapReduceBase implements Reducer
  <Text, Text, Text, Text> {
    //Reduce function
    public void reduce(Text key, Iterator <Text> values,
      OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
      
      //Map.Entry imginfo1 = values.next();

      String imginfo1 = values.next().toString();
      StringTokenizer st = new StringTokenizer(imginfo1,",");
      st.nextToken();
      String h1 = st.nextToken(); //hash1


      if(values.hasNext()){
        String imginfo2 = values.next().toString();
        st = new StringTokenizer(imginfo2,",");
        st.nextToken();

        String h2 = st.nextToken(); //hash2
        
        int dist = hamming(h1, h2);
        int percentSimilar = (64 - dist) * 100 / 64;
        
        if(percentSimilar < 90) 
          output.collect(key, new Text(imginfo2));
        
      }
      output.collect(key, new Text(imginfo1));
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
  }

  public static void main(String[] args) throws Exception{
    JobConf conf = new JobConf(DetectImageDuplicates.class);

    conf.setJobName("DetectImageDuplicates");
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);
    conf.setMapperClass(ImageAhashMapper.class);
    conf.setCombinerClass(ImageDupsReducer.class);
    conf.setReducerClass(ImageDupsReducer.class);
    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
  */