package com.jk.spring.java.modern.chapter13;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SuperNumClassTest {

  @Test
  public void superSubTest() {
    SubNumClass subNumClass = new SubNumClass();
    SuperNumClass superNumClass = new SuperNumClass();

    int superNum = 5;
    int subNum = 3;
    int totalNum = superNum + subNum;

    superNumClass.setSuperNum(superNum);
    subNumClass.setSuperNum(superNum);
    subNumClass.setSubNum(subNum);

    assertEquals(totalNum, subNumClass.getSumOfNum());
    assertEquals(subNumClass.getSuperNum(), superNumClass.getSuperNum());
  }
}