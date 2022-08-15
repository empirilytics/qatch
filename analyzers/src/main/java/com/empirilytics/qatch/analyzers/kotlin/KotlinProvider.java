package com.empirilytics.qatch.analyzers.kotlin;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.CKJMAggregator;
import com.empirilytics.qatch.analyzers.java.CKJMAnalyzer;
import com.empirilytics.qatch.analyzers.java.CKJMResultsImporter;
import com.empirilytics.qatch.analyzers.java.JavaProvider;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class KotlinProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return KotlinProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new KotlinProvider();
  }

  /** Private default constructor */
  private KotlinProvider() {}

  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);

    issuesAnalyzer = new DetektAnalyzer(config.get("detektPath"), resultsPath, config.get("ruleSetPath"));
    metricsAnalyzer = new CKJMAnalyzer(config.get("ckjmPath"), resultsPath);
    issuesImporter = new DetektResultsImporter();
    metricsImporter = new CKJMResultsImporter();
    issuesAggregator = new DetektAggregator();
    metricsAggregator = new CKJMAggregator();
  }

  @Override
  public String getLanguage() {
    return "kotlin";
  }
}
