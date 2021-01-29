package com.MyUtils.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerNIO {
	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel  = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		serverSocketChannel.configureBlocking(false);

		while(true){
			SocketChannel socketChannel  = serverSocketChannel.accept();
			ByteBuffer buf = ByteBuffer.allocate(48);
			if(socketChannel != null){

			int bytesRead = socketChannel.read(buf); //read into buffer.
			while (bytesRead != -1) {

			  buf.flip();  //make buffer ready for read

			  while(buf.hasRemaining()){
			      System.out.print((char) buf.get()); // read 1 byte at a time
			  }

			  buf.clear(); //make buffer ready for writing
			  bytesRead = socketChannel.read(buf);
			}
			}
		}
	}
}
