package com.MyUtils.io.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SocketService {
	private <T> List<T> aaa(){
		return null;
	}
	
	public static void main(String[] args) {
		SocketService s = new SocketService();
		s.startService();
	}
	private int port = 9999;
	
	public void startService(){
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("服务端启动成功，等待连接........");
			while(true){
				Socket accept = server.accept();
				new HandlerThread(accept);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
