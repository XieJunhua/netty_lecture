package com.junhua.thriftcode;

import com.junhua.thrift.generated.DataException;
import com.junhua.thrift.generated.Person;
import com.junhua.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @author junhua xjhclks@163.com
 * @date 2019/5/26 2:15 PM
 */
public class PersonServiceImpl implements PersonService.Iface {

  @Override
  public Person getPersonByUsername(String username) throws DataException, TException {
    System.out.println("ddd");
    Person person = new Person();
    person.setUsername("dd");
    person.setAge(11);
    person.setMarried(false);
    System.out.println("query result is : " + person);
    return person;
  }

  @Override
  public void savePerson(Person person) throws DataException, TException {
    System.out.println("save to db: " + person);
  }
}
