package com.junhua.netty.example6;

import com.junhua.protobuf.MyDataInfo;
import com.junhua.protobuf.MyDataInfo.MyMessage;
import com.junhua.protobuf.MyDataInfo.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 6:06 PM
 */
public class ServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
    switch (msg.getDataType()) {
      case DogType:
        System.out.println(msg.getDog());
        break;
      case CatType:
        System.out.println(msg.getCat());
        break;
      case StudentType:
        System.out.println(msg.getStudent());
        break;
      default:
        break;
    }
  }
}
