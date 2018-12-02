package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: ClarkRao
 * @Date: 2018/12/2 20:30
 * @Description:
 */
public class EchoClient {
    /**
     *  端口号
     */
    private static final int PORT = 8888;
    /**
     *   主机ip
     */
    private static final String HOST = "127.0.0.1";

    public void connect(int port,String host) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //添加分隔符 “$_”
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            //分隔符作结束标志的解码器
                            ch.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(1024, delimiter));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {

        new EchoClient().connect(PORT,HOST);
    }
}
