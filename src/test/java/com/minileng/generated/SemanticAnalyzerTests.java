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
    LoggerUtils.addAppender(stdOut, "SemanticAnalyzerTests");
    ParserInitializer.init();
  }

  @Before
  public void setStreams() {
    stdOut.reset();
  }

  @Test
  public void symbolAlreadyDeclared() throws ParseException {
    String p = "programa t; entero a; entero a; principio ; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 30): Simbolo ya declarado previamente: \"a\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void symbolAlreadyDeclaredFunction() throws ParseException {
    String p = "programa t; entero a; accion a; principio ; fin principio ; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 30): Simbolo ya declarado previamente: \"a\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void divisionByZeroComplexExpression() throws ParseException {
    String p = "programa t; entero a; principio a := 0 / (a * 0 / a * a * 3); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): Division por cero\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void divisionByZeroSimpleExpression() throws ParseException {
    String p = "programa t; entero a; principio a := 1 / 0; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): Division por cero\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatTypesMustBeTheRequiredOnes() throws ParseException {
    String p = "programa t; entero a; principio a := 0 div (not ((3 + 3) and False or (3 + 1))); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(1, 58): Los operandos del operador \"and\" han de ser del tipo BOOL\n"
            + "ERROR SEMÁNTICO(1, 68): Los operandos del operador \"or\" han de ser del tipo BOOL\n"
            + "ERROR SEMÁNTICO(1, 40): Los operandos del operador \"div\" han de ser del tipo INT\n";
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
    String p = "programa t; entero a, b, c; principio a := a - b * b / (3 - 42 * (1 + 2 * d - e)); a := d; fin";
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
    String p = "programa t; caracter a, b, c; principio a := entacar(4 * 100 + 20); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 46): El argumento de 'entacar' ha de tener un "
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
    String p = "programa t; entero arg; accion f(val entero a); principio ; fin principio f(arg, 0, 1, 2); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 75): Número de argumentos (4) es distinto al número de parámetros (1) para la acción: \"f\"\n";
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

  @Test
  public void callingAnActionIsFine() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a, b); principio ; fin principio f(2, 3); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void cantAssignToANonVariable() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a, b); principio ; fin principio f := arg; t := arg; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(1, 78): El simbolo no es una variable o un parámetro calificado con \"ref\": \"f\"\n"
            + "ERROR SEMÁNTICO(1, 88): El simbolo no es una variable o un parámetro calificado con \"ref\": \"t\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void expressionsCantWorkWithNonVariablesOperands() throws ParseException {
    String p = "programa t; entero arg; accion f(val entero a, b); principio ; fin principio arg := 3 * f; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 89): El simbolo no es una variable o un parámetro: \"f\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatLeerCantWorkWithRvalues() throws ParseException {
    String p = "programa t;\n"
        + "  accion f0(val entero a);\n"
        + "  principio\n"
        + "   ; \n"
        + "  fin\n"
        + "\n"
        + "  accion f1(val entero a);\n"
        + "  principio\n"
        + "    leer(f0, a, 3);\n"
        + "  fin\n"
        + "\n"
        + "  principio\n"
        + "    ;\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SINTÁCTICO(9, 17): Encontrado \"<tKENTERO>:=3\". Se esperaba: <tID>.\n"
        + "ERROR SEMÁNTICO(9, 10): El simbolo no es una variable o un parámetro calificado con \"ref\": \"f0\"\n"
        + "ERROR SEMÁNTICO(9, 14): El simbolo no es una variable o un parámetro calificado con \"ref\": \"a\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatCaraentArgMustBeOfTypeChar1() throws ParseException {
    String p = "programa t; entero a; principio a := caraent(32); a := caraent(\"a\"); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 38): El argumento de 'caraent' ha de ser del tipo caracter\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatCaraentArgMustBeOfTypeChar2() throws ParseException {
    String p = "programa t; entero a; principio a := caraent(2 * 42 / 2); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 38): El argumento de 'caraent' ha de ser del tipo caracter\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatEntacarArgMustBeOfTypeInt1() throws ParseException {
    String p = "programa t; caracter a; principio a := entacar(\"a\"); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 40): El argumento de 'entacar' ha de ser del tipo entero\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatYouCanOnlyUseSomeOperatorsOnBooleans() throws ParseException {
    String p = "programa t;\n"
        + "  booleano a;\n"
        + "  principio\n"
        + "    a := True > False;\n"
        + "    a := True >= False;\n"
        + "    a := True < False;\n"
        + "    a := True <= False;\n"
        + "    a := True = False;\n"
        + "    a := True <> False;\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(4, 15): No se puede aplicar el operador \">\" a operandos del tipo booleano. Aplicar solo \"<>\" y \"=\"\n"
            + "ERROR SEMÁNTICO(5, 15): No se puede aplicar el operador \">=\" a operandos del tipo booleano. Aplicar solo \"<>\" y \"=\"\n"
            + "ERROR SEMÁNTICO(6, 15): No se puede aplicar el operador \"<\" a operandos del tipo booleano. Aplicar solo \"<>\" y \"=\"\n"
            + "ERROR SEMÁNTICO(7, 15): No se puede aplicar el operador \"<=\" a operandos del tipo booleano. Aplicar solo \"<>\" y \"=\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatYouCantPassAnRValueArgToRefQualifiedParam() throws ParseException {
    String p = "programa t;\n"
        + "  entero a;\n"
        + "\n"
        + "  accion f0(val entero a, b; ref entero c, d);\n"
        + "  principio\n"
        + "  ;\n"
        + "  fin\n"
        + "\n"
        + "  accion f1(val entero a, b; ref entero c, d);\n"
        + "  principio\n"
        + "    f0(0, a, a, b);\n"
        + "  fin\n"
        + "\n"
        + "  principio\n"
        + "    a := 42;\n"
        + "    f1(0, 1, a, 42);\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(11, 14): No se puede pasar un rvalue a un parametero calificado con \"ref\"\n"
            + "ERROR SEMÁNTICO(11, 17): No se puede pasar un rvalue a un parametero calificado con \"ref\"\n"
            + "ERROR SEMÁNTICO(16, 17): No se puede pasar un rvalue a un parametero calificado con \"ref\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatTypesOfArgsAndParamsMustBeTheSame() throws ParseException {
    String p = "programa t;\n"
        + "\n"
        + "  accion f(val entero a; val booleano b; val caracter c);\n"
        + "  principio\n"
        + "    ;\n"
        + "  fin\n"
        + "\n"
        + "  principio\n"
        + "    f(\"L\", 42, True);\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(9, 7): Se esperaba un argumento del tipo INT, pero se ha obtenido uno del tipo CHAR\n"
            + "ERROR SEMÁNTICO(9, 12): Se esperaba un argumento del tipo BOOL, pero se ha obtenido uno del tipo INT\n"
            + "ERROR SEMÁNTICO(9, 16): Se esperaba un argumento del tipo CHAR, pero se ha obtenido uno del tipo BOOL\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatLhsAndRhsTypesMustBeTheSame() throws ParseException {
    String p = "programa t;\n"
        + "  entero a;\n"
        + "  booleano b;\n"
        + "  caracter c;\n"
        + "\n"
        + "  principio\n"
        + "    a := \"L\";\n"
        + "    b := 42;\n"
        + "    c := True;\n"
        + "    a := c;\n"
        + "    b := a;\n"
        + "    c := b;\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(7, 7): Los operandos del operador \":=\" han de ser del tipo INT\n"
            + "ERROR SEMÁNTICO(8, 7): Los operandos del operador \":=\" han de ser del tipo BOOL\n"
            + "ERROR SEMÁNTICO(9, 7): Los operandos del operador \":=\" han de ser del tipo CHAR\n"
            + "ERROR SEMÁNTICO(10, 7): Los operandos del operador \":=\" han de ser del tipo INT\n"
            + "ERROR SEMÁNTICO(11, 7): Los operandos del operador \":=\" han de ser del tipo BOOL\n"
            + "ERROR SEMÁNTICO(12, 7): Los operandos del operador \":=\" han de ser del tipo CHAR\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  @Test
  public void thatYouCantAssignToAValQualParameter() throws ParseException {
    String p = "programa t;\n"
        + "\n"
        + "  accion f(val entero a, b);\n"
        + "  principio\n"
        + "    a := 4 * 10 + 2;\n"
        + "    b := 2;\n"
        + "  fin\n"
        + "\n"
        + "  principio\n"
        + "    ;\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected =
        "ERROR SEMÁNTICO(5, 5): El simbolo no es una variable o un parámetro calificado con \"ref\": \"a\"\n"
            + "ERROR SEMÁNTICO(6, 5): El simbolo no es una variable o un parámetro calificado con \"ref\": \"b\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

  // If an action symbol is undefined a dummy action symbol is inserted with dummy parameters.
  // The number of parameters of this dummy is the same as the number of arguments first encountered.
  @Test
  public void thatIfActionSymUndThenADummySymIsInserted() throws ParseException {
    String p = "programa t; booleano arg; principio f(1, 2); f(3, 4, 3); fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "ERROR SEMÁNTICO(1, 37): Simbolo no declarado: \"f\"\n"
        + "ERROR SEMÁNTICO(1, 46): Número de argumentos (3) es distinto al número de parámetros (2) para la acción: \"f\"\n";
    Assert.assertEquals(expected, stdOut.toString());
  }

}
