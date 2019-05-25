package com.junhua.netty.chatexample;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 1:46 PM
 */
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()))
        .addLast(new StringEncoder(CharsetUtil.UTF_8))
        .addLast(new StringDecoder(CharsetUtil.UTF_8))
        .addLast(new MyChatServerHandler());
  }
}
