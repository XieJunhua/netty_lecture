package com.junhua.netty.example2.client;

import com.junhua.netty.example2.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 12:15 PM
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
        .addLast(new LengthFieldPrepender(4))
        .addLast(new StringDecoder(CharsetUtil.UTF_8))
        .addLast(new StringEncoder(CharsetUtil.UTF_8))
        .addLast(new MyClientHandler());
  }
}
