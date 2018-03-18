package com.minileng.arguments;

/**
 * Exception to be used when the main arguments of the program couldn't be parsed.
 */
public class ArgParserException extends Exception {

  public ArgParserException() {
    super();
  }

  public ArgParserException(String message) {
    super(message);
  }
}
