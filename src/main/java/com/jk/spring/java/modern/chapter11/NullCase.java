package com.jk.spring.java.modern.chapter11;

import java.util.Optional;
import java.util.Properties;

public class NullCase {
//  public String canBeNull(Person person) {
//    return person.getCar().getInsurance().getName();
//  }

//  public String cannotBeNull(Person person) {
//    if (person != null) {
//      Car car = person.getCar();
//
//      if (car != null) {
//        Insurance insurance = car.getInsurance();
//
//        if (insurance != null) {
//          return insurance.getName();
//        }
//      }
//    }
//
//    return "Unknown";
//  }

  public String cannotBeNullOptional(Optional<Person> person) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }

  public Insurance findInsurance(Optional<Person> person, String insuranceName) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .filter(insurance -> insurance.getName().equals(insuranceName))
        .orElse(null);
  }
  
  public int readDuration(Properties properties, String name) {
    return Optional.ofNullable(properties.getProperty(name))
        .map(Integer::parseInt)
        .filter(i -> i > 0)
        .orElse(0);
  }
}
