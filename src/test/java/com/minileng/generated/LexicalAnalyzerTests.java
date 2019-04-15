package com.minileng.generated;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minileng.collections.KindNameMapping;
import com.minileng.utils.LoggerUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LexicalAnalyzerTests {

  private static ByteArrayOutputStream stdOut = new ByteArrayOutputStream();

  @BeforeClass
  public static void initParser() {
    LoggerUtils.addAppender(stdOut, "test");
    new MiniLeng(new ByteArrayInputStream(new byte[]{}));
  }

  private static String stripExtension(String filename) {
    return filename.substring(0, filename.lastIndexOf("."));
  }

  @Before
  public void setStreams() {
    stdOut.reset();
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
  public void thatReadsTheSameTokenSequence() throws FileNotFoundException {
    File programsDir = new File(getClass().getResource("/programs/source").getFile());
    for (File f : Objects.requireNonNull(programsDir.listFiles())) {
      MiniLeng.ReInit(new FileReader(f));
      List<ExpectedToken> expectedTokens = getExpectedTokens(f.getName());
      int i = 0;
      for (Token actualToken = MiniLeng.getNextToken(); actualToken.kind != MiniLengConstants.EOF;
          actualToken = MiniLeng.getNextToken()) {
        Assert.assertEquals("They have different 'kind'. In file " + f.getName(),
            expectedTokens.get(i).token, KindNameMapping.getInstance().name(actualToken.kind));
        Assert.assertEquals("They have different 'image'. In file " + f.getName(),
            expectedTokens.get(i).image, actualToken.image);
        ++i;
      }
      Assert.assertEquals("The token manager didn't consume all of the tokens from the input",
          expectedTokens.size(), i);
    }
  }

  @Test
  public void reportUnkownSymbol() throws ParseException {
    String p = "programa t;\n principio [ := 3 ; fin";
    MiniLeng.ReInit(new ByteArrayInputStream(p.getBytes(StandardCharsets.UTF_8)));
    MiniLeng.programa();
    String expectedOutput = "ERROR LÉXICO (2, 12): símbolo no reconocido: [\n";
    Assert.assertEquals(expectedOutput, stdOut.toString());
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
