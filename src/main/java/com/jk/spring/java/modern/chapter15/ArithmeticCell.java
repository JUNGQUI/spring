package com.jk.spring.java.modern.chapter15;

public class ArithmeticCell extends SimpleCell {
  private int left;
  private int right;

  public ArithmeticCell(String namaste) {
    // conflict 유발
    super(namaste);
    // conflict 유발
  }

  public void setLeft(int left) {
    this.left = left;
    // conflict 유발
    onNext(left + this.right);
  }

  public void setRight(int right) {
    this.right = right;
    onNext(this.left + right);
  }
  // conflict 유발
}
