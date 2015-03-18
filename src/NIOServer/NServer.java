package NIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NServer {
	// used to check all channels
	private Selector selector=null;
	private Charset charset=Charset.forName("UTF-8");
	public void init() throws IOException{
		selector=Selector.open();
		// open a server socket channel
		ServerSocketChannel server=ServerSocketChannel.open();
		InetSocketAddress isa=new InetSocketAddress("127.0.0.1",30000);
		
		server.socket().bind(isa);
		server.configureBlocking(false);
		// register server to selector
		server.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select()>0){
			// handle each selectedKeys
			for(SelectionKey sk:selector.selectedKeys()){
				
				selector.selectedKeys().remove(sk);
				//is ready to accept new connection
				if(sk.isAcceptable()){
					//create a socket channel 
					SocketChannel sc=server.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
					// set sk channel ready to accept new request.
					sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				if (sk.isReadable()){
					// get channel, and it has readable data.
					SocketChannel sc=(SocketChannel)sk.channel();
					ByteBuffer buff=ByteBuffer.allocate(1024);
					String content="";
					// read data
					try{
						while(sc.read(buff)>0){
							buff.flip();
							content+=charset.decode(buff);
						}
						System.out.println("======="+content);
						// make sk channel ready for next read.
						sk.interestOps(SelectionKey.OP_READ);
					}catch(IOException e){
						// remove slelectkey from selector
						sk.cancel();
						if(sk.channel()!=null){
							sk.channel().close();
						}
					}
					
					if(content.length()>0){
//						for(SelectionKey key:selector.keys()){
							// select right channel with key
							Channel targetChannel=sk.channel();
							if(targetChannel instanceof SocketChannel){
								// write its content to this channel
								SocketChannel dest=(SocketChannel) targetChannel;
								dest.write(charset.encode(sk.toString()+" "+content));
							}
//						}
					}
				}
			}
		}
	}
	public static void main(String args[]) throws IOException{
		new NServer().init();
	}

}
