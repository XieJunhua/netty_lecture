package com.junhua.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/30 7:30 AM
 */
public class NioClient {

  public static void main(String[] args) {
    try {
      SocketChannel socketChannel = SocketChannel.open();
      socketChannel.configureBlocking(false);

      Selector selector = Selector.open();
      socketChannel.register(selector, SelectionKey.OP_CONNECT);

      socketChannel.connect(new InetSocketAddress("localhost", 8899));

      while (true) {
        selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();

        for (SelectionKey selectionKey: selectionKeys) {
          if (selectionKey.isConnectable()) {
            SocketChannel client = (SocketChannel) selectionKey.channel();

            if (client.isConnectionPending()) { // 连接成功
              client.finishConnect();
              ByteBuffer buffer = ByteBuffer.allocate(1024);
              buffer.put( (LocalDateTime.now() + "连接成功").getBytes());
              buffer.flip();
              client.write(buffer);

              // 启一个新的线程从标准输入读取数据
              ExecutorService executorService = Executors.newSingleThreadExecutor();
              executorService.submit(() -> {
                while (true) {
                  try {
                    buffer.clear();
                    InputStreamReader inputStream = new InputStreamReader(System.in);
                    BufferedReader bufferedReader = new BufferedReader(inputStream);
                    String sendMessage = bufferedReader.readLine();

                    buffer.put(sendMessage.getBytes());
                    buffer.flip();
                    client.write(buffer);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
              });
            }
            client.register(selector, SelectionKey.OP_READ);
          } else if (selectionKey.isReadable()) {
            SocketChannel client = (SocketChannel)selectionKey.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int count = client.read(readBuffer);
            if (count > 0) {
              String receiveMessage = new String(readBuffer.array(), 0, count);
              System.out.println(receiveMessage);
            }
          }


        }
        selectionKeys.clear();// 清空处理完的selectkey
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
