package com.empirilytics.qatch.analyzers.ts;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.js.*;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class TypeScriptProvider extends LanguageProvider {

  /**
   * Retrieves the singleton instance
   *
   * @return Singleton instance
   */
  public static LanguageProvider instance() {
    return TypeScriptProvider.InstanceHolder.INSTANCE;
  }

  /**
   * Internal clas designed to hold the singleton instance while providing lazy loading and thread
   * safety
   */
  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new TypeScriptProvider();
  }

  /** Private default constructor */
  private TypeScriptProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(Map<String, String> config) {
    super.initialize(config);

    issuesAnalyzer = new ESLintAnalyzer(config.get("eslintPath"), resultsPath, config.get("ruleSetPath"));
    metricsAnalyzer = new CK4JSAnalyzer(config.get("ck4jsPath"), resultsPath);
    issuesImporter = new ESLintResultsImporter();
    metricsImporter = new CK4JSResultsImporter();
    issuesAggregator = new ESLintAggregator();
    metricsAggregator = new CK4JSAggregator();
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "ts";
  }
}
