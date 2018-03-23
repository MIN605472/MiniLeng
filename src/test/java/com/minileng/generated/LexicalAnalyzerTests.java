package com.minileng.generated;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class LexicalAnalyzerTests {

  private final static Map<Integer, String> KIND_NAME = new HashMap<>();

  static {
    KIND_NAME.put(51, "tID");
    KIND_NAME.put(50, "tPROGRAMA");
    KIND_NAME.put(49, "tPAR_IZQ");
    KIND_NAME.put(48, "tPAR_DCHA");
    KIND_NAME.put(47, "tCOMA");
    KIND_NAME.put(46, "tPUNTOCOMA");
    KIND_NAME.put(45, "tKCADENA");
    KIND_NAME.put(44, "tKCARACTER");
    KIND_NAME.put(43, "tKENTERO");
    KIND_NAME.put(42, "tFALSE");
    KIND_NAME.put(41, "tTRUE");
    KIND_NAME.put(40, "tFIN");
    KIND_NAME.put(39, "tPRINCIPIO");
    KIND_NAME.put(38, "tACCION");
    KIND_NAME.put(37, "tREF");
    KIND_NAME.put(36, "tVAL");
    KIND_NAME.put(35, "tLEER");
    KIND_NAME.put(34, "tESCRIBIR");
    KIND_NAME.put(33, "tFMQ");
    KIND_NAME.put(32, "tMQ");
    KIND_NAME.put(31, "tFSI");
    KIND_NAME.put(30, "tSI_NO");
    KIND_NAME.put(29, "tENT");
    KIND_NAME.put(28, "tSI");
    KIND_NAME.put(27, "tCARAENT");
    KIND_NAME.put(26, "tENTACAR");
    KIND_NAME.put(25, "tNOT");
    KIND_NAME.put(24, "tOR");
    KIND_NAME.put(23, "tAND");
    KIND_NAME.put(22, "tNI");
    KIND_NAME.put(21, "tMEI");
    KIND_NAME.put(20, "tMAI");
    KIND_NAME.put(19, "tIGUAL");
    KIND_NAME.put(18, "tMENOR");
    KIND_NAME.put(17, "tMAYOR");
    KIND_NAME.put(16, "tOPAS");
    KIND_NAME.put(15, "tMOD");
    KIND_NAME.put(14, "tDIV");
    KIND_NAME.put(13, "tDIVCHAR");
    KIND_NAME.put(12, "tMUL");
    KIND_NAME.put(11, "tMENOS");
    KIND_NAME.put(10, "tMAS");
    KIND_NAME.put(9, "tCARACTER");
    KIND_NAME.put(8, "tBOOLEANO");
    KIND_NAME.put(7, "tENTERO");
    KIND_NAME.put(6, "tCOMENTARIO");
  }

  private static String stripExtension(String filename) {
    return filename.substring(0, filename.lastIndexOf("."));
  }

  private List<ExpectedToken> getExpectedTokens(String filename) throws FileNotFoundException {
    Gson gson = new Gson();
    FileReader expectedTokensReader = new FileReader(
        getClass().getResource("/programs/tokens/" + stripExtension(filename) + ".json").getFile());
    Type type = new TypeToken<Map<String, List<ExpectedToken>>>() {
    }.getType();
    Map<String, List<ExpectedToken>> expectedTokens = gson.fromJson(expectedTokensReader, type);
    return expectedTokens.get("tokens");
  }

  @Test
  public void tokenFreq() throws FileNotFoundException {
    File programsDir = new File(getClass().getResource("/programs/source").getFile());
    for (File f : Objects.requireNonNull(programsDir.listFiles())) {
      MiniLeng.ReInit(new FileReader(f));
      List<ExpectedToken> expectedTokens = getExpectedTokens(f.getName());
      int i = 0;
      for (Token actualToken = MiniLeng.getNextToken(); actualToken.kind != MiniLengConstants.EOF;
          actualToken = MiniLeng.getNextToken()) {
        Assert.assertEquals("They have different 'kind'. In file " + f.getName(),
            expectedTokens.get(i).token, KIND_NAME.get(actualToken.kind));
        Assert.assertEquals("They have different 'image'. In file " + f.getName(),
            expectedTokens.get(i).image, actualToken.image);
        ++i;
      }
      Assert.assertEquals("The token manager didn't consume all of the tokens from the input",
          expectedTokens.size(), i);
    }
  }

  private static class ExpectedToken {

    int line;
    String token;
    String image;

    ExpectedToken(int line, String token, String image) {
      this.line = line;
      this.token = token;
      this.image = image;
    }
  }
}
