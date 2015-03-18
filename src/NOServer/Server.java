package NOServer;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

// block IO, IO will block thread, so server need to provide many threads to each client.
// the performance will has a big problem.(thread switch..). NIO will solove with limited threads and nonblock IO.
public class Server {

	public static void main(String args[]) throws IOException{
		ServerSocket ss=new ServerSocket(30000);
		
		while(true){
			//create socket.
			Socket s=ss.accept();
			
			PrintStream ps=new PrintStream(s.getOutputStream());
			ps.println("hello client");
			
			ps.close();
			s.close();
		}
	}
}
