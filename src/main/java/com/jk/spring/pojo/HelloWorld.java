package com.jk.spring.pojo;

public class HelloWorld {

    String name;
    Printer printer;

    public String sayHello() {
        return "Hello" + name;
    }

    public void print() {
        this.printer.print(sayHello());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
