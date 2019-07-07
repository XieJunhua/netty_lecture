package com.junhua.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/30 6:46 AM
 */
public class NioServer {

  private static Map<String, SocketChannel> socketChannelMap = new HashMap<>();

  public static void main(String[] args) throws Exception {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);// 非阻塞
    ServerSocket serverSocket = serverSocketChannel.socket();
    serverSocket.bind(new InetSocketAddress(8899));

    Selector selector = Selector.open();

    // serverSocketChannel注册到selector上面,selector就可以监听channel上相应的事件
    // serverSocketChannel关注的是连接的事件
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    while (true) {
      try {
        selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 返回所有触发了相应的事件的selectionKey
        selectionKeys.forEach(selectionKey -> {
          SocketChannel client;
          try {
            if (selectionKey.isAcceptable()) {
              ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
              client = server.accept();
              client.configureBlocking(false);
              // socketChannel关注的是读取数据的事件
              client.register(selector, SelectionKey.OP_READ);

              String key = "[" + UUID.randomUUID() + "]";
              socketChannelMap.put(key, client);
            } else if (selectionKey.isReadable()) {
              client = (SocketChannel) selectionKey.channel();
              ByteBuffer buffer = ByteBuffer.allocate(1024);
              int count = client.read(buffer);
              if (count > 0) {
                buffer.flip();
                String receivedMessage = String
                    .valueOf(Charset.forName("utf-8").decode(buffer).array());
                System.out.println(client + ": receive " + receivedMessage);

                String senderKey = null;
                for (Map.Entry<String, SocketChannel> entry : socketChannelMap.entrySet()) {
                  if (client == entry.getValue()) {
                    senderKey = entry.getKey();
                    break;
                  }
                }

                for (Map.Entry<String, SocketChannel> entry : socketChannelMap.entrySet()) {
                  SocketChannel value = entry.getValue();
                  ByteBuffer buffer1 = ByteBuffer.allocate(1024);
                  buffer1.put((senderKey + ":" + receivedMessage).getBytes());
                  buffer1.flip();

                  value.write(buffer1);
                }
              }

            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
        selectionKeys.clear();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

}
