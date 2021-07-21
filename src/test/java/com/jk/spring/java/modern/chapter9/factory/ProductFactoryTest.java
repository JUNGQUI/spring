package com.jk.spring.java.modern.chapter9.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductFactoryTest {

  @Test
  void factoryPatternTest() {
    Loan loan = (Loan) ProductFactory.createProduct(ProductType.LOAN.getName());
    Loan lambdaLoan = (Loan) ProductFactory.createProductWithLambda(ProductType.LOAN.getName());

    assertEquals(loan, lambdaLoan);
  }
}