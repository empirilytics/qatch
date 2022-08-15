package com.empirilytics.qatch.analyzers.java;

import com.empirilytics.qatch.analyzers.AbstractMetricsAnalyzer;
import com.empirilytics.qatch.analyzers.MetricsAnalyzer;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * This class is responsible for analyzing a single project by invoking the CKJM tool.
 *
 * <p>This can be done by using the first and the second method of this class respectively.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class CKJMAnalyzer extends AbstractMetricsAnalyzer {

  public static final String TOOL_NAME = "CKJM";

  public CKJMAnalyzer(String path, String resultPath) {
    super(path, resultPath);
  }

  /** {@inheritDoc} */
  @Override
  public CommandLine constructCommandLine(@NonNull String src, @NonNull String dest) {
    return new CommandLine(toolPath)
            .addArgument("-x")
            .addArgument("-n")
            .addArgument("-d")
            .addArgument(src)
            .addArgument("-o")
            .addArgument(dest);
  }

  /** {@inheritDoc} */
  @Override
  protected String getToolName() {
    return TOOL_NAME;
  }
}
