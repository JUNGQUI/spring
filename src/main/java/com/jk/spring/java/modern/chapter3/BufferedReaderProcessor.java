package com.jk.spring.java.modern.chapter3;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderProcessor {
  String process(BufferedReader b) throws IOException;
}
