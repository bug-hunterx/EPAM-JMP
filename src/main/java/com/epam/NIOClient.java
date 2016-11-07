package com.epam;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

	private Selector selector;
	//connect to NIOServer
	public void connect(String url, int port) throws IOException{
		//TODO: Steps:
		/*
		 * (1) create client local socket channel
		 * (2) Register the connect operation for the socket channel
		 * (3) call socket channel to connect to NIOServer 
		 *  
		 * */
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);
		channel.connect(new InetSocketAddress(url,port));		
	}
	
	//listen to server request
	public void listen() throws IOException{
		SocketChannel client = null;		
		ByteBuffer sendByteBuffer = ByteBuffer.allocate(1024);
		ByteBuffer receiveByteBuffer = ByteBuffer.allocate(1024);
		//Steps:
				/*
				 * (1) For loop selector to get interested channel key including event
				 * (2) Read/Write msg from channel and process 
				 * */
		while(true){
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> ite = keys.iterator();
			while(ite.hasNext()){
				SelectionKey key = ite.next();
				ite.remove(); //remove the keys to avoid duplicate handling
				if(key.isConnectable()){					
					//test proves that server and client connectable and acceptable happens almost at the same time while server is a little earlier.					
					System.out.println(+Calendar.getInstance().getTimeInMillis());
					client = (SocketChannel)key.channel();
					//judge whether the client is in pending connection status
					if(client.isConnectionPending()){
						client.finishConnect();
						System.out.println("finish connection");
						//send info to server
						sendByteBuffer.clear();
						sendByteBuffer.put("Hello,server.".getBytes());
						sendByteBuffer.flip();
						client.write(sendByteBuffer);
					}
					client.register(selector,SelectionKey.OP_READ);
					
				}else if(key.isReadable()){
					receiveByteBuffer.clear();
					client = (SocketChannel)key.channel();
					int receiveLen = client.read(receiveByteBuffer);			
					if(receiveLen>0){
						String receiveText = new String(receiveByteBuffer.array(),0,receiveLen);
						System.out.println("client receive content from server is:"+receiveText);
					}
					//.register() method means it'll register one key at a time
					client.register(selector,SelectionKey.OP_WRITE);
					
				}else if(key.isWritable()){
					sendByteBuffer.clear();
					client = (SocketChannel)key.channel();
					
					sendByteBuffer.clear();
					sendByteBuffer.put("Write to server.".getBytes());
					sendByteBuffer.flip();
					client.write(sendByteBuffer);					
					client.register(selector,SelectionKey.OP_READ);
					
				}				
			}
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		NIOClient client = new NIOClient();
		client.connect("localhost", 8888);
		client.listen();		

	}

}
