package com.empirilytics.qatch.analyzers.js;

import com.empirilytics.qatch.analyzers.AbstractIssueAnalyzer;
import com.empirilytics.qatch.analyzers.IssuesAnalyzer;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import org.apache.commons.exec.CommandLine;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class ESLintAnalyzer extends AbstractIssueAnalyzer {

  public static final String TOOL_NAME = "ESLint";

  public ESLintAnalyzer(@NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    super(path, resultsPath, rulesetPath);
  }

  @Override
  protected CommandLine constructCommandLine(@NonNull String src, @NonNull String dest, @NonNull String ruleset, @NonNull String filename) {
    return null;
  }

  @Override
  protected String getToolName() {
    return null;
  }

  @Override
  protected int[] getExitValues() {
    return new int[0];
  }
}
