package com.MyUtils.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class Test1 {
	public static void main(String[] args) throws Exception {
		String path = "C:\\Users\\ljb\\Desktop\\TestIo\\新建文本文档.txt";
		FileInputStream fis = new FileInputStream(new File(path));
		FileChannel inChannel = fis.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip();
			while(buf.hasRemaining()){
				System.out.print((char) buf.get());
			}
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
			
	}
}
