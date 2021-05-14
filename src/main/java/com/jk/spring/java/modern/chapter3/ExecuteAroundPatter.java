package com.jk.spring.java.modern.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPatter {
  public String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
      return p.process(br);
    }
  }

  public void usingWithLambda() throws IOException {
    String oneLine = processFile(BufferedReader::readLine);
    String secondLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());

    System.out.println(oneLine);
    System.out.println(secondLine);
  }
}
