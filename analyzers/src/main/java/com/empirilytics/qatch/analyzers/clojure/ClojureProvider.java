package com.empirilytics.qatch.analyzers.clojure;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.java.*;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class ClojureProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return ClojureProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new ClojureProvider();
  }

  /** Private default constructor */
  private ClojureProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);

    issuesAnalyzer = new KondoAnalyzer(config.get("clj-kondoPath"), resultsPath, config.get("ruleSetPath"));
    metricsAnalyzer = new CKJMAnalyzer(config.get("ckjmPath"), resultsPath);
    issuesImporter = new KondoResultsImporter();
    metricsImporter = new CKJMResultsImporter();
    issuesAggregator = new KondoAggregator();
    metricsAggregator = new CKJMAggregator();
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "clojure";
  }
}
