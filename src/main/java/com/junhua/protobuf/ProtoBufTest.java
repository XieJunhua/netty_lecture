package com.junhua.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.junhua.protobuf.MyDataInfo.Student;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/25 5:50 PM
 */
public class ProtoBufTest {

  public static void main(String[] args) throws InvalidProtocolBufferException {
    Student student = MyDataInfo.Student.newBuilder()
        .setName("xiaoming")
        .setId(12)
        .setAddress("wuhan")
        .build();

    System.out.println(student);
    System.out.println("-----------");
    System.out.println(MyDataInfo.Student.parseFrom(student.toByteArray()));
  }
}
