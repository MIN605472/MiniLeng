package com.minileng.generated;

import com.minileng.utils.LoggerUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SemanticAnalyzerTests {

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

  @Test
  public void symbolAlreadyDeclared() throws ParseException {
    String p = "programa t; entero a; entero a; principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 30): Simbolo ya declarado previamente: \"a\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void symbolAlreadyDeclaredFunction() throws ParseException {
    String p = "programa t; entero a; accion a; principio fin principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 30): Simbolo ya declarado previamente: \"a\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  // TODO: finish
  @Ignore
  public void divisionByZero() throws ParseException {
    String p = "programa t; entero a; principio a := 0 / 0; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
  }

  // TODO: finish
  @Ignore
  public void moduloZero() throws ParseException {
    String p = "programa t; entero a; principio a := 1 mod 0; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
  }

  @Test
  public void undefinedVariableSymbols() throws ParseException {
    String p = "programa t; principio leer(a, b, c); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 28): Simbolo no declarado: \"a\"\n"
        + "ERROR SEMÁNTICO(1, 31): Simbolo no declarado: \"b\"\n"
        + "ERROR SEMÁNTICO(1, 34): Simbolo no declarado: \"c\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void undefinedVariableSymbolsInComplexStatements() throws ParseException {
    String p = "programa t; entero a, b, c; principio a := a - b * b / (3 - 42 * (1 + 2 * d - e)); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 75): Simbolo no declarado: \"d\"\n"
        + "ERROR SEMÁNTICO(1, 79): Simbolo no declarado: \"e\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void undefinedActionSymbolsInComplexStatements() throws ParseException {
    String p = "programa t; entero a, b, c; principio f(a); g(b); h(c); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 39): Simbolo no declarado: \"f\"\n"
        + "ERROR SEMÁNTICO(1, 45): Simbolo no declarado: \"g\"\n"
        + "ERROR SEMÁNTICO(1, 51): Simbolo no declarado: \"h\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  // TODO: finish
  @Ignore
  public void entacarValueMustBeBetween0and255() throws ParseException {
    String p = "programa t; entero a, b, c; principio entacar(420); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();

  }

  // TODO: finish
  @Ignore
  public void missingParameterType() throws ParseException {
    String p = "programa t; accion dato (val d); principio fin principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
  }

}
