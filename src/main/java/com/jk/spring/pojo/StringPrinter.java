package com.jk.spring.pojo;

public class StringPrinter implements Printer {

    private StringBuilder stringBuilder = new StringBuilder();

    public void print(String message) {
        this.stringBuilder.append(message);
    }

    public String toString() {
        return this.stringBuilder.toString();
    }
}
