package com.junhua.jvm;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/6/8 4:39 PM
 */
public class MySample {

  public MySample() {
    System.out.println("mysample loaded: " + this.getClass().getClassLoader());
    new MyCat();
  }
}
