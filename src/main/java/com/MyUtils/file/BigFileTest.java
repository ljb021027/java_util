package com.MyUtils.file;

import java.io.*;

/**
 * Created by ljb on 2017/12/29.
 */
public class BigFileTest {

    public static void main(String[] args) throws IOException {
//        int bufSize = 100;
//        File fin = new File("C:\\Users\\ljb\\Desktop\\新建文本文档.txt");
//        File fout = new File("E:\\jiahui\\res.txt");
//
//        System.out.print("开始读取并重写文件，请等待...");
//        RandomAccessFile raf=new RandomAccessFile(fin, "rw");
//        raf.seek(raf.length());
//        for (int i =0;i<1000000*5;i++){
//            raf.writeBytes("first test RandomAccessFile appendfirst test RandomAccessFile appendfirst test RandomAccessFile append \r\n");
//        }
//        raf.close();
//        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
//        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
//
////        FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
////        ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);
//
////        readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);
//
//        System.out.print("读写完成！");

        System.out.println(1);
//        byte[] b = new byte[1024*40*1024];
        String path = "C:\\Users\\ljb\\Desktop\\新建文本文档.txt";
        BufferedReader bufr = new BufferedReader(new FileReader(path));
        String line = null;
        while((line = bufr.readLine())!=null)
        {
            System.out.println(line);
        }

        System.out.println(2);
    }
}
