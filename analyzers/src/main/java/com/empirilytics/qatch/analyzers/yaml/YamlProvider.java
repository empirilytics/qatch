package com.empirilytics.qatch.analyzers.yaml;

import com.empirilytics.qatch.analyzers.LanguageProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class YamlProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return YamlProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new YamlProvider();
  }

  /** Private default constructor */
  private YamlProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "yaml";
  }
}
