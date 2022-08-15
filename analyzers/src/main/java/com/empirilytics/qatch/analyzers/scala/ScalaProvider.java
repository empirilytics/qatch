package com.empirilytics.qatch.analyzers.scala;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.CKJMAggregator;
import com.empirilytics.qatch.analyzers.java.CKJMAnalyzer;
import com.empirilytics.qatch.analyzers.java.CKJMResultsImporter;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class ScalaProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return ScalaProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new ScalaProvider();
  }

  /** Private default constructor */
  private ScalaProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);

    issuesAnalyzer = new LinterAnalyzer(config.get("linterPath"), resultsPath, config.get("ruleSetPath"));
    metricsAnalyzer = new CKJMAnalyzer(config.get("ckjmPath"), resultsPath);
    issuesImporter = new LinterResultsImporter();
    metricsImporter = new CKJMResultsImporter();
    issuesAggregator = new LinterAggregator();
    metricsAggregator = new CKJMAggregator();
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "scala";
  }
}
