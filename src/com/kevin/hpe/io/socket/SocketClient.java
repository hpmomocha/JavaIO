package com.kevin.hpe.io.socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = 7000;
		String host = "localhost";

		Socket socket = new Socket(host, port);

		DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

		Scanner sc = new Scanner(System.in);

		boolean flag = false;

		while (!flag) {
			System.out.println("请输入正方形的边长:");
			double length = sc.nextDouble();

			dos.writeDouble(length); // 作为服务器端的输入
			dos.flush();

			double area = dis.readDouble();
			System.out.println("服务器返回的计算面积为:" + area);

			while (true) {
				System.out.println("继续计算？(Y/N)");

				String str = sc.next();

				if (str.equalsIgnoreCase("N")) {
					dos.writeInt(0);
					dos.flush();
					flag = true;
					break;
				} else if (str.equalsIgnoreCase("Y")) {
					dos.writeInt(1);
					dos.flush();
					break;
				}
			}
		}
		socket.close();
	}

}
