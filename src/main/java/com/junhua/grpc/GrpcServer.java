package com.junhua.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 5:25 PM
 */
public class GrpcServer {

  private Server server;




  public static void main(String[] args) throws IOException, InterruptedException {
    GrpcServer grpcServer = new GrpcServer();
    grpcServer.start();
    grpcServer.awaitTermination();
  }

  private void stop() {
    System.out.println("stop grpc");
    if (server != null) {
      server.shutdown();
    }
  }

  private void awaitTermination() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  private void start() throws IOException {
    this.server = ServerBuilder.forPort(8899)
        .addService(new StudentServiceImpl())
        .build().start();
    System.out.println("service start");

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("shut down jvm");
      stop();
    }));
  }



}
