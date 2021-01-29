package com.MyUtils.io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HandlerThread implements Runnable{
	private Socket accept;
	public HandlerThread(Socket accept){
		this.accept = accept;
		new Thread(this).start();
	}
	@Override
	public void run() {
		InputStream is;
		try {
			System.out.println("客户端："+accept.getInetAddress());
			is = accept.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n = is.read(buf)) != -1){
				System.out.println("客户端说:"+new String(buf));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
