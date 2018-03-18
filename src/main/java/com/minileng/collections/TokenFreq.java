package com.minileng.collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used for storing the number of tokens appearing in a program.
 */
public class TokenFreq {

  private Map<String, Integer> tokenFreq;

  public TokenFreq() {
    tokenFreq = new HashMap<>();
  }

  public TokenFreq(Map<String, Integer> tokenFreq) {
    this.tokenFreq = tokenFreq;
  }

  /**
   * If it's the first time the specified token is counted, it's added with a frequency of one. If
   * it isn't the first time then its frequency is increased by one.
   *
   * @param token the name of the token to be added
   */
  public void addOrInc(String token) {
    int count = 1;
    if (tokenFreq.containsKey(token)) {
      count = tokenFreq.get(token) + 1;
    }
    tokenFreq.put(token, count);
  }

  /**
   * Get the frequency of the specified token.
   *
   * @param token the token we are interested in
   * @return the number of times the token with the name {@code token} has appeared
   */
  public Integer getFreq(String token) {
    return tokenFreq.get(token);
  }

  @Override
  public String toString() {
    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    return gson.toJson(tokenFreq);
  }

  /**
   * True if they are equal, false otherwise. Two {@link TokenFreq} are equal if they have the same
   * tokens with the same frequency.
   *
   * @param tf the other {@link TokenFreq}
   * @return true if they are equal, false otherwise
   */
  public boolean equals(TokenFreq tf) {
    for (String token : tokenFreq.keySet()) {
      if (tf.getFreq(token) == null || !tf.getFreq(token).equals(getFreq(token))) {
        return false;
      }
    }
    return true;
  }
}
