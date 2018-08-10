package com.kevin.hpe.io.calculator.aio.server;

public class AIOServer {
	private static int DEFAULT_PORT = 12345;
	private static AIOAsyncServerHandler serverHandler;
	public volatile static long clientCount = 0;

	public static void start() {
		start(DEFAULT_PORT);
	}

	public static synchronized void start(int port) {
		if (serverHandler != null)
			return;
		serverHandler = new AIOAsyncServerHandler(port);
		// 启动线程，执行AIOAsyncServerHandler的run方法
		new Thread(serverHandler, "Server").start();
	}

	public static void main(String[] args) {
		AIOServer.start();
	}
}
