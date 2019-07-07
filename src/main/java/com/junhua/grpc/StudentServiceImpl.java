package com.junhua.grpc;

import com.junhua.proto.Request;
import com.junhua.proto.Response;
import com.junhua.proto.StreamRequest;
import com.junhua.proto.StreamResponse;
import com.junhua.proto.StudentProto;
import com.junhua.proto.StudentRequest;
import com.junhua.proto.StudentResponse;
import com.junhua.proto.StudentResponseList;
import com.junhua.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 5:15 PM
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

  @Override
  public void getRealNameByUsername(Request request, StreamObserver<Response> responseObserver) {
    System.out.println("rec message: " + request.getUsername());
    responseObserver.onNext(Response.newBuilder().setRealname("real name").build());
    responseObserver.onCompleted();
  }


  @Override
  public void getStudentsByAge(StudentRequest request,
      StreamObserver<StudentResponse> responseObserver) {
    System.out.println("rec message:" + request.getAge());
    responseObserver.onNext(StudentResponse.newBuilder().setName("zs").setAge(10).setCity("a").build());
    responseObserver.onNext(StudentResponse.newBuilder().setName("zs1").setAge(20).setCity("a1").build());
    responseObserver.onNext(StudentResponse.newBuilder().setName("z2").setAge(30).setCity("a2").build());
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<StudentRequest> getStudentsWrapperByAge(
      StreamObserver<StudentResponseList> responseObserver) {
    StudentResponseList.Builder studentResponseListBulider = StudentResponseList.newBuilder();

    return new StreamObserver<StudentRequest>(){

      @Override
      public void onNext(StudentRequest value) {
        System.out.println("request : +" + value.getAge());
        StudentResponse studentResponse = StudentResponse.newBuilder()
            .setName("h1").setAge(value.getAge()).setCity("w1").build();

        studentResponseListBulider.addStudentResponse(studentResponse);

      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onCompleted() {
        responseObserver.onNext(studentResponseListBulider.build());
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
    return new StreamObserver<StreamRequest>() {
      @Override
      public void onNext(StreamRequest value) {
        System.out.println("request: " + value);
        responseObserver.onNext(StreamResponse.newBuilder().setResponseInfor("hehehe").build());
      }

      @Override
      public void onError(Throwable t) {

      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();

      }
    };
  }
}
