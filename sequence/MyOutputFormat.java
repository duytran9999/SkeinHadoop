package imagesdetectdup.hadoop;



import java.io.DataOutputStream;
import java.io.IOException;


import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.mapred.*;


public class MyOutputFormat extends FileOutputFormat<Text, MyEntry>{

	protected static class MyRecordWriter implements RecordWriter<Text, MyEntry> {
		 
		private DataOutputStream out;
	 
		public MyRecordWriter(DataOutputStream out) throws IOException {
	 
			this.out = out;
	 
			out.writeBytes("<Here is link path images affter detect duplication - @LeDinhTri[1513656]@[HCMUT-CSE]>\n");
	 
		}
	 
		private void writeStyle(String xml_tag,String tag_value) throws IOException {
	 
			out.writeBytes(" [ "+xml_tag+" ] "+tag_value+" [ "+xml_tag+" ]\n");
	 
		}
	 
		public synchronized void write(Text key, MyEntry value) throws IOException {
	 
			out.writeBytes("<image>\n");
	 
			this.writeStyle("@-> key: ", key.toString());
	 
			this.writeStyle("@-> value: ", value.toString());
	 
			out.writeBytes("</image>\n");
	 
		}

		@Override
		public synchronized void close(Reporter arg0) throws IOException {
			// TODO Auto-generated method stub
			try {
				out.writeBytes("</========================End===========================>\n");
			} finally {
				out.close();
			}
		}
	}
	
	@Override
	public RecordWriter<Text, MyEntry> getRecordWriter(FileSystem ignored,
	        JobConf job, String name, Progressable progress)
			throws IOException {
		// TODO Auto-generated method stubS

		
		FileSystem localFs = FileSystem.getLocal(job);
		FileSystem rfs = ((LocalFileSystem)localFs).getRaw();
		
		FSDataOutputStream rawOutput = rfs.create(getOutputPath(job), true);

		return new MyRecordWriter(rawOutput);
	}

}
