package com.junhua.jvm;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/6/8 4:38 PM
 */
public class MyCat {

  public MyCat() {
    System.out.println("my cat class loader: " + this.getClass().getClassLoader());
//    System.out.println(MySample.class);
  }
}
