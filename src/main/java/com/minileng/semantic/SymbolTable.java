package com.minileng.semantic;

import com.minileng.semantic.Symbol.ParameterType;
import com.minileng.semantic.Symbol.VariableType;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

/**
 * A simple implementation of a symbol table.
 */
public class SymbolTable {

  private static final SymbolTable INSTANCE = new SymbolTable();

  private static final int TABLE_SIZE = 127;
  private LinkedList<Symbol> symbols[];
  private int scope = 0;

  @SuppressWarnings("unchecked")
  private SymbolTable() {
    symbols = new LinkedList[TABLE_SIZE];
  }

  /**
   * Get the single instance of the symbol table.
   *
   * @return the instance of the symbol table
   */
  public static SymbolTable getInstance() {
    return INSTANCE;
  }

  /**
   * Retrieve the symbol with the name {@code name} that has the highest level; null if there is no
   * symbol with that name.
   *
   * @param name the name of the symbol to find
   */
  public Symbol get(String name) {
    int h = hash(name);
    List<Symbol> l = symbols[h];
    if (l == null) {
      return null;
    }
    if (l.isEmpty()) {
      return null;
    }
    return l.get(0);
  }

  public void putParameter(String name, VariableType variableType,
      ParameterType parameterType) {
    put(Symbol.buildParameter(name, variableType, parameterType, scope));
  }

  public void putVariable(String name, VariableType variableType) {
    put(Symbol.buildVariable(name, variableType, scope));
  }

  public void putProgram(String name) {
    put(Symbol.buildProgram(name, scope));
  }

  public void putAction(String name, List<Symbol> parameterList) {
    put(Symbol.buildAction(name, parameterList, scope));
  }

  /**
   * Insert the symbol {@code symbol} into the table.
   *
   * @param symbol the symbol to be inserted
   */
  private void put(Symbol symbol) {
    int h = hash(symbol.getName());
    LinkedList<Symbol> l = symbols[h];
    if (l == null) {
      LinkedList<Symbol> n = new LinkedList<>();
      n.add(symbol);
      symbols[h] = n;
    } else {
      insert(l, symbol);
    }
  }

  /**
   * Remove the program symbol. Only one should exist.
   */
  public void removeProgram() {
    for (LinkedList<Symbol> s : symbols) {
      if (s != null) {
        s.removeIf(Symbol::isProgram);
      }
    }
  }

  /**
   * Remove the variable symbols that can be found at the level {@code level}.
   *
   * @param level the level at witch we should delete the symbols
   */
  public void removeVariables(int level) {
    for (LinkedList<Symbol> s : symbols) {
      if (s != null) {
        removeSymbols(s, level, Symbol::isVariable);
      }
    }
  }

  /**
   * Hide the parameter symbols that can be found at the level {@code level}.
   *
   * @param level the level at witch we should delete the symbols
   */

  public void hideParameters(int level) {
    for (LinkedList<Symbol> s : symbols) {
      if (s != null) {
        hideSymbols(s, level, Symbol::isParameter);
      }
    }
  }

  /**
   * Open new scope.
   */
  public void openScope() {
    ++scope;
  }

  /**
   * Close last scope.
   */
  public void closeScope() {
    --scope;
  }

  /**
   * Remove the hidden parameter symbols that can be found at the level {@code level}.
   *
   * @param level the level at witch we should delete the symbols
   */

  public void removeHiddenParameters(int level) {
    for (LinkedList<Symbol> s : symbols) {
      if (s != null) {
        removeSymbols(s, level, symbol -> symbol.isParameter() && !symbol.isVisible());
      }
    }
  }

  /**
   * Remove the action symbols that can be found at the level {@code level}.
   *
   * @param level the level at witch we should delete the symbols
   */

  public void removeActions(int level) {
    for (LinkedList<Symbol> s : symbols) {
      if (s != null) {
        removeSymbols(s, level, Symbol::isAction);
      }
    }
  }


  /**
   * Remove the symbols at the level {@code level} from the list {@code symbols} that satisfy the
   * predicate {@code predicate}.
   *
   * @param symbols the list of symbols
   * @param level the level
   * @param predicate the predicate
   */
  private void removeSymbols(LinkedList<Symbol> symbols, int level, Predicate<Symbol> predicate) {
    assert symbols != null;
    assert predicate != null;
    if (symbols.isEmpty()) {
      return;
    }
    ListIterator<Symbol> it = symbols.listIterator();
    while (it.hasNext()) {
      Symbol s = it.next();
      if (s.getScope() < level) {
        return;
      }
      if (predicate.test(s)) {
        it.remove();
      }
    }
  }

  /**
   * Mark as invisible the symbols from the list {@code symbols} at the level {@code level} that
   * satisfy the predicate {@code predicate}
   *
   * @param symbols the list of symbols
   * @param level the level
   * @param predicate the predicate
   */
  private void hideSymbols(LinkedList<Symbol> symbols, int level, Predicate<Symbol> predicate) {
    assert symbols != null;
    assert predicate != null;
    if (symbols.isEmpty()) {
      return;
    }
    for (Symbol s : symbols) {
      if (s.getScope() < level) {
        return;
      }
      if (predicate.test(s)) {
        s.setVisible(false);
      }
    }
  }

  /**
   * Insert the symbol {@code symbol} in the list of symbols {@code symbols}.
   * <p>
   * The list of symbols is ordered in descendant order of level, so the symbol is inserted with
   * this constraint in mind and always to the leftmost side.
   *
   * @param symbols the list of symbols
   * @param symbol the symbol to be inserted
   */
  private void insert(LinkedList<Symbol> symbols, Symbol symbol) {
    assert symbols != null;
    assert symbol != null;
    if (symbols.isEmpty()) {
      symbols.add(symbol);
      return;
    }
    ListIterator<Symbol> it = symbols.listIterator();
    while (it.hasNext()) {
      Symbol s = it.next();
      if (symbol.getScope() >= s.getScope()) {
        break;
      }
    }
    it.add(symbol);
  }

  /**
   * Calculate the index of the symbol name {@code name} inside the table.
   *
   * @param name the name of the symbol
   * @return the index inside the table where the symbol name should go
   */
  private int hash(String name) {
    assert name != null;
    return (PearsonHash.hash(name) & 0x7FFFFFFF) % symbols.length;
  }

}
