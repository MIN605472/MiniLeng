package com.minileng.collections;

import com.minileng.generated.MiniLengConstants;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds the mapping between token kind and token name.
 */
public class KindNameMapping {

  private static final KindNameMapping instance = new KindNameMapping();

  private Map<String, Integer> nameKindMap;
  private Map<Integer, String> kindNameMap;

  private KindNameMapping() {
    Field[] fields = MiniLengConstants.class.getFields();
    nameKindMap = new HashMap<>(fields.length);
    kindNameMap = new HashMap<>(fields.length);
    for (Field f : fields) {
      if (!f.getType().equals(int.class) || f.getName().equals("DEFAULT")) {
        continue;
      }
      try {
        nameKindMap.put(f.getName(), f.getInt(null));
        kindNameMap.put(f.getInt(null), f.getName());
      } catch (IllegalAccessException e) {
        // Shouldn't happen
        e.printStackTrace();
      }
    }
  }

  public static KindNameMapping getInstance() {
    return instance;
  }

  /**
   * Return the kind of the given token name.
   *
   * @param name the name of the token
   * @return the kind of the token named {@code name}, null if there is no token named that way
   */
  public Integer kind(String name) {
    return nameKindMap.get(name);
  }

  /**
   * Return the name of the given token kind.
   *
   * @param kind the kind of the token
   * @return the name associated with the kind {@code kind}, null if there is no such kind
   */
  public String name(int kind) {
    return kindNameMap.get(kind);
  }
}
