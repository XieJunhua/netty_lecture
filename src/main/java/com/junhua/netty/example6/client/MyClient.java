package com.junhua.netty.example6.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 12:11 PM
 */
public class MyClient {

  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup eventExecutors = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
          .handler(new MyClientInitializer());
      ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      eventExecutors.shutdownGracefully();
    }
  }

}
