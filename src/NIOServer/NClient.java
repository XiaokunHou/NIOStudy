package NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NClient {
	// multiplexor for selectable channel 
	private Selector selector=null;
	private Charset charset =Charset.forName("UTF-8");
	
	// client channel
	private SocketChannel sc=null;
	public void init() throws IOException{
		selector=Selector.open();
		InetSocketAddress isa =new InetSocketAddress("127.0.0.1", 30000);
		// connect to remote address
		sc=SocketChannel.open(isa);
		sc.configureBlocking(false);
		// register channel to selector
		sc.register(selector, SelectionKey.OP_READ);
		//start client thread to read data from server
		new ClientThread().start();
		// create keyboard input stream
		Scanner scan=new Scanner(System.in);
		while(scan.hasNextLine()){
			String line=scan.nextLine();
			sc.write(charset.encode(line));
		}
		
	}
	//thread to read data from server
	private class ClientThread extends Thread{

		public void run(){
			try{
				// when 
				while(selector.select()>0){
					// iterate SelectionKey for channels who has IO operations
					for(SelectionKey sk:selector.selectedKeys()){
						// delete current one
						selector.selectedKeys().remove(sk);
						// if current channel has data ready.
						if(sk.isReadable()){
							// use NIO to read channel data
							SocketChannel sc=(SocketChannel)sk.channel();
							ByteBuffer buff=ByteBuffer.allocate(1024);
							String content="";
							while(sc.read(buff)>0){
								buff.flip();
								content+=charset.decode(buff);
							}
							//print content
							System.out.println("chart content is: "+content);
							//ready for next read
							sk.interestOps(SelectionKey.OP_READ);
						}
					}
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

	}
	
	public static void main(String []args) throws IOException{
		new NClient().init();
	}

}

