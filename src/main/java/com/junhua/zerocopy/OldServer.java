package com.junhua.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/30 10:45 PM
 */
public class OldServer {

  public static void main(String[] args) throws Exception{
    ServerSocket serverSocket = new ServerSocket(8899);
    while (true) {
      Socket socket = serverSocket.accept();
      DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
      byte[] byteArray = new byte[4096];
      while (true) {
        int readCount = dataInputStream.read(byteArray, 0, byteArray.length);
        
      }
    }

  }

}
