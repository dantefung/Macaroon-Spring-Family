package com.dantefung.springbootbatchtask.batch;

import com.dantefung.springbootbatchtask.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor
    implements ItemProcessor<Person, Person> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(PersonItemProcessor.class);

  @Override
  public Person process(Person person) throws Exception {
    Person result = new Person();
    result.setFirstName(person.getFirstName().toUpperCase());
    result.setLastName(person.getLastName().toUpperCase());

    LOGGER.info("converting '{}' into '{}'", person, result);
    return result;
  }
}