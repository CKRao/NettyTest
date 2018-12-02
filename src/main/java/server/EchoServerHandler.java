package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: ClarkRao
 * @Date: 2018/12/2 20:14
 * @Description:
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    int counter = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        System.out.println("This is " + ++counter + " times receive client: [" + body + "]");
        //添加分隔符 “$_”
        body += "$_";

        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());

        ctx.writeAndFlush(echo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常，关闭链路
        ctx.close();
    }
}
