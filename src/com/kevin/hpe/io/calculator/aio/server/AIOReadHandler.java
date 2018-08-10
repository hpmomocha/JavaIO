package com.kevin.hpe.io.calculator.aio.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.kevin.hpe.io.calculator.util.Calculator;

public class AIOReadHandler implements CompletionHandler<Integer, ByteBuffer> {
	// 用于读取半包消息和发送应答
	private AsynchronousSocketChannel channel;

	public AIOReadHandler(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	// 读取到消息后的处理
	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		// flip操作
		attachment.flip();
		// 读取
		byte[] message = new byte[attachment.remaining()];
		// 读取数据到message
		attachment.get(message);

		try {
			String expression = new String(message, "UTF-8");
			System.out.println("服务器收到消息: " + expression);
			String calrResult = null;
			try {
				calrResult = Calculator.cal(expression).toString();
			} catch (Exception e) {
				calrResult = "计算错误：" + e.getMessage();
			}
			// 向客户端发送消息
			doWrite(calrResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 发送消息
	private void doWrite(String result) {
		byte[] bytes = result.getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		writeBuffer.put(bytes);
		writeBuffer.flip();

		channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				// 如果没有发送完，就继续发送直到完成
				if (buffer.hasRemaining())
					channel.write(buffer, buffer, this);
				else {
					// 创建新的Buffer
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					// 异步读 第三个参数为接收消息回调的业务Handler
					channel.read(readBuffer, readBuffer, new AIOReadHandler(channel));
				}

			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					channel.close();
				} catch (IOException e) {
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		try {
			channel.close();
		} catch (IOException e) {
		}
	}

}
