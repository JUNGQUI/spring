package com.jk.spring.pojo;

public class ConsolePrinter implements Printer {

    public void print(String message) {
        System.out.println(message);
    }
}
