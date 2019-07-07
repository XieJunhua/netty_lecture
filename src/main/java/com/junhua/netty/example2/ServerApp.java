package com.junhua.netty.example2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

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
          .option(ChannelOption.AUTO_CLOSE, false)
          .handler(new LoggingHandler(LogLevel.WARN))
          .childHandler(new MyServerInitializer()); //对应workGroup
      // sync确保绑定和初始化完成
      ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
      ThreadLocal<String> s = new ThreadLocal<>();
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

}
