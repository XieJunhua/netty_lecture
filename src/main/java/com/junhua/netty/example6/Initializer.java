package com.junhua.netty.example6;

import com.junhua.protobuf.MyDataInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 6:04 PM
 */
public class Initializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline()
        .addLast(new ProtobufVarint32FrameDecoder())
        .addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()))
        .addLast(new ProtobufVarint32LengthFieldPrepender())
        .addLast(new ProtobufEncoder())
    .addLast(new ServerHandler());
  }
}
