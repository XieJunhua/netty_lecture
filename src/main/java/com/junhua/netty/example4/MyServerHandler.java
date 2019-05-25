package com.junhua.netty.example4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import java.nio.channels.SocketChannel;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 3:49 PM
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      String eventType = null;

      switch (((IdleStateEvent) evt).state()) {
        case READER_IDLE:
          eventType = "读空闲";
          break;
        case ALL_IDLE:
          eventType = "读写空闲";
          break;
        case WRITER_IDLE:
          eventType = "写空闲";
          break;
      }
      System.out.println(ctx.channel().remoteAddress() + " 超时：" + eventType);
      ctx.close();
    }
  }
}
