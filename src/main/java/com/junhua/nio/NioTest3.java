package com.junhua.nio;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/28 2:20 AM
 */
public class NioTest3 {

  public static void main(String[] args) throws Exception{


    scatter2();
  }

  public static void mapperFile() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest2.txt", "rw");

    FileChannel fileChannel = randomAccessFile.getChannel();

    // 文件映射到对象中
    MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 5);

    mappedByteBuffer.put(0, (byte)'a');
    mappedByteBuffer.put(4, (byte)'b');

    randomAccessFile.close();
  }

  public static void scatter() throws Exception{
    // scatter 将一个channel中的数据读到多个Buffer中
    // gathering 将多个Buffer中的数据写到channel中
    ServerSocketChannel socketChannel = ServerSocketChannel.open();
    socketChannel.socket().bind(new InetSocketAddress(8899));

    int message = 2 + 3 + 4;

    ByteBuffer[] buffers = new ByteBuffer[3];
    buffers[0] = ByteBuffer.allocate(2);
    buffers[1] = ByteBuffer.allocate(3);
    buffers[2] = ByteBuffer.allocate(4);

    SocketChannel socketChannel1 = socketChannel.accept();

    while (true) {
      int byteRead = 0;
      while (byteRead < message) {
        long r = socketChannel1.read(buffers);
        byteRead += r;
        System.out.println("byteRead: " + byteRead);

        Arrays.stream(buffers).map(x -> "position: " + x.position() + ", limit: " + x.limit())
            .forEach(System.out::println);
      }

      Arrays.stream(buffers).forEach(ByteBuffer::flip);

      long bytesWritten = 0;
      while (bytesWritten < message) {
        long r = socketChannel1.write(buffers);
        bytesWritten += r;
      }

      Arrays.stream(buffers).forEach(ByteBuffer::clear);

      System.out.println("ByteRead: " + byteRead + ", ByteWrite" + bytesWritten);
    }
  }

  public static void scatter2() throws Exception {
    ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
    InetSocketAddress address=new InetSocketAddress(8899);
    serverSocketChannel.socket().bind(address);

    int messageLength=2+3+5;

    ByteBuffer[] byteBuffers=new ByteBuffer[3];
    byteBuffers[0]=ByteBuffer.allocate(2);
    byteBuffers[1]=ByteBuffer.allocate(3);
    byteBuffers[2]=ByteBuffer.allocate(5);

    SocketChannel socketChannel=serverSocketChannel.accept();
    while (true){
      int byteRead=0;
      //接受客户端写入的的字符串
      while(byteRead<messageLength){
        long r=socketChannel.read(byteBuffers);
        byteRead+=r;
        System.out.println("byteRead:"+byteRead);
        //通过流打印
        Arrays.asList(byteBuffers).stream().
            map(buffer -> "postiton:"+ buffer.position() +",limit:"+buffer.limit()).
            forEach(System.out::println);

      }
      //将所有buffer都flip。
      Arrays.asList(byteBuffers).
          forEach(buffer -> {
            buffer.flip();
          });
      //将数据读出回显到客户端
      long byteWrite=0;
      while (byteWrite < messageLength) {
        long r=socketChannel.write(byteBuffers);
        byteWrite+=r;
      }
      //将所有buffer都clear
      Arrays.asList(byteBuffers).
          forEach(buffer -> {
            buffer.clear();
          });

      System.out.println("byteRead:"+byteRead+",byteWrite:"+byteWrite+",messageLength:"+messageLength);
    }
  }

}
