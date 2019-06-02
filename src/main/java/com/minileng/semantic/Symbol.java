package com.minileng.semantic;

import java.util.List;

/**
 * A simple class that represents a symbol.
 */
public class Symbol {

  private String name;
  private int scope;
  private SymbolType symbolType;
  private VariableType variableType;
  private ParameterType parameterType;
  private boolean visible;
  private List<Symbol> parameterList;
  private int address;

  private Symbol() {
  }

  /**
   * Build a parameter symbol.
   *
   * @param name the name of the symbol
   * @param variableType the type of the parameter
   * @param parameterType the parameter type
   * @param scope the scope of the symbol
   * @return the parameter symbol with the specified values
   */
  static Symbol buildParameter(String name, VariableType variableType,
      ParameterType parameterType, int scope) {
    if (name == null || variableType == null || parameterType == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.variableType = variableType;
    symbol.parameterType = parameterType;
    symbol.scope = scope;
    symbol.symbolType = SymbolType.PARAMETER;
    return symbol;
  }

  /**
   * Build a variable symbol.
   *
   * @param name the name of the symbol
   * @param variableType the type of the variable
   * @param scope the scope of the symbol
   * @return the variable symbol with the specified values
   */
  static Symbol buildVariable(String name, VariableType variableType, int scope, int address) {
    if (name == null || variableType == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.variableType = variableType;
    symbol.scope = scope;
    symbol.symbolType = SymbolType.VARIABLE;
    symbol.address = address;
    return symbol;
  }

  /**
   * Build a program symbol.
   *
   * @param name the name of the symbol
   * @param scope the scope of the symbol
   * @return the program symbol with the specified values
   */
  static Symbol buildProgram(String name, int scope) {
    if (name == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.scope = scope;
    symbol.symbolType = SymbolType.PROGRAM;
    return symbol;
  }

  /**
   * Build an action symbol.
   *
   * @param name the name of the symbol
   * @param parameterList the list of parameter symbols that are part of the action
   * @param scope the scope of the symbol
   * @return the action symbol with the specified values
   */
  static Symbol buildAction(String name, List<Symbol> parameterList, int scope) {
    if (name == null || parameterList == null) {
      throw new IllegalArgumentException("Arguments must be non null");
    }
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.parameterList = parameterList;
    symbol.scope = scope;
    symbol.symbolType = SymbolType.ACTION;
    return symbol;
  }

  public String getName() {
    return name;
  }

  public int getScope() {
    return scope;
  }

  public SymbolType getSymbolType() {
    return symbolType;
  }

  public VariableType getVariableType() {
    return variableType;
  }

  public ParameterType getParameterType() {
    return parameterType;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public List<Symbol> getParameterList() {
    return parameterList;
  }

  public int getAddress() {
    return address;
  }

  /**
   * Check if this symbol is a variable symbol.
   *
   * @return true if this symbol is a variable symbol, false otherwise
   */
  public boolean isVariable() {
    return symbolType == SymbolType.VARIABLE;
  }

  /**
   * Check if this symbol is a parameter symbol.
   *
   * @return true if this symbol is a parameter symbol, false otherwise
   */
  public boolean isParameter() {
    return symbolType == SymbolType.PARAMETER;
  }

  /**
   * Check if this symbol is an action symbol.
   *
   * @return true if this symbol is an action symbol, false otherwise
   */
  public boolean isAction() {
    return symbolType == SymbolType.ACTION;
  }

  /**
   * Check if this symbol is a value parameter symbol.
   *
   * @return true if this symbol is a value parameter symbol, false otherwise
   */
  public boolean isValue() {
    return symbolType == SymbolType.PARAMETER && parameterType == ParameterType.VAL;
  }

  /**
   * Check if this symbol is a reference parameter symbol.
   *
   * @return true if this symbol is a reference parameter symbol, false otherwise
   */
  public boolean isReference() {
    return symbolType == SymbolType.PARAMETER && parameterType == ParameterType.REF;
  }

  /**
   * Check if this symbol is a program symbol.
   *
   * @return true if this symbol is a program symbol, false otherwise
   */
  public boolean isProgram() {
    return symbolType == SymbolType.PROGRAM;
  }

  /**
   * The types of symbols that there can be.
   */
  public enum SymbolType {
    PROGRAM, VARIABLE, ACTION, PARAMETER
  }

  /**
   * The types of parameters that there can be.
   */
  public enum ParameterType {
    VAL, REF
  }

  /**
   * The types of variables that there can be.
   */
  public enum VariableType {
    UNKNOWN, INT, BOOL, CHAR, STRING
  }
}
