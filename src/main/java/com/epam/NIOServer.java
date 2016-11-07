package com.epam;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;


public class NIOServer {
	private  Selector selector;
	static String clientServerFirst = null;
	//init server
	public void initServer(int port) throws IOException{
		 
		//steps: 
		/*(1) Create server channel;
		 *(2) Bind server channel to local server;
		 *(3) Set server channel to non-blocking
		 *(4) Register channel to the selector for connect accept operation
		 * */
		 ServerSocketChannel serverChannel = ServerSocketChannel.open();		 
		 
		 serverChannel.socket().bind(new InetSocketAddress(port));
		 
		 serverChannel.configureBlocking(false);
		 
		 selector = Selector.open();		 
		 serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		//ServerSocketChannel serverChannel = new ServerSocketChannel(selector);
		//InetAddress inet = new InetAddress(url,port);
		System.out.println("start NIO server");
	}
	
	//listen to client request
	public void listen() throws IOException{
		// steps:
		/*(1) For loop selector to listen whether any coming request with keys
		 *(2)If there're keys,get corresponding channel which is interesting to the key event;
		 *(3) Process the event via read/write SocketChannel. 
		 *(4) Removed the selected key to avoid duplicate handling
		 * 
		 * */
		while(true){
			//
			selector.select();
			
			//return the selector selected key sets
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while(iterator.hasNext()){
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				handleKey(selectionKey);
			}
		}
	}
	
	//process request
	public void handleKey(SelectionKey selectionKey) throws IOException{
		ServerSocketChannel server = null;		
		SocketChannel serverChannel = null;
		ByteBuffer receivedByteBuffer = ByteBuffer.allocate(1024);	//Use MAX INT?
		ByteBuffer sendByteBuffer = ByteBuffer.allocate(1024);	//Use MAX INT?
		//test whether the key corresponding channel is ready to accept new client requested Socket connection
		if(selectionKey.isAcceptable()){
			System.out.println(+Calendar.getInstance().getTimeInMillis());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			
			//handle server connection socket event, client is actual client
			server = (ServerSocketChannel) selectionKey.channel();
			serverChannel = server.accept();			
			if(serverChannel==null){
				return;
			}
			serverChannel.configureBlocking(false);
			serverChannel.register(selector,SelectionKey.OP_READ);		
			
		}else if(selectionKey.isReadable()){
			//client here should be server writable
			receivedByteBuffer.clear();
			serverChannel = (SocketChannel)selectionKey.channel();
			//need to for loop receivedByteBuffer to read client data?
			int nBytes = serverChannel.read(receivedByteBuffer);
		
			while(serverChannel.read(receivedByteBuffer) > 0){
				receivedByteBuffer.flip();
				String receiveText = new String(receivedByteBuffer.array(),0,nBytes);
				System.out.println("server end receive client data--"+receiveText);
				receivedByteBuffer.clear();
			}
			if(nBytes > 0){
				String receiveText = new String(receivedByteBuffer.array(),0,nBytes);
				System.out.println("server end receive client data--"+receiveText);
				serverChannel.register(selector,SelectionKey.OP_WRITE);
			}
		}else if(selectionKey.isWritable()){
			sendByteBuffer.clear();
			//actually this client should be server from server side perspective,change the naming
			serverChannel = (SocketChannel)selectionKey.channel();
			sendByteBuffer.put("server send test".getBytes());
			sendByteBuffer.flip();
			serverChannel.write(sendByteBuffer);			
			
		}
		
	}
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {		
		NIOServer server = new NIOServer();
		server.initServer(8888);
		server.listen();
	}

}
