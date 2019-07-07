package com.junhua.jvm;

import java.util.UUID;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/6/5 6:16 AM
 */
public class Test2 {

  public static void main(String[] args) {
    System.out.println(MyParent2.str);
  }
}

class MyParent2 {
  public static final String str = UUID.randomUUID().toString();
  static {
    System.out.println("load parent");
  }
}
