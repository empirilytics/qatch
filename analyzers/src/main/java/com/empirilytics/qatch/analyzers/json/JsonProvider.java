package com.empirilytics.qatch.analyzers.json;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.JavaProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class JsonProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return JsonProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new JsonProvider();
  }

  /** Private default constructor */
  private JsonProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "json";
  }
}
