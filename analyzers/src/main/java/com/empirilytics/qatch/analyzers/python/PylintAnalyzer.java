package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.AbstractIssueAnalyzer;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class PylintAnalyzer extends AbstractIssueAnalyzer {

  public static final String TOOL_NAME = "pylint";

  public PylintAnalyzer(String pylintPath, String resultsPath, String ruleSetPath) {
    super(pylintPath, resultsPath, ruleSetPath);
  }

  /** {@inheritDoc} */
  protected CommandLine constructCommandLine(
      @NotNull String src,
      @NotNull String dest,
      @NotNull String ruleset,
      @NotNull String filename) {
    // need to set working directory to python directory
    // pylint --recursive=y --rcfile=ruleset --output-format=json:output.json -j 4 filename
    // filename2 filename3 ...
    var rulesetName = Paths.get(ruleset).getFileName().toString().split("\\.")[0];
    CommandLine cmdLine = new CommandLine(toolPath)
            .addArgument("--rcfile=" + ruleset)
            .addArgument("--output-format=json:" + Paths.get(dest, filename + ".json"))
            .addArgument("-j")
            .addArgument("4")
            .addArgument(src);

    return cmdLine;
  }

  /** {@inheritDoc} */
  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }

  @Override
  protected int[] getExitValues() {
    int[] values = new int[64];
    for (int i = 0; i < values.length; i++)
      values[i] = i;
    return values;
  }
}
