package com.empirilytics.qatch.analyzers.cpp;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class CppProvider extends LanguageProvider {

  public static LanguageProvider instance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new CppProvider();
  }

  private CppProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(@NonNull Map<String, String> config) {
    super.initialize(config);

    issuesAnalyzer = new CppCheckAnalyzer(config.get("cppcheckPath"), resultsPath, config.get("ruleSetPath"));
    metricsAnalyzer = new CqmetricsAnalyzer(config.get("cqmetricsPath"), resultsPath);
    issuesAggregator = new CppCheckAggregator();
    metricsAggregator = new CqmetricsAggregator();
    issuesImporter = new CppCheckResultsImporter();
    metricsImporter = new CqmetricsResultsImporter();
  }

  /** {@inheritDoc}  */
  @Override
  public String getLanguage() {
    return "cpp";
  }
}
