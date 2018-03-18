package com.minileng.arguments;


import java.util.HashMap;
import java.util.Map;

/**
 * This class holds the parsed arguments.
 */
public class Args {

  private Map<String, Object> parsedArgs;

  Args() {
    parsedArgs = new HashMap<>();
  }

  void add(String name, Object value) {
    parsedArgs.put(name, value);
  }

  /**
   * Returns the value of the argument with the specified name.
   *
   * @param name the name of the argument to retrieve
   * @param clazz the argument's value class
   * @param <T> the argument's value type
   * @return the value of the argument with the name {@code name} casted to {@code clazz}. If there
   * is no argument with that name, null is returned
   */
  public <T> T getArg(String name, Class<T> clazz) {
    Object o = parsedArgs.get(name);
    return o == null ? null : clazz.cast(o);
  }

}