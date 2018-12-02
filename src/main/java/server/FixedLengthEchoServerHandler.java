package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: ClarkRao
 * @Date: 2018/12/2 20:52
 * @Description:
 */
public class FixedLengthEchoServerHandler extends ChannelInboundHandlerAdapter {
    int counter = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive client ：[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常，关闭链路
        ctx.close();
    }
}
