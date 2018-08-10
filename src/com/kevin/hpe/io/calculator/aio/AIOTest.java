package com.kevin.hpe.io.calculator.aio;

import java.util.Scanner;

import com.kevin.hpe.io.calculator.aio.client.AIOClient;
import com.kevin.hpe.io.calculator.aio.server.AIOServer;

public class AIOTest {

	public static void main(String[] args) throws Exception {
		// 运行服务器
		AIOServer.start();
		// 避免客户端先于服务器启动前执行代码
		Thread.sleep(1000);
		// 运行客户端
		AIOClient.start();
		System.out.println("请输入请求消息：");
		Scanner scanner = new Scanner(System.in);
		while (AIOClient.sendMsg(scanner.nextLine()))
			;
	}
}
