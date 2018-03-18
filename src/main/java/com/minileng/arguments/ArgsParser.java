package com.minileng.arguments;

import java.util.Arrays;
import java.util.Optional;

public class ArgsParser {

  private static final String USAGE = "Parámetros: [-v] fichero";

  public static Args parse(String... args) throws ArgParserException {
    if (args.length == 0 || args.length > 2) {
      throw new ArgParserException(USAGE);
    }

    Optional<String> version = Arrays.stream(args).filter(s -> s.equals("-v")).findFirst();
    Optional<String> file = Arrays.stream(args).filter(s -> s.matches("[^-][^\\n]*")).findFirst();
    Args parseArgs = new Args();
    version.ifPresent(s -> parseArgs.add("-v", Boolean.TRUE));
    file.ifPresent(s -> parseArgs.add("file", s));
    return parseArgs;
  }

}
