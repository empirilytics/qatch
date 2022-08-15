package com.empirilytics.qatch.analyzers.clojure;

import com.empirilytics.qatch.analyzers.AbstractIssueAnalyzer;
import lombok.NonNull;
import org.apache.commons.exec.CommandLine;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class KondoAnalyzer extends AbstractIssueAnalyzer {

  public static final String TOOL_NAME = "clj-kondo";

  public KondoAnalyzer(@NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    super(path, resultsPath, rulesetPath);
  }

  /** {@inheritDoc} */
  @Override
  protected CommandLine constructCommandLine(@NonNull String src, @NonNull String dest, @NonNull String ruleset, @NonNull String filename) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  protected String getToolName() {
    return null;
  }

  @Override
  protected int[] getExitValues() {
    return new int[0];
  }
}
