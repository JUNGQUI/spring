package com.jk.spring.java.modern.chapter9.observer;

public interface Subject {
  void registerObserver(Observer o);
  void notifyObservers(String tweet);

}
