package com.jk.spring.java.modern.chapter9.templateMethod;

import java.util.function.Consumer;

abstract class OnlineBanking {

  public void processCustomer(int id) {
    Customer c = getCustomerFromDB(id);
    makeCustomerHappy(c);
  }
  public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
    Customer c = getCustomerFromDB(id);
    makeCustomerHappy.accept(c);
  }
  abstract void makeCustomerHappy(Customer c);

  private Customer getCustomerFromDB(int id) {
    return new Customer(123, "쿠이정", "jklee", "jkleePassword");
  }
}
