package netty4.fileExample;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FileClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		// long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
		
		ctx.
		System.out.println((ChunkedFile)((RandomAccessFile) msg).length());
//		try {
//			RandomAccessFile raf = new RandomAccessFile("c:\\a", "rw");
//			ctx.writeAndFlush(msg);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
