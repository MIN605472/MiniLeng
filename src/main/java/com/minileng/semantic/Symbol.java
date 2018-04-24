package com.minileng.semantic;

import java.util.List;

/**
 * A simple class that represents a symbol.
 */
public class Symbol {

  private static final char T[] = new char[]{
      251, 175, 119, 215, 81, 14, 79, 191, 103, 49, 181, 143, 186, 157, 0,
      232, 31, 32, 55, 60, 152, 58, 17, 237, 174, 70, 160, 144, 220, 90, 57,
      223, 59, 3, 18, 140, 111, 166, 203, 196, 134, 243, 124, 95, 222, 179,
      197, 65, 180, 48, 36, 15, 107, 46, 233, 130, 165, 30, 123, 161, 209, 23,
      97, 16, 40, 91, 219, 61, 100, 10, 210, 109, 250, 127, 22, 138, 29, 108,
      244, 67, 207, 9, 178, 204, 74, 98, 126, 249, 167, 116, 34, 77, 193,
      200, 121, 5, 20, 113, 71, 35, 128, 13, 182, 94, 25, 226, 227, 199, 75,
      27, 41, 245, 230, 224, 43, 225, 177, 26, 155, 150, 212, 142, 218, 115,
      241, 73, 88, 105, 39, 114, 62, 255, 192, 201, 145, 214, 168, 158, 221,
      148, 154, 122, 12, 84, 82, 163, 44, 139, 228, 236, 205, 242, 217, 11,
      187, 146, 159, 64, 86, 239, 195, 42, 106, 198, 118, 112, 184, 172, 87,
      2, 173, 117, 176, 229, 247, 253, 137, 185, 99, 164, 102, 147, 45, 66,
      231, 52, 141, 211, 194, 206, 246, 238, 56, 110, 78, 248, 63, 240, 189,
      93, 92, 51, 53, 183, 19, 171, 72, 50, 33, 104, 101, 69, 8, 252, 83, 120,
      76, 135, 85, 54, 202, 125, 188, 213, 96, 235, 136, 208, 162, 129, 190,
      132, 156, 38, 47, 1, 7, 254, 24, 4, 216, 131, 89, 21, 28, 133, 37, 153,
      149, 80, 170, 68, 6, 169, 234, 151
  };

  private String name;
  private int level;
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
   * @param level the level of the symbol
   * @return the parameter symbol with the specified values
   */
  public static Symbol buildParameter(String name, VariableType variableType,
      ParameterType parameterType, int level) {
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.variableType = variableType;
    symbol.parameterType = parameterType;
    symbol.level = level;
    symbol.symbolType = SymbolType.PARAMETER;
    return symbol;
  }

  /**
   * Build a variable symbol.
   *
   * @param name the name of the symbol
   * @param variableType the type of the variable
   * @param level the level of the symbol
   * @return the variable symbol with the specified values
   */
  public static Symbol buildVariable(String name, VariableType variableType, int level) {
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.variableType = variableType;
    symbol.level = level;
    symbol.symbolType = SymbolType.VARIABLE;
    return symbol;
  }

  /**
   * Build a program symbol.
   *
   * @param name the name of the symbol
   * @param level the level of the symbol
   * @return the program symbol with the specified values
   */
  public static Symbol buildProgram(String name, int level) {
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.level = level;
    symbol.symbolType = SymbolType.PROGRAM;
    return symbol;
  }

  /**
   * Build an action symbol.
   *
   * @param name the name of the symbol
   * @param parameterList the list of parameter symbols that are part of the action
   * @param level the level of the symbol
   * @return the action symbol with the specified values
   */
  public static Symbol buildAction(String name, List<Symbol> parameterList, int level) {
    Symbol symbol = new Symbol();
    symbol.name = name;
    symbol.parameterList = parameterList;
    symbol.level = level;
    symbol.symbolType = SymbolType.ACTION;
    return symbol;
  }

  // Implementation using Pearson's hashing algorithm
  @Override
  public int hashCode() {
    char hh[] = new char[4];
    for (int i = 0; i < hh.length; ++i) {
      char h = T[(name.charAt(0) + i) % T.length];
      for (int j = 0; j < name.length(); ++j) {
        h = T[h ^ name.charAt(j)];
      }
      hh[i] = h;
    }
    int res = hh[0] & 0xFF;
    res |= (hh[1] << 8) & 0xFF00;
    res |= (hh[2] << 16) & 0xFF0000;
    res |= (hh[3] << 24) & 0xFF000000;
    return res;
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
