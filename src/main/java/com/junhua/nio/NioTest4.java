package com.junhua.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/28 3:00 AM
 */
public class NioTest4 {

  public static void main(String[] args) throws Exception {
    int[] ports = new int[5];

    for (int i = 0; i < ports.length; i++) {
      ports[i] = 5000 + i;
    }

    Selector selector = Selector.open();
    for (int port : ports) {
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);
      ServerSocket serverSocket = serverSocketChannel.socket();
      serverSocket.bind(new InetSocketAddress(port));

      // SelectionKey代表是一个事件
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("监听端口：" + port);
    }

    while (true) {
      int numbers = selector.select();
      System.out.println("numbers: " + numbers);

      Set<SelectionKey> selectionKeys = selector.selectedKeys();

      Iterator<SelectionKey> iterator = selectionKeys.iterator();

      while (iterator.hasNext()) {
        SelectionKey selectionKey = iterator.next();
        if (selectionKey.isAcceptable()) {
          ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
          SocketChannel socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);

          socketChannel.register(selector, SelectionKey.OP_READ);
          iterator.remove();
          System.out.println("获取客户端连接：" + socketChannel);
        } else if (selectionKey.isReadable()) {
          SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
          int byteRead = 0;
          while (true) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(512);
            buffer.clear();
            int read = socketChannel.read(buffer);
            if (read <= 0) {
              break;
            }
            buffer.flip();
            socketChannel.write(buffer);
            byteRead += read;
          }
          System.out.println("读取: " + byteRead + ", 来自于：" + socketChannel);
          iterator.remove();
        }
      }
    }
  }

}
