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

  /**
   * Should report a division by 0 error when the divisor is a constant expression, e.g., "3 - 3 + 3
   * -3" or "id * 0 - 3 + 3".
   */
  @Test
  public void divisionByZero() throws ParseException {
    String p = "programa t; entero a; principio a := 0 / (a * 0 / a * a * 3); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): Division por cero\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void divisionByZeroBooleanExpression() throws ParseException {
    String p = "programa t; entero a; principio a := 0 div (not ((3 + 3) and False or (2 > 1))); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): Division por cero\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void moduloZero() throws ParseException {
    String p = "programa t; entero a; principio a := 1 mod (a * 0); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): Modulo cero\n";
    Assert.assertEquals(expected, stdOut.toString());
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

  @Test
  public void entacarArgsMustBeBetween0and255() throws ParseException {
    String p = "programa t; entero a, b, c; principio a := entacar(420); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 44): El argumento de 'entacar' ha de tener un "
        + "valorcomprendido entre 0 y 255, ambos incluidos\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  // TODO: finish
  @Ignore
  public void missingParameterType() throws ParseException {
    String p = "programa t; accion dato (val d); principio fin principio fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
  }

  @Test
  public void numberOfArgumentsAndParametersMismatch() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a); principio fin principio f(arg, 0, 1, 2); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 73): Número de argumentos (4) es distinto al número de parámetros (1) para la acción: \"f\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void notAnAction() throws ParseException {
    String p = "programa t; entero arg; principio arg(0, 1, 2); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 35): El simbolo no es una acción: \"arg\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  // TODO: finish
  @Ignore
  public void typesOfArgumentsAndParametersMismatch() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a); principio fin principio f(arg, 0, 1, 2); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
  }

  @Test
  public void callingAnActionIsFine() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a, b); principio fin principio f(2, 3); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "";
    Assert.assertEquals(expected, stdOut.toString());
  }
}
