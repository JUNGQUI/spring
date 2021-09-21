package com.jk.spring.java.modern.chapter13;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SubNumClass extends SuperNumClass {
  private int subNum;

  public int getSubNum() {
    return this.subNum;
  }

  public void setSubNum(int subNum) {
    this.subNum = subNum;
  }

  public int getSumOfNum() {
    return superNum + subNum;
  }
}
