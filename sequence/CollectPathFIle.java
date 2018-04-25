import java.io.File;
import java.io.PrintWriter;
import javax.activation.MimetypesFileTypeMap;

class CollectPathFile{

	public static void main(String[] args) {
		try{
			
			final File folder = new File(args[0]);
			PrintWriter writer = new PrintWriter("imageinfo.txt", "UTF-8");
			listFilesForFolder(folder, writer);
			writer.close();
		}
		catch(Exception e){
			System.out.println("Usages: $ java CollectPathFile [path/to/folder/]");
		}
	}
	public static void listFilesForFolder(final File folder, PrintWriter writer) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry, writer);
        } else {
        	MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        	mimetypesFileTypeMap.addMimeTypes("image png tif jpg jpeg bmp webp hdr bat bpg svg");
			
			//String namefile = fileEntry.getName().replaceAll(" ", "\\\\ ");

      		String mimetype= mimetypesFileTypeMap.getContentType(fileEntry.getName());

      		String type = mimetype.split("/")[0];

      		if(type.equals("image")){

	        	//System.out.println(fileEntry.getParent()+"/"+namefile);
	        	//File file = new File(fileEntry.getParent()+"/"+namefile);
	        	//fileEntry.renameTo(file);

	        	//writer.println(fileEntry.getParent()+"/"+namefile);
	        	writer.println("/"+fileEntry.getPath());
	            
      		}
      		else{
      			try{
      				System.out.println(fileEntry.getAbsolutePath()+" is not a image!");
      			}
      			catch(Exception e){}
      		}
        }
    }
	}


}