package org.slf4j.impl;

import java.util.Scanner;
import java.util.concurrent.Callable;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {
  public static String REQUESTED_API_VERSION = "1.7";
  private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

  private final String loggerFactoryClassStr;
  private final ClassLoader loader;

  private StaticLoggerBinder() {
    loader = getClass().getClassLoader();
    Scanner s = new Scanner(loader.getResourceAsStream("slf4j-impl-helper"));
    loggerFactoryClassStr = s.nextLine();
    s.close();
  }

  public ILoggerFactory getLoggerFactory() {
    try {
      return loader.loadClass(loggerFactoryClassStr).asSubclass(ILoggerFactory.class).newInstance();
    }
    catch (ClassNotFoundException ex) {
      throw new RuntimeException(ex);
    }
    catch (InstantiationException ex) {
      throw new RuntimeException(ex);
    }
    catch (IllegalAccessException ex) {
      throw new RuntimeException(ex);
    }
  }

  public String getLoggerFactoryClassStr() {
    return loggerFactoryClassStr;
  }

  public static StaticLoggerBinder getSingleton() {
    return SINGLETON;
  }
}
