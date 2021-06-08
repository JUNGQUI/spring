package com.jk.spring.java.modern.chapter8;

import static org.junit.jupiter.api.Assertions.*;

import com.jk.spring.java.modern.chapter5.Transaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionFactoryTest {

  @Test
  void removeIteratorTest() {
    List<Transaction> result = CollectionFactory.removeIterate();
    
    result.forEach(transaction -> assertNotEquals("c2", transaction.getTrader().getCity()));
  }
}