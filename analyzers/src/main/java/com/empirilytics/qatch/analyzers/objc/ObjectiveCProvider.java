package com.empirilytics.qatch.analyzers.objc;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.JavaProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class ObjectiveCProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return ObjectiveCProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new ObjectiveCProvider();
  }

  /** Private default constructor */
  private ObjectiveCProvider() {}

  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  @Override
  public String getLanguage() {
    return "objc";
  }
}
