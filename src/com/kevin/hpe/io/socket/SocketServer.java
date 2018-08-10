package com.kevin.hpe.io.socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String[] args) throws IOException {
		// 端口号
		int port = 7000;
		// 在端口上创建一个服务器套接字
		ServerSocket serverSocket  = new ServerSocket(port);
		// 监听来自客户端的连接
		Socket socket = serverSocket .accept();
		
		DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		
		do {
			double length = dis.readDouble(); // 接收客户端的输出
			System.out.println("服务器端收到的边长数据为：" + length);
			double result = length * length;
			dos.writeDouble(result);
			dos.flush();
			
		} while(dis.readInt() != 0); // 不再继续计算时，client端输入0
		
		socket.close();
		serverSocket.close();
	}
}
