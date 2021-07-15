package com.jk.spring.java.modern.chapter9.templateMethod;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  private int id;
  private String name;
  private String loginId;
  private String password;
}
