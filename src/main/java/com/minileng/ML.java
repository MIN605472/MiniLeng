package com.minileng;

import com.minileng.arguments.ArgParserException;
import com.minileng.arguments.Args;
import com.minileng.arguments.ArgsParser;
import com.minileng.generated.MiniLeng;
import com.minileng.generated.MiniLengTokenManager;
import com.minileng.generated.ParseException;
import com.minileng.generated.TokenMgrError;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ML {

  private static final Logger LOG = LogManager.getLogger();

  public static void main(String... args) {
    try {
      Args parsedArgs = ArgsParser.parse(args);
      String file = parsedArgs.getArg("file", String.class);
      MiniLeng.ReInit(new FileReader(file));
      MiniLeng.programa();
      if (!Objects.isNull(parsedArgs.getArg("-v", Boolean.class))) {
        LOG.info("Tabla de frecuencia de tokens:\n{}",
            MiniLengTokenManager.getTokenFreq().toString());
      }
    } catch (FileNotFoundException e) {
      LOG.error("No se puede encontrar el fichero especificado.");
    } catch (ArgParserException | TokenMgrError e) {
      LOG.error(e.getMessage());
    } catch (ParseException e) {
      LOG.error(e);
    }
  }
}
