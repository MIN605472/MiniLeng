import com.google.gson.Gson;
import com.minileng.collections.TokenFreq;
import com.minileng.generated.MiniLeng;
import com.minileng.generated.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class LexicalAnalyzerTests {

  private File getExpectedFreq(File program) {
    return new File(program.getParent() + File.separator + program.getName() + "-freq.json");
  }


  @Test
  @Ignore
  public void tokenFreq() throws FileNotFoundException, ParseException {
    Gson gson = new Gson();
    File programsDir = new File(this.getClass().getResource("/programas").getFile());
    for (File f : Objects.requireNonNull(programsDir.listFiles())) {
      if (f.getName().endsWith(".ml")) {
        MiniLeng parser = new MiniLeng(new FileReader(f));
        parser.programa();
        TokenFreq actualFreq = parser.token_source.getTokenFreq();
        TokenFreq expectedFreq = new TokenFreq(
            gson.<Map<String, Integer>>fromJson(new FileReader(getExpectedFreq(f)), Map.class));
        Assert.assertEquals("Programa: " + f.getName(), expectedFreq, actualFreq);
      }
    }
  }
}
