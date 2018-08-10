package com.kevin.hpe.io.calculator.aio.client;

import java.util.Scanner;

public class AIOClient {
	private static String DEFAULT_HOST = "127.0.0.1";
	private static int DEFAULT_PORT = 12345;
	private static AIOAsyncClientHandler clientHandler;

	public static void start() {
		start(DEFAULT_HOST, DEFAULT_PORT);
	}

	public static synchronized void start(String ip, int port) {
		if (clientHandler != null)
			return;
		clientHandler = new AIOAsyncClientHandler(ip, port);
		new Thread(clientHandler, "Client").start();
	}

	// 向服务器发送消息
	public static boolean sendMsg(String msg) throws Exception {
		if (msg.equals("q"))
			return false;
		clientHandler.sendMsg(msg);
		return true;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		AIOClient.start();
		System.out.println("请输入请求消息：");
		Scanner scanner = new Scanner(System.in);
		while (AIOClient.sendMsg(scanner.nextLine()))
			;
	}
}
