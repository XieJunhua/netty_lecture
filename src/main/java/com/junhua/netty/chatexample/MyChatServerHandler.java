package com.junhua.netty.chatexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 1:50 PM
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

  private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.forEach(ch -> {
      if (ch != channel) {
        ch.writeAndFlush(channel.remoteAddress() + " 发送消息" + msg + " \n");
      } else {
        ch.writeAndFlush("自己发送消息" + msg + " \n");
      }
    });
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("server - " + channel.remoteAddress() + " join in \n");
    channelGroup.add(channel);


  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    channelGroup.writeAndFlush("server - " + channel.remoteAddress() + " leave out \n");
    System.out.println("channel size: " + channelGroup.size());
//    channels.remove(channel);
  }



  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println(channel.remoteAddress() + " 上线");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    System.out.println(channel.remoteAddress() + " 下线");
  }
}
