package com.junhua.nio;

import java.io.InputStream;
import java.nio.IntBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.security.SecureRandom;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/27 10:27 PM
 */
public class NioTest1 {

  public static void main(String[] args) {
    IntBuffer intBuffer = IntBuffer.allocate(10);
    for (int i = 0; i < intBuffer.capacity(); i++) {
      int randNumber = new SecureRandom().nextInt(10);
      intBuffer.put(randNumber);
    }
    intBuffer.flip();

    while (intBuffer.hasRemaining()) {
      System.out.println(intBuffer.get());
    }
  }

}
