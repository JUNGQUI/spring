package com.jk.spring.java.modern.chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReduceProblem {

  private final Trader raoul = new Trader("Raoul", "Cambridge");
  private final Trader mario = new Trader("Mario", "Cambridge");
  private final Trader alan = new Trader("Alan", "Milan");
  private final Trader brian = new Trader("Brian", "Cambridge");

  private final List<Transaction> transactions = Arrays.asList(
      new Transaction(brian, 2011, 300),
      new Transaction(raoul, 2012, 1000),
      new Transaction(raoul, 2011, 400),
      new Transaction(mario, 2012, 710),
      new Transaction(mario, 2012, 700),
      new Transaction(alan, 2012, 950)
  );

  @Test
  void transactionASC() {
    List<Transaction> orderedList = transactions.stream()
        .filter(t -> t.getYear() == 2011)
        .sorted(Comparator.comparing(Transaction::getValue))
//        .sorted(new Comparator<Transaction>() {
//          @Override
//          public int compare(Transaction t1, Transaction t2) {
////            return t1.getValue() == t2.getValue() ? 0 : t1.getValue() > t2.getValue() ? 1 : -1;
//            return Integer.compare(t1.getValue(), t2.getValue());
//          }
//        })
        .collect(Collectors.toList());

    List<Transaction> orderedListManual = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2011, 400)
    );

    for (int i = 0; i < orderedList.size(); i++) {
      Assertions.assertEquals(orderedList.get(i), orderedListManual.get(i));
    }
  }

  @Test
  void distinctWorkerCity() {

  }

  @Test
  void cambridgeWorkerNameASC() {

  }

  @Test
  void traderAlphabetASC() {

  }

  @Test
  void isMilanoTraderExist() {

  }

  @Test
  void cambridgeLiveTradersTransaction() {

  }

  @Test
  void transactionMax() {

  }

  @Test
  void transactionMin() {

  }
}
