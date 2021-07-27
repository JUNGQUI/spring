package com.jk.spring.java.modern.chapter11;

import java.util.Optional;

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
}
