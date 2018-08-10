package com.kevin.hpe.io.calculator.nio;

public class NIOServer {
	private static int DEFAULT_PORT = 12345;
	private static NIOServerHandler serverHandler;

	public static void start() {
		start(DEFAULT_PORT);
	}

	public static synchronized void start(int port) {
		if (serverHandler != null)
			serverHandler.stop();
		serverHandler = new NIOServerHandler(port);
		new Thread(serverHandler, "Server").start();
	}

	public static void main(String[] args) {
		start();
	}

}
