package com.junhua.netty.example6.client;

import com.junhua.protobuf.MyDataInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
    ch.pipeline()
        .addLast(new ProtobufVarint32FrameDecoder())
        .addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
        .addLast(new ProtobufVarint32LengthFieldPrepender())
        .addLast(new ProtobufEncoder())
    .addLast(new MyClientHandler());
  }
}
