package com.junhua.netty.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author junhua
 * @date 2019/5/23 9:53 AM
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast("httpServerCode", new HttpServerCodec());
    pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());

  }
}
