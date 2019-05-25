package com.junhua.netty.chatexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 2:10 PM
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println("message from server: " + msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
