package com.junhua.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author junhua
 * @date 2019/5/23 9:53 AM
 */
public class  TestServer {

  public static void main(String[] args) throws InterruptedException {
    // accept connection
    EventLoopGroup boosGroup = new NioEventLoopGroup();

    // deal with connection
    EventLoopGroup workGroup = new NioEventLoopGroup();



    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(boosGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new TestServerInitializer());

      ChannelFuture future = serverBootstrap.bind(8899).sync();

      future.channel().closeFuture().sync();
    } finally {
      boosGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }

  }

}
