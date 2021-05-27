package com.jk.spring.java.modern.chapter5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("2011년에 일어난 트랜잭션 값 오름차순")
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
  @DisplayName("중복 없이 거래자 근무 도시")
  void distinctWorkerCity() {
    List<String> distinctCity = transactions.stream()
        .map(t -> t.getTrader().getCity())
        .distinct()
        .collect(Collectors.toList());

    List<String> distinctManualCity = Arrays.asList(
        "Cambridge",
        "Milan"
    );

    for (int i = 0; i < distinctCity.size(); i++) {
      Assertions.assertEquals(distinctManualCity.get(i), distinctCity.get(i));
    }
  }

  @Test
  @DisplayName("케임브리지 근무 거래자 이름 오름차순")
  void cambridgeWorkerNameASC() {
    List<Trader> cambridgeWorkerNameASC = transactions.stream()
        .map(Transaction::getTrader)
        .filter(t -> t.getCity().equals("Cambridge"))
        .distinct()
        .sorted(Comparator.comparing(Trader::getName))
        .collect(Collectors.toList());

    List<Trader> cambridgeWorkerNameASCManual = Arrays.asList(
        brian, mario, raoul
    );

    for (int i = 0; i < cambridgeWorkerNameASC.size(); i++) {
      Assertions.assertEquals(cambridgeWorkerNameASC, cambridgeWorkerNameASCManual);
    }
  }

  @Test
  @DisplayName("근무자 알파벳순 정렬")
  void traderAlphabetASC() {
    List<Trader> traderAlphabetASC = transactions.stream()
        .map(Transaction::getTrader)
        .distinct()
        .sorted(Comparator.comparing(Trader::getName))
        .collect(Collectors.toList());

    List<Trader> traderAlphabetASCManual = Arrays.asList(
        alan, brian, mario, raoul
    );

    for (int i = 0; i < traderAlphabetASC.size(); i++) {
      Assertions.assertEquals(traderAlphabetASC, traderAlphabetASCManual);
    }
  }

  @Test
  @DisplayName("밀라노 거래자 유무 확인")
  void isMilanoTraderExist() {
    Trader milanTrader = transactions.stream()
        .map(Transaction::getTrader)
        .filter(t -> t.getCity().equals("Milan"))
        .findAny().orElse(null);
    Assertions.assertNotNull(milanTrader);
  }

  @Test
  @DisplayName("케임브리즈 거주 거래자 트랜잭션")
  void cambridgeLiveTradersTransaction() {
    List<Transaction> cambridgeLiveTradersTransaction = transactions.stream()
        .filter(t -> t.getTrader().getCity().equals("cambridge"))
        .collect(Collectors.toList());

    List<Transaction> cambridgeLiveTradersTransactionManual = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700)
    );

    for (int i = 0; i < cambridgeLiveTradersTransaction.size(); i++) {
      Assertions.assertEquals(cambridgeLiveTradersTransactionManual, cambridgeLiveTradersTransaction);
    }
  }

  @Test
  @DisplayName("전체 트랜잭션 최대값")
  void transactionMax() {
    Transaction maxTransaction = transactions.stream()
        .max(Comparator.comparing(Transaction::getValue))
        .orElse(null);

    Assertions.assertNotNull(maxTransaction);
    Assertions.assertEquals(1000, maxTransaction.getValue());
  }

  @Test
  @DisplayName("전체 트랜잭션 최소값")
  void transactionMin() {
    Transaction minTransaction = transactions.stream()
        .min(Comparator.comparing(Transaction::getValue))
        .orElse(null);

    Assertions.assertNotNull(minTransaction);
    Assertions.assertEquals(300, minTransaction.getValue());
  }
}
