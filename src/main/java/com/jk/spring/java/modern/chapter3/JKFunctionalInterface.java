package com.jk.spring.java.modern.chapter3;

import java.util.Comparator;
import java.util.concurrent.Callable;

public class JKFunctionalInterface {

  public static class JKCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
      return "called by JKCallable from JKFunctionalInterface";
    }
  }

  public static class JKRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println("called by JKRunnable from JKFunctionalInterface");
    }
  }

  public static class JKComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  }

  public static class JKComparable implements Comparable<Integer> {
    int number;

    @Override
    public int compareTo(Integer o) {
      if (number > o) {
        return 1;
      }

      if (number < o) {
        return -1;
      }

      return 0;
    }
  }
}
