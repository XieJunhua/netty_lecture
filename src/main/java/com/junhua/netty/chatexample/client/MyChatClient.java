package com.junhua.netty.chatexample.client;

import com.junhua.netty.example2.client.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 2:09 PM
 */
public class MyChatClient {

  public static void main(String[] args) throws Exception {
    EventLoopGroup eventExecutors = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
          .handler(new MyChatClientInitializer());
      Channel channel = bootstrap.connect("localhost", 8899)
          .sync().channel();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      for (;;) {
        channel.writeAndFlush(br.readLine() + "\r\n");

      }
    } finally {
      eventExecutors.shutdownGracefully();
    }
  }

}
