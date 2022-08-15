package com.empirilytics.qatch.analyzers.php;

import com.empirilytics.qatch.analyzers.LanguageProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class PhpProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return PhpProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new PhpProvider();
  }

  /** Private default constructor */
  private PhpProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "php";
  }
}
