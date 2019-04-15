package com.minileng.utils;

import java.io.OutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LoggerUtils {

  public static void addAppender(final OutputStream outputStream, final String outputStreamName) {
    final LoggerContext context = LoggerContext.getContext(false);
    final Configuration config = context.getConfiguration();
    final PatternLayout layout = PatternLayout.createDefaultLayout(config);
    final Appender appender = OutputStreamAppender
        .createAppender(layout, null, outputStream, outputStreamName, false, true);
    appender.start();
    config.addAppender(appender);
    updateLoggers(appender, config);
  }

  private static void updateLoggers(final Appender appender, final Configuration config) {
    final Level level = null;
    final Filter filter = null;
    for (final LoggerConfig loggerConfig : config.getLoggers().values()) {
      loggerConfig.addAppender(appender, level, filter);
    }
    config.getRootLogger().addAppender(appender, level, filter);
  }
}
