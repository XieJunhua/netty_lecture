package com.junhua.netty.example2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 12:05 PM
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {


  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println(ctx.channel().remoteAddress() + " : " + msg);
    ctx.channel().writeAndFlush("from server" + UUID.randomUUID());

  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
