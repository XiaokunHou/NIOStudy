package netty4.fileCopy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FileClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		LatestFile m = (LatestFile) msg; // (1)
		// long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
		
		
		System.out.println(m.getFile().length());
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
