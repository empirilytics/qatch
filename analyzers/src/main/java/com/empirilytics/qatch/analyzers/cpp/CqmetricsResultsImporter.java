package com.empirilytics.qatch.analyzers.cpp;

import com.empirilytics.qatch.analyzers.MetricsImporter;
import com.empirilytics.qatch.core.eval.MetricSet;
import lombok.NonNull;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class CqmetricsResultsImporter implements MetricsImporter {

  @Override
  public MetricSet parseMetrics(@NonNull String path) {
    return null;
  }

  @Override
  public String getFileName() {
    return null;
  }
}
