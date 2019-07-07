package com.junhua.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/6/8 4:26 PM
 */
public class MyClassLoader extends ClassLoader {
  private String classLoadName;

  private String path;

  private String fileExtension = ".class";


  public MyClassLoader(ClassLoader parent, String classLoadName) {
    super(parent);
    this.classLoadName = classLoadName;
  }

  public MyClassLoader(String classLoadName) {
    this.classLoadName = classLoadName;
  }

  public void setPath(String path) {
    this.path = path;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    System.out.println("findClass invoked: " + name);
    System.out.println("class loader name: " + classLoadName);
    byte[] data = this.loadClassData(name);
    return this.defineClass(name, data, 0, data.length);

  }

  /**
   * 读取class数据到byte数组中
   * @param className
   * @return
   */
  private byte[] loadClassData(String className) {
    InputStream in = null;
    byte[] data = null;
    ByteArrayOutputStream baos = null;
    className = className.replace(".", "/");
    try {
      in = new FileInputStream(new File(this.path + className + this.fileExtension));
      baos = new ByteArrayOutputStream();
      int ch;
      while (-1 != (ch = in.read())) {
        baos.write(ch);
      }
      data = baos.toByteArray();
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      try {
        in.close();
        baos.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return data;
  }

  public static void main(String[] args) throws Exception {
    MyClassLoader load1 = new MyClassLoader("load1");
    MyClassLoader load2 = new MyClassLoader("load1");
    load1.setPath("/Users/xiejunhua/Desktop/");
    load2.setPath("/Users/xiejunhua/Desktop/");
    Class<?> clazz = load1.loadClass("com.junhua.jvm.MySample");
    Class<?> clazz2 = load2.loadClass("com.junhua.jvm.MySample");
    System.out.println(clazz == clazz2);
  }
}
