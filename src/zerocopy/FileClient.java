package zerocopy;

import static org.jboss.netty.channel.Channels.pipeline;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseDecoder;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

public class FileClient
{
    public static void main(String[] args)
    {

        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory()
        {

            @Override
            public ChannelPipeline getPipeline() throws Exception
            {
                ChannelPipeline pipeline = pipeline();

                pipeline.addLast("decoder", new HttpResponseDecoder());
                
                /*
                 * �������������Դ����ļ� �����˴�С�����ơ���������
                 */
//                pipeline.addLast("aggregator", new HttpChunkAggregator(6048576));
                pipeline.addLast("encoder", new HttpRequestEncoder());
                pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
                pipeline.addLast("handler", new FileClientHandler());

                return pipeline;
            }

        });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(
                "localhost", 8080));

        /*
         * ����Ϊ�˱�֤connect���ӣ����ԲŽ�����sleep
         * ��ȻҲ����ͨ��future��connect�����ж�
         */
        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
                HttpMethod.GET, "DSC01575.JPG");
        future.getChannel().write(request);

        // Wait until the connection is closed or the connection attempt fails.
        future.getChannel().getCloseFuture().awaitUninterruptibly();

        // Shut down thread pools to exit.
        bootstrap.releaseExternalResources();

    }
}