package com.junhua.nio;

import io.netty.buffer.ByteBuf;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/27 10:43 PM
 */
public class NioTest2 {

  public static void main(String[] args) throws Exception {
//    readData();
//    writeData();
//    readAndWrite();
    readAndWriteDirect();

  }

  /**
   * 构造一些内容，输出到文件中
   * @throws Exception
   */
  public static void writeData() throws Exception {
//    FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
//    FileChannel fileChannel = fileOutputStream.getChannel();
    FileChannel fileChannel = FileChannel.open(Paths.get("NioTest3.txt"),StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
    ByteBuffer buffer = ByteBuffer.allocate(32);
    byte[] message = "hello world".getBytes();
    for (int i = 0; i < message.length; i++) {
      buffer.put(message[i]);
    }
    buffer.flip();
    fileChannel.write(buffer);
    fileChannel.close();
//    fileOutputStream.close();
  }

  public static void readData() throws Exception {
    FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
    FileChannel fileChannel = fileInputStream.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(512);
    fileChannel.read(buffer);

    buffer.flip();
    while (buffer.remaining() > 0) {
      byte b = buffer.get();
      System.out.println("Character: " + (char)b);

    }
    fileInputStream.close();
  }

  public static void readAndWrite() throws Exception{
    FileChannel fileInputChannel = FileChannel.open(Paths.get("NioTest3.txt"), StandardOpenOption.READ);
    FileChannel fileOutChannel = FileChannel.open(Paths.get("NioTest4.txt"),StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

    ByteBuffer buffer = ByteBuffer.allocate(1024);

    while (true) {
      buffer.clear(); // 这里一定要调用该方法清除掉buffer中的数据，否则会一直重复输出
      int read = fileInputChannel.read(buffer);
      if(-1 == read) {
        break;
      }
      buffer.flip();
      buffer.asReadOnlyBuffer();
      fileOutChannel.write(buffer);
    }
    fileInputChannel.close();
    fileOutChannel.close();
  }

  public static void readAndWriteDirect() throws Exception{
    FileChannel fileInputChannel = FileChannel.open(Paths.get("NioTest3.txt"), StandardOpenOption.READ);
    FileChannel fileOutChannel = FileChannel.open(Paths.get("NioTest4.txt"),StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

    while (true) {
      buffer.clear(); // 这里一定要调用该方法清除掉buffer中的数据，否则会一直重复输出
      int read = fileInputChannel.read(buffer);
      if(-1 == read) {
        break;
      }
      buffer.flip();
      buffer.asReadOnlyBuffer();
      fileOutChannel.write(buffer);
    }
    fileInputChannel.close();
    fileOutChannel.close();
  }
}
