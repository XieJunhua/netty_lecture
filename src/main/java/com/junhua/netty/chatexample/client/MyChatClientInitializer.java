package com.junhua.netty.chatexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 2:09 PM
 */
public class MyChatClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline()
        .addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()))
        .addLast(new StringEncoder(CharsetUtil.UTF_8))
        .addLast(new StringDecoder(CharsetUtil.UTF_8))
        .addLast(new MyChatClientHandler());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
