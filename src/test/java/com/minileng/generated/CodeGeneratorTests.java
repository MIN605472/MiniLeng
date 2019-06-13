package com.minileng.generated;

import com.minileng.utils.LoggerUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodeGeneratorTests {

  private static ByteArrayOutputStream stdOut = new ByteArrayOutputStream();

  @BeforeClass
  public static void initParser() {
    LoggerUtils.addAppender(stdOut, "CodeGeneratorTests");
    ParserInitializer.init();
  }

  @Test
  public void thatGivenProgram1IsFine() throws ParseException {
    String p = "programa max; entero i, j; principio leer(i); leer(j); si i > j ent escribir(i); si_no escribir(j); fsi fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSRF 0 3\n"
        + "\tRD 1\n"
        + "\tSRF 0 4\n"
        + "\tRD 1\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tGT\n"
        + "\tJMF L1\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "\tJMP L2\n"
        + "L1:\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "L2:\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void thatAssignmentIsFine() throws ParseException {
    String p = "programa max; entero i; entero j; principio i := j; fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void thatComplexExpressionsAreFine() throws ParseException {
    String p = "programa max; entero i; entero j; principio i := j * j - 3 * 5 + 1 / (3 + 6); fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tTMS\n"
        + "\tSTC 3\n"
        + "\tSTC 5\n"
        + "\tTMS\n"
        + "\tSBT\n"
        + "\tSTC 1\n"
        + "\tSTC 3\n"
        + "\tSTC 6\n"
        + "\tPLUS\n"
        + "\tDIV\n"
        + "\tPLUS\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  // Boolean expressions are not short circuited for now.
  @Test
  public void thatBoolExpIsFine() throws ParseException {
    String p = "programa max; booleano i; entero j; principio i := (false and (true or true)) or (not false); fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSTC 0\n"
        + "\tSTC 1\n"
        + "\tSTC 1\n"
        + "\tOR\n"
        + "\tAND\n"
        + "\tSTC 0\n"
        + "\tNGB\n"
        + "\tOR\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void thatLoopsAreFine() throws ParseException {
    String p = "programa max; entero i; entero j; principio j := 1; mq j <= 10 i := i + j; j := j + 1;fmq fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSTC 1\n"
        + "\tSRF 0 4\n"
        + "\tASGI\n"
        + "L1:\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tSTC 10\n"
        + "\tLTE\n"
        + "\tJMF L2\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tPLUS\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tSTC 1\n"
        + "\tPLUS\n"
        + "\tSRF 0 4\n"
        + "\tASGI\n"
        + "\tJMP L1\n"
        + "L2:\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void thatSimpleFunctionCallsAreFine() throws ParseException {
    String p = "programa p;\n"
        + "\n"
        + "accion max(val entero a);\n"
        + "  entero i, j;\n"
        + "  principio \n"
        + "    leer(i);\n"
        + "    leer(j);\n"
        + "    si i > j ent\n"
        + "      escribir(i);\n"
        + "    si_no\n"
        + "      escribir(j);\n"
        + "    fsi\n"
        + "  fin\n"
        + "\n"
        + "principio\n"
        + "  max(3);\n"
        + "fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "; Accion: max\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tJMP L1\n"
        + "L1:\n"
        + "\tSRF 0 4\n"
        + "\tRD 1\n"
        + "\tSRF 0 5\n"
        + "\tRD 1\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tSRF 0 5\n"
        + "\tDRF\n"
        + "\tGT\n"
        + "\tJMF L2\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "\tJMP L3\n"
        + "L2:\n"
        + "\tSRF 0 5\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "L3:\n"
        + "\tCSF\n"
        + "L0:\n"
        + "\tSTC 3\n"
        + "\tOSF 3 0 1\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void thatGivenProgram2IsFine() throws ParseException {
    String p = "programa p;\n"
        + "  entero i, j;\n"
        + "\n"
        + "  accion q(ref entero m);\n"
        + "  principio\n"
        + "    escribir(m);\n"
        + "    m := 0;\n"
        + "  fin\n"
        + "\n"
        + "  accion r(val entero k; ref entero l);\n"
        + "  principio\n"
        + "    escribir(k, l);\n"
        + "    l := 0;\n"
        + "    q(l);\n"
        + "    q(l);\n"
        + "  fin\n"
        + "\n"
        + "principio\n"
        + "  r(i, j);\n"
        + "fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "; Accion: q\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tJMP L1\n"
        + "L1:\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "\tSTC 0\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tASGI\n"
        + "\tCSF\n"
        + "; Accion: r\n"
        + "\tSRF 0 4\n"
        + "\tASGI\n"
        + "\tSRF 0 3\n"
        + "\tASGI\n"
        + "\tJMP L2\n"
        + "L2:\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tDRF\n"
        + "\tWRT 1\n"
        + "\tSTC 0\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tASGI\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tOSF 5 1 1\n"
        + "\tSRF 0 4\n"
        + "\tDRF\n"
        + "\tOSF 5 1 1\n"
        + "\tCSF\n"
        + "L0:\n"
        + "\tSRF 0 3\n"
        + "\tDRF\n"
        + "\tSRF 0 4\n"
        + "\tOSF 5 0 13\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }

  @Test
  public void whatWhenABooleanIsWrittenTheirStringRepresentationIsWritten() throws ParseException {
    String p = "programa t;\n"
        + "\n"
        + "  principio\n"
        + "    escribir(True);\n"
        + "    escribir(False);\n"
        + "  fin\n";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expected = "\tENP L0\n"
        + "L0:\n"
        + "\tSTC 1\n"
        + "\tJMF L1\n"
        + "\tSTC 86\n"
        + "\tWRT 0\n"
        + "\tSTC 101\n"
        + "\tWRT 0\n"
        + "\tSTC 114\n"
        + "\tWRT 0\n"
        + "\tSTC 100\n"
        + "\tWRT 0\n"
        + "\tSTC 97\n"
        + "\tWRT 0\n"
        + "\tSTC 100\n"
        + "\tWRT 0\n"
        + "\tSTC 101\n"
        + "\tWRT 0\n"
        + "\tSTC 114\n"
        + "\tWRT 0\n"
        + "\tSTC 111\n"
        + "\tWRT 0\n"
        + "\tJMP L2\n"
        + "L1:\n"
        + "\tSTC 70\n"
        + "\tWRT 0\n"
        + "\tSTC 97\n"
        + "\tWRT 0\n"
        + "\tSTC 108\n"
        + "\tWRT 0\n"
        + "\tSTC 115\n"
        + "\tWRT 0\n"
        + "\tSTC 111\n"
        + "\tWRT 0\n"
        + "L2:\n"
        + "\tSTC 0\n"
        + "\tJMF L3\n"
        + "\tSTC 86\n"
        + "\tWRT 0\n"
        + "\tSTC 101\n"
        + "\tWRT 0\n"
        + "\tSTC 114\n"
        + "\tWRT 0\n"
        + "\tSTC 100\n"
        + "\tWRT 0\n"
        + "\tSTC 97\n"
        + "\tWRT 0\n"
        + "\tSTC 100\n"
        + "\tWRT 0\n"
        + "\tSTC 101\n"
        + "\tWRT 0\n"
        + "\tSTC 114\n"
        + "\tWRT 0\n"
        + "\tSTC 111\n"
        + "\tWRT 0\n"
        + "\tJMP L4\n"
        + "L3:\n"
        + "\tSTC 70\n"
        + "\tWRT 0\n"
        + "\tSTC 97\n"
        + "\tWRT 0\n"
        + "\tSTC 108\n"
        + "\tWRT 0\n"
        + "\tSTC 115\n"
        + "\tWRT 0\n"
        + "\tSTC 111\n"
        + "\tWRT 0\n"
        + "L4:\n"
        + "\tLVP\n";
    Assert.assertEquals(expected, MiniLeng.getCodeAsString());
  }


}
