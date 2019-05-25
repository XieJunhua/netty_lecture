package com.junhua.netty.example2.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.time.LocalDateTime;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 12:16 PM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println(ctx.channel().remoteAddress());
    System.out.println("client accept: " + msg);
    ctx.writeAndFlush("from client" + LocalDateTime.now());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush("start...");
  }
}
