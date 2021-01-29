import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class FileTest {

	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\ljb\\Desktop\\TestIo\\";
		RandomAccessFile file = new RandomAccessFile(path + "新建文本文档.txt", "rw");
		RandomAccessFile fileCopy = new RandomAccessFile(path + "新建文本文档1.txt",
				"rw");
		String str = "插入的数据";
		byte[] bytes = str.getBytes();
		file.setLength(file.length()+bytes.length);
		file.write(bytes);
//		for(long i=file.length()-1;i>file.length();i--){
//			file.seek(i-);
//			
//		}
		
		beiju(0,"去去去",path + "新建文本文档1.txt");
	}
	
	public static void beiju(long skip, String str, String fileName){  
	    try {  
	        RandomAccessFile raf = new RandomAccessFile(fileName,"rw");  
	        if(skip <  0 || skip > raf.length()){  
	            System.out.println("跳过字节数无效");  
	            return;  
	        }  
	        byte[] b = str.getBytes();  
	        raf.setLength(raf.length() + b.length);  
	        for(long i = raf.length() - 1; i > b.length + skip - 1; i--){  
	            raf.seek(i - b.length);  
	            byte temp = raf.readByte();  
	            raf.seek(i);  
	            raf.writeByte(temp);  
	        }  
	        raf.seek(skip);  
	        raf.write(b);  
	        raf.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
}
