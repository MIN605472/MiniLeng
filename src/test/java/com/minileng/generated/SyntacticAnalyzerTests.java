package com.minileng.generated;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Assert;
import org.junit.Test;

public class SyntacticAnalyzerTests {

  private static final String VALID_SYNTAX_FILES[] = new String[]{
      "/programs/source/adivinar1.ml",
      "/programs/source/calendar1.ml",
      "/programs/source/fib.ml",
      "/programs/source/mcd1.ml"
  };

  private static final String INVALID_SYNTAX_FILES[] = new String[]{
      "/programs/source/mcd.ml"
  };

  @Test
  public void thatThereIsNoErrorsWhenSyntaxIsValid() {
    for (String f : VALID_SYNTAX_FILES) {
      try {
        MiniLeng.ReInit(new FileReader(SyntacticAnalyzerTests.class.getResource(f).getFile()));
        MiniLeng.programa();
      } catch (FileNotFoundException e) {
        Assert.fail("No se ha encontrado el fichero de entrada: " + f);
      } catch (ParseException e) {
        Assert.fail("Sintaxis invalida.");
      }
    }
    Assert.assertTrue(true);
  }

  @Test
  public void thatThereIsErrorsWhenSyntaxIsNotValid() {
    for (String f : INVALID_SYNTAX_FILES) {
      try {
        MiniLeng.ReInit(new FileReader(SyntacticAnalyzerTests.class.getResource(f).getFile()));
        MiniLeng.programa();
      } catch (FileNotFoundException e) {
        Assert.fail("No se ha encontrado el fichero de entrada: " + f);
      } catch (ParseException e) {
        continue;
      }
      Assert.fail("Sintaxis valida cuando lo esperado es sintaxis invalida.");
    }
  }

}
