package com.empirilytics.qatch.analyzers.dart;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.JavaProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class DartProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return DartProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new DartProvider();
  }

  /** Private default constructor */
  private DartProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "dart";
  }
}
