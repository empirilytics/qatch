package com.empirilytics.qatch.analyzers.fortran;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.JavaProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class FortranProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return FortranProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new FortranProvider();
  }

  /** Private default constructor */
  private FortranProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "fortran";
  }
}
