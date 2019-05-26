package com.junhua.thriftcode;

import com.junhua.thrift.generated.DataException;
import com.junhua.thrift.generated.Person;
import com.junhua.thrift.generated.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 2:20 PM
 */
public class Client {

  public static void main(String[] args) {
    TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);
    TProtocol protocol = new TCompactProtocol(transport);
    PersonService.Client client = new PersonService.Client(protocol);

    try {
      transport.open();
      Person person = new Person();
      person.setMarried(false);
      person.setAge(11);
      person.setUsername("ddd");
      client.savePerson(person);

      System.out.println(client.getPersonByUsername("ddddd"));


    } catch (TTransportException e) {
      e.printStackTrace();
    } catch (DataException e) {
      e.printStackTrace();
    } catch (TException e) {
      e.printStackTrace();
    } finally {
      transport.close();
    }
  }

}
