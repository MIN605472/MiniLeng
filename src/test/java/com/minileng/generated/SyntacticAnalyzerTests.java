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

  // Not a very succinct error message, but whatevs
  @Test
  public void rando() throws ParseException {
    String p = "programa t; entero a, b, c; principio a := a - b * b / (3 - 42 * (1 + 2 * fun(pi) - e)); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "ERROR SEMÁNTICO(1, 75): Simbolo no declarado: \"fun\"\n"
        + "ERROR SINTÁCTICO(1, 78): Encontrado \"<tPAR_IZQ>:=(\". Se esperaba uno de: <tMAS>, <tMENOS>, <tMUL>, <tDIVCHAR>, <tDIV>, <tMOD>, <tMAYOR>, <tMENOR>, <tIGUAL>, <tMAI>, <tMEI>, <tNI>, <tAND>, <tOR>, <tPAR_DCHA>.\n"
        + "ERROR SINTÁCTICO(1, 79): Encontrado \"<tID>:=pi\". Se esperaba uno de: <tMAS>, <tMENOS>, <tMUL>, <tDIVCHAR>, <tDIV>, <tMOD>, <tMAYOR>, <tMENOR>, <tIGUAL>, <tMAI>, <tMEI>, <tNI>, <tAND>, <tOR>, <tPAR_DCHA>.\n"
        + "ERROR SINTÁCTICO(1, 81): Encontrado \"<tPAR_DCHA>:=)\". Se esperaba uno de: <tMAS>, <tMENOS>, <tMUL>, <tDIVCHAR>, <tDIV>, <tMOD>, <tMAYOR>, <tMENOR>, <tIGUAL>, <tMAI>, <tMEI>, <tNI>, <tAND>, <tOR>, <tPUNTOCOMA>.\n"
        + "ERROR SEMÁNTICO(1, 85): Simbolo no declarado: \"e\"\n"
        + "ERROR SINTÁCTICO(1, 86): Encontrado \"<tPAR_DCHA>:=)\". Se esperaba uno de: <tOPAS>, <tPUNTOCOMA>, <tPAR_IZQ>.\n";
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
    String p = "programa t; entero arg; accion fun(val entero a); principio fin principio fun(\"Hola mundo!\"); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput =
        "ERROR SINTÁCTICO(1, 79): Encontrado \"<tKCADENA>:=\\\"Hola mundo!\\\"\". Se esperaba uno de: <tMAS>, <tMENOS>, <tNOT>, <tENTACAR>, <tCARAENT>, <tTRUE>, <tFALSE>, <tKENTERO>, <tKCARACTER>, <tPAR_DCHA>, <tPAR_IZQ>, <tID>.\n"
            + "ERROR SEMÁNTICO(1, 75): Número de argumentos (0) es distinto al número de parámetros (1) para la acción: \"fun\"\n";
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

}
