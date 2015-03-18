package netty4.fileCopy;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FileServerHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void channelActive(final ChannelHandlerContext ctx){
		final ChannelFuture f = ctx.writeAndFlush(new LatestFile()); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
	}

}
