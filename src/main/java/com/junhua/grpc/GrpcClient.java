package com.junhua.grpc;

import com.junhua.proto.Request;
import com.junhua.proto.Response;
import com.junhua.proto.StudentRequest;
import com.junhua.proto.StudentResponseList;
import com.junhua.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 5:31 PM
 */
public class GrpcClient {

  public static void main(String[] args) throws InterruptedException {
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
        .usePlaintext().build();
    // 阻塞式的调用
    StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
    Response response = blockingStub.getRealNameByUsername(Request.newBuilder().setUsername("heiheihei").build());
    System.out.println(response.getRealname());

    System.out.println("---------------");
    blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(10).build()).forEachRemaining(x -> {
      System.out.println(x);
    });

    System.out.println("--------------------");

    StudentServiceGrpc.StudentServiceStub serviceStub = StudentServiceGrpc.newStub(managedChannel);
    CountDownLatch countDownLatch = new CountDownLatch(1);
    StreamObserver<StudentRequest> request = serviceStub.getStudentsWrapperByAge(new StreamObserver<StudentResponseList>() {
      @Override
      public void onNext(StudentResponseList value) {
        value.getStudentResponseList().forEach(System.out::println);
      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onCompleted() {
        countDownLatch.countDown();
        System.out.println("finish");
      }
    });

    request.onNext(StudentRequest.newBuilder().setAge(10).build());
    request.onNext(StudentRequest.newBuilder().setAge(11).build());

    request.onCompleted();
    countDownLatch.await();



  }

}
