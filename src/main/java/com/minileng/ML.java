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
import java.io.IOException;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ML {

  private static final Logger LOG = LogManager.getLogger();
  private static final String FILE_POSTFIX_CODE = ".code";
  private static final String FILE_POSTFIX_SRC = ".ml";

  public static void main(String... args) {
    String outputFile = null;
    try {
      Args parsedArgs = ArgsParser.parse(args);
      String file = parsedArgs.getArg("file", String.class);
      outputFile = file + FILE_POSTFIX_CODE;
      MiniLeng.ReInit(new FileReader(file + FILE_POSTFIX_SRC));
      MiniLeng.programa();
      if (!Objects.isNull(parsedArgs.getArg("-v", Boolean.class))) {
        LOG.info("Tabla de frecuencia de tokens:\n{}",
            MiniLengTokenManager.getTokenFreq().toString());
      }
      if (!MiniLeng.wasThereAnError()) {
        MiniLeng.writeCodeToFile(outputFile);
        LOG.info("Compilación finalizada. Se ha generado el fichero {}", outputFile);
      }
    } catch (FileNotFoundException e) {
      LOG.error("No se puede encontrar el fichero especificado.");
    } catch (ArgParserException | TokenMgrError e) {
      LOG.error("Argumentos incorrectos.");
    } catch (ParseException e) {
      // It should never happen because of error recovery
      LOG.error(e);
    } catch (IOException e) {
      LOG.error("Error al crear o al escribir en el fichero {}", outputFile);
    }
  }
}
