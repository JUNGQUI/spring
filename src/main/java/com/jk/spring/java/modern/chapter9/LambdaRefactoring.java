package com.jk.spring.java.modern.chapter9;

public class LambdaRefactoring {
  public void refactoringByLambda() {
    int a = 10;
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        int a = 2;
        System.out.println(this);
        System.out.println(a);
      }
    };

    Runnable r2 = () -> {
//      int a = 2;  // already defined error 발생
      System.out.println(this);
      System.out.println(a);
    };

    r1.run();
    r2.run();
  }

  public void becarefulLambdaRefactoring() {
    // doSomething(() -> System.out.println("J Tag")); // Ambiguous method call, 중복 메소드 콜
  }

  private static void doSomething(Runnable r) {
    r.run();
  }

  private static void doSomething(Task t) {
    t.execute();
  }
}
