package com.MyUtils.io.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class SocketClient {
	public static void main(String[] args) {
		SocketClient s = new SocketClient();
		s.start();
	}
	private int port = 9999;
	public void start(){
		try {
			Socket socket = new Socket("localhost", port);
			System.out.println("客户端启动成功");
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(1);
//			socket.close();
//			outputStream.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
