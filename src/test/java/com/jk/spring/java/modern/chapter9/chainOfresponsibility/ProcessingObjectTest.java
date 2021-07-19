package com.jk.spring.java.modern.chapter9.chainOfresponsibility;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.Test;

class ProcessingObjectTest {
  @Test
  void processingObject() {
    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();
    p1.setSuccessor(p2);

    String testText = "Aren't labdas really hot?!";
    String result = p1.handle(testText);

    assertEquals(
        "From Raoul, Mario and Alan: " + testText.replace("labda", "lambda")
        , result);

    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replace("labda", "lambda");
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
    String resultWithLambda = pipeline.apply(testText);

    assertEquals(result, resultWithLambda);
  }
}