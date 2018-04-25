package imagesdetectdup.hadoop;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.hadoop.io.WritableComparable; 
import java.io.Serializable;

final class MyEntry  implements Serializable, WritableComparable<MyEntry>{
    private String key;
    private String value;

    public MyEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void set(String key, String value){
        this.key = key;
        this.value = value;
    }

    public MyEntry(){
        this.key = "";
        this.value = "";
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
    
    @Override
    public String setValue(String value) {
        String old = this.value;
        this.value = value;
        return old;
    }

    public int compareTo(MyEntry o) {
        return 0;
    }

    public synchronized void write(DataOutput out) throws IOException {
            //.out.println(getValue());
            //System.out.println(getKey());
        try{
            System.out.println("key = "+getKey()+" ??? value = "+getValue());
            //out.writeChars(getKey()+"--");

            byte[] data = SerializationUtils.serialize((Serializable) this);

            out.write(data);
            out.writeInt(data.length);


        }
        catch(Exception e){
            System.out.println(e);
        }
    }

 
    public synchronized void readFields(DataInput in) throws IOException {
        try{
            int size = in.readInt();
            System.out.println("size = :"+ size);
            byte[] data = new byte[1000];
            in.readFully(data);
            MyEntry res = (MyEntry) SerializationUtils.deserialize(data);
            this.set(res.getKey(), res.getValue());
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public String toString(){
        return getKey() + "====" + this.getValue();
    }

}