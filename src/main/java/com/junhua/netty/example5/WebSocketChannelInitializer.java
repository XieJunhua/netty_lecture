package com.junhua.netty.example5;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 4:15 PM
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline()
        .addLast(new HttpServerCodec())
        .addLast(new ChunkedWriteHandler())
        .addLast(new HttpObjectAggregator(8192))
        .addLast(new WebSocketServerProtocolHandler("/ws"))
        .addLast(new TextWebSocketFrameHandler());
  }
}
