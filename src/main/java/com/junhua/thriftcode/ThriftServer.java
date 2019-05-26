package com.junhua.thriftcode;

import com.junhua.thrift.generated.PersonService;
import com.junhua.thrift.generated.PersonService.Processor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 2:16 PM
 */
public class ThriftServer {

  public static void main(String[] args) throws TTransportException {
    TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(8899);
    THsHaServer.Args args1 = new Args(serverSocket).minWorkerThreads(2)
        .maxWorkerThreads(4);
    PersonService.Processor<PersonServiceImpl> processor = new Processor<>(new PersonServiceImpl());

    args1.protocolFactory(new TCompactProtocol.Factory());
    args1.transportFactory(new TFastFramedTransport.Factory());
    args1.processorFactory(new TProcessorFactory(processor));

    TServer server = new THsHaServer(args1);

    server.serve();
  }

}
