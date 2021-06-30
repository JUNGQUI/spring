package com.jk.spring.java.modern.chapter8;

import com.jk.spring.java.modern.chapter5.Trader;
import com.jk.spring.java.modern.chapter5.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionFactory {

  public Map<String, String> getFavouriteMovies() {
    return Map.of(
        "Raphael", "Star Wars"
        , "Cristina", "Matrix"
        , "Olivia", "James Bond"
    );
  }

  public List<String> getFriends() {
    return List.of("Raphael", "Leonardo", "Donatello", "Michelangelo");
  }

  public Map<String, Integer> getFriendsWithAge() {
    return Map.of("Raphael", 30
        , "Leonardo", 30
        , "Donatello", 30
        , "Michelangelo", 30);
  }

  public List<Transaction> removeIterate() {
    List<Transaction> transactions = new ArrayList<>(Arrays.asList(
        new Transaction(new Trader("a1", "c1"), 2020, 5000)
        , new Transaction(new Trader("a2", "c2"), 2020, 5000)
        , new Transaction(new Trader("a3", "c3"), 2020, 5000)
        , new Transaction(new Trader("a4", "c2"), 2020, 5000)
        , new Transaction(new Trader("a5", "c1"), 2020, 5000)
        , new Transaction(new Trader("a6", "c3"), 2020, 5000)));

    // concurrentModificationException
//    for (Transaction transaction : transactions) {
//      if("c2".equals(transaction.getTrader().getCity())) {
//        transactions.remove(transaction);
//      }
//    }

    transactions.removeIf(transaction -> "c2".equals(transaction.getTrader().getCity()));

    return transactions;
  }

  public List<Entry<String, String>> sortedASCByEditor() {
    return getFavouriteMovies().entrySet()
        .stream()
        .sorted(Entry.comparingByKey(String::compareTo))
//        .sorted(Entry.comparingByKey((s1, s2) -> {
//          return s1.compareTo(s2);
//        }))
        .collect(Collectors.toList());
  }
}
