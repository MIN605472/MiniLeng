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
import org.junit.Ignore;
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
    LoggerUtils.addAppender(stdOut, "SyntacticAnalyzerTests");
    ParserInitializer.init();
  }


  @Before
  public void setStreams() {
    stdOut.reset();
  }

  /**
   * Test that no error is reported for some valid input files.
   */
  @Test
  public void thatThereIsNoErrorsWhenSyntaxIsValid() throws ParseException, FileNotFoundException {
    for (String f : VALID_SYNTAX_FILES) {
        MiniLeng.ReInit(new FileReader(SyntacticAnalyzerTests.class.getResource(f).getFile()));
        MiniLeng.programa();
        Assert.assertEquals("", stdOut.toString());
    }
  }

  /**
   * Test that the basic semantic error reporting works.
   */
  @Test
  public void idCantStartWithNumber() throws ParseException {
    String p = "programa t; entero 42id; principio ; fin";
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
    String p = "programa t; entero 42id; entero 43id; entero 44id; principio ; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput =
        "ERROR SINTÁCTICO(1, 20): Encontrado \"<tKENTERO>:=42\". Se esperaba: <tID>.\n"
            + "ERROR SINTÁCTICO(1, 33): Encontrado \"<tKENTERO>:=43\". Se esperaba: <tID>.\n"
            + "ERROR SINTÁCTICO(1, 46): Encontrado \"<tKENTERO>:=44\". Se esperaba: <tID>.\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  // TODO: deep nesting expressions makes the compiler report many syntactic error at the same place,
  //  I think it's normal, but I gotta make the message more succinct
  @Ignore
  public void rando() throws ParseException {
    String p = "programa t; entero a, b, c; principio a := ((fun(pi))); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void stringAsArgToEscribirIsFine() throws ParseException {
    String p = "programa t; principio escribir(\"Hola mundo!\"); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  // TODO: it'd be a good idea to create a flag to turn on and off the reporting of semantic errors
  //  so that we have cleaner syntactic tests
  @Test
  public void stringAsArgToAnActionReportsError() throws ParseException {
    String p = "programa t; entero arg; accion fun(val entero a); principio ; fin principio fun(\"Hola mundo!\"); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput =
        "ERROR SINTÁCTICO(1, 81): Encontrado \"<tKCADENA>:=\\\"Hola mundo!\\\"\". Se esperaba uno de: <tMAS>, <tMENOS>, <tNOT>, <tENTACAR>, <tCARAENT>, <tTRUE>, <tFALSE>, <tKENTERO>, <tKCARACTER>, <tPAR_DCHA>, <tPAR_IZQ>, <tID>.\n"
            + "ERROR SEMÁNTICO(1, 77): Número de argumentos (0) es distinto al número de parámetros (1) para la acción: \"fun\"\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void entacarAsOperandIsFine() throws ParseException {
    String p = "programa t; entero a; principio a := entacar(10) + 3; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void singleLineCommentsAreFine() throws ParseException {
    String p = "% Testing comment\nprograma t; entero a; principio a := entacar(10) + 3; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void multilineCommentsAreFine() throws ParseException {
    String p = "%% Comment 1\nComment2%%programa t; entero a; principio a := 3; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void programaMustHaveAtLeastOneStatement() throws ParseException {
    String p = "programa t; entero a; principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "ERROR SINTÁCTICO(1, 33): Encontrado \"<tFIN>:=fin\". Se esperaba uno de: <tSI>, <tMQ>, <tESCRIBIR>, <tLEER>, <tPUNTOCOMA>, <tID>.\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

  @Test
  public void accionMustHaveAtLeastOneStatement() throws ParseException {
    String p = "programa t; entero arg; accion fun(val entero a); principio fin principio arg := 2; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "ERROR SINTÁCTICO(1, 61): Encontrado \"<tFIN>:=fin\". Se esperaba uno de: <tSI>, <tMQ>, <tESCRIBIR>, <tLEER>, <tPUNTOCOMA>, <tID>.\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
  }

}
