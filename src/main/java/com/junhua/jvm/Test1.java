package com.junhua.jvm;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/6/5 6:16 AM
 */
public class Test1 {

  public static void main(String[] args) {
    System.out.println(MyParent.str);
  }
}

class MyParent {
  public static final String str = "hello";
  static {
    System.out.println("load parent");
  }
}
