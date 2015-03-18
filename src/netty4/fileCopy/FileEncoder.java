package netty4.fileCopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FileEncoder extends MessageToByteEncoder<LatestFile> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LatestFile msg, ByteBuf out) {
        //out.writeInt(msg.getFile().);
        try {
        	File file=msg.getFile();
			out.writeBytes(new FileInputStream(file), (int)file.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
