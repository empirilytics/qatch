package com.empirilytics.qatch.analyzers.js;

import com.empirilytics.qatch.analyzers.AbstractMetricsAnalyzer;
import lombok.NonNull;
import org.apache.commons.exec.CommandLine;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class CK4JSAnalyzer extends AbstractMetricsAnalyzer {

  public static final String TOOL_NAME = "CK4JS";

  public CK4JSAnalyzer(String ck4jsPath, String resultPath) {
    super(ck4jsPath, resultPath);
  }

  @Override
  protected CommandLine constructCommandLine(@NonNull String src, @NonNull String dest) {
    return null;
  }

  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }
}
