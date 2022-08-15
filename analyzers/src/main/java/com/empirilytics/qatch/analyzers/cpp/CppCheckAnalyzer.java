package com.empirilytics.qatch.analyzers.cpp;

import com.empirilytics.qatch.analyzers.AbstractIssueAnalyzer;
import lombok.NonNull;
import org.apache.commons.exec.CommandLine;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class CppCheckAnalyzer extends AbstractIssueAnalyzer {

  public static final String TOOL_NAME = "CppCheck";

  public CppCheckAnalyzer(@NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    super(path, resultsPath, rulesetPath);
  }

  @Override
  protected CommandLine constructCommandLine(@NonNull String src, @NonNull String dest, @NonNull String ruleset, @NonNull String filename) {
    return null;
  }

  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }

  @Override
  protected int[] getExitValues() {
    return new int[0];
  }
}
