package com.junhua.netty.example5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.time.LocalDateTime;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 4:23 PM
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    System.out.println("receive message : " + msg.text());
    ctx.channel().writeAndFlush(new TextWebSocketFrame("server time: " + LocalDateTime.now()));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
