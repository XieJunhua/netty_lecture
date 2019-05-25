package com.junhua.netty.example2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 11:56 AM
 */
public class ServerApp {

  public static void main(String[] args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(bossGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new MyServerInitializer()); //对应workGroup

      ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

}
