package com.empirilytics.qatch.analyzers.java;

import com.empirilytics.qatch.analyzers.AbstractIssueAnalyzer;
import com.empirilytics.qatch.analyzers.IssuesAnalyzer;
import com.empirilytics.qatch.core.model.Finding;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * This class is responsible for analyzing a single project against:
 *
 * <p>1. a certain ruleset (i.e. property) or 2. a set of rulesets (i.e. properties) by invoking the
 * PMD tool.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class PMDAnalyzer extends AbstractIssueAnalyzer {

  public static final String TOOL_NAME = "PMD";

  /**
   * Constructs a new PMDAnalyzer
   *
   * @param path Path of the project, cannot be null
   * @param resultsPath Path to the results directory, cannot be null
   * @param rulesetPath Path to the rulesets, cannot be null
   */
  public PMDAnalyzer(
      @NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    super(path, resultsPath, rulesetPath);
  }

  /** {@inheritDoc} */
  @Override
  protected CommandLine constructCommandLine(
      @NotNull String src,
      @NotNull String dest,
      @NotNull String ruleset,
      @NotNull String filename) {

    return new CommandLine(toolPath)
      .addArgument("pmd")
      .addArgument("-d")
      .addArgument(src)
      .addArgument("-f")
      .addArgument("xml")
      .addArgument("-l")
      .addArgument("java")
      .addArgument("-R")
      .addArgument(ruleset)
      .addArgument("-r")
      .addArgument(dest + File.separator + filename + ".xml");
  }

  /** {@inheritDoc} */
  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }

  @Override
  protected int[] getExitValues() {
    return new int[]{0, 1, 4};
  }
}
