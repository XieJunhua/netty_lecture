package com.junhua.netty.example4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 3:47 PM
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline()
        .addLast(new IdleStateHandler(5, 7 , 10, TimeUnit.SECONDS))
    .addLast(new MyServerHandler());
  }
}
