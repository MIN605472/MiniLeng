package com.minileng.generated;

import com.minileng.utils.LoggerUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SyntacticAnalyzerTests {

  private static final String VALID_SYNTAX_FILES[] = new String[]{
      "/programs/source/adivinar1.ml",
      "/programs/source/calendar1.ml",
      "/programs/source/fib.ml",
      "/programs/source/mcd1.ml"
  };

  private static ByteArrayOutputStream stdOut = new ByteArrayOutputStream();

  @BeforeClass
  public static void initParser() {
    LoggerUtils.addAppender(stdOut, "test");
    new MiniLeng(new ByteArrayInputStream(new byte[]{}));
  }


  @Before
  public void setStreams() {
    stdOut.reset();
  }

  /**
   * Test that no error is reported for some valid input files.
   */
  @Test
  public void thatThereIsNoErrorsWhenSyntaxIsValid() {
    for (String f : VALID_SYNTAX_FILES) {
      try {
        MiniLeng.ReInit(new FileReader(SyntacticAnalyzerTests.class.getResource(f).getFile()));
        MiniLeng.programa();
      } catch (FileNotFoundException e) {
        Assert.fail("File not found: " + f);
      } catch (ParseException e) {
        // The exception will never be thrown.
        Assert.fail("Invalid syntax.");
      }
    }
    Assert.assertTrue(true);
  }

  /**
   * Test that the basic semantic error reporting works.
   */
  @Test
  public void idCantStartWithNumber() throws ParseException {
    String p = "programa t; entero 42id; principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SINTÁCTICO(1, 20): Encontrado \"<tKENTERO>:=42\". Se esperaba: <tID>.\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  /**
   * Test that panic mode error recovery works and that it doesn't just report the first error.
   */
  @Test
  public void reportThreeSemanticErrors() throws ParseException {
    String p = "programa t; entero 42id; entero 43id; entero 44id; principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput =
        "ERROR SINTÁCTICO(1, 20): Encontrado \"<tKENTERO>:=42\". Se esperaba: <tID>.\n"
            + "ERROR SINTÁCTICO(1, 33): Encontrado \"<tKENTERO>:=43\". Se esperaba: <tID>.\n"
            + "ERROR SINTÁCTICO(1, 46): Encontrado \"<tKENTERO>:=44\". Se esperaba: <tID>.\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

}
