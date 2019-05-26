package com.junhua.netty.example6.client;

import com.junhua.protobuf.MyDataInfo;
import com.junhua.protobuf.MyDataInfo.Cat;
import com.junhua.protobuf.MyDataInfo.Dog;
import com.junhua.protobuf.MyDataInfo.MyMessage;
import com.junhua.protobuf.MyDataInfo.MyMessage.DataType;
import com.junhua.protobuf.MyDataInfo.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 12:16 PM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Student student = Student.newBuilder()
        .setId(2)
        .setName("dd")
        .setAddress("wuhan")
        .build();
    Dog dog = Dog.newBuilder()
        .setName("dd")
        .setAge(12)
        .build();
    Cat cat = Cat.newBuilder()
        .setName("dd")
        .setAge(12)
        .build();

    MyMessage myMessage = null;
    int rand = new Random().nextInt(3);
    switch (rand) {
      case 0:
        myMessage = MyMessage.newBuilder()
            .setDataType(DataType.StudentType)
            .setStudent(student).build();
        break;
      case 1:
        myMessage = MyMessage.newBuilder()
            .setDataType(DataType.DogType)
            .setDog(dog).build();
        break;
      case 2:
        myMessage = MyMessage.newBuilder()
            .setDataType(DataType.CatType)
            .setCat(cat).build();
        break;
      default:
        break;

    }
    ctx.writeAndFlush(myMessage);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {

  }


}
