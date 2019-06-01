package com.minileng.generated;

import java.io.ByteArrayInputStream;

class ParserInitializer {

  private static boolean initialized = false;

  static void init() {
    if (!initialized) {
      initialized = true;
      new MiniLeng(new ByteArrayInputStream(new byte[]{}));
    }
  }
}
