package com.empirilytics.qatch.analyzers.cpp;

import com.empirilytics.qatch.analyzers.AbstractMetricsAnalyzer;
import lombok.NonNull;
import org.apache.commons.exec.CommandLine;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class CqmetricsAnalyzer extends AbstractMetricsAnalyzer {

  public static final String TOOL_NAME = "cqmetrics";

  public CqmetricsAnalyzer(String cqmetricsPath, String resultPath) {
    super(cqmetricsPath, resultPath);
  }

  /** {@inheritDoc} */
  @Override
  protected CommandLine constructCommandLine(@NonNull String src, @NonNull String dest) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }
}
