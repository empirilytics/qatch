package com.empirilytics.qatch.analyzers;

import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public abstract class AbstractMetricsAnalyzer implements MetricsAnalyzer {
  protected String toolPath;
  protected String resultPath;

  public AbstractMetricsAnalyzer(String path, String resultPath) {
    this.toolPath = path;
    this.resultPath = resultPath;
  }

  /**
   * This method is used to analyze a single project with the CKJM static analysis tool.
   *
   * <p>ATTENTION: - The appropriate build.xml ant file should be placed inside the eclipse folder.
   * - TODO: Check if you can provide the path of the build.xml.
   *
   * @param src : The path of the folder that contains the class files of the project, cannot be
   *     null
   * @param dest : The path where the XML file that contains the results will be placed, cannot be
   *     null
   */
  @Override
  public void analyze(@NonNull String src, @NonNull String dest) {
    // Configure the command that should be executed
    src = Paths.get(src).toAbsolutePath().toString();
    dest = Paths.get(dest).toAbsolutePath().normalize().toString();
    CommandLine cmdLine = constructCommandLine(src,  dest);

    try {
      DefaultExecutor executor = new DefaultExecutor();
      executor.setExitValue(0);
      executor.setWorkingDirectory(new File(src));
      int exitValue = executor.execute(cmdLine);
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }

  /**
   * This method is responsible for analyzing a single project against a set of properties by using
   * the CKJM Tool.
   *
   * <p>Typically this method does the following:
   *
   * <p>1. Iterates through the PropertySet. 2. If it finds at least one property that uses the CKJM
   * tool then it calls the simple analyze() method.
   *
   * <p>IDEA: - All the metrics are calculated for the project and then loaded by the program. -
   * After that we decide which metrics to keep by iterating through the PropertySet of the Quality
   * Model.
   *
   * <p>It has this form in order to look the same with the PMDAnalyzer.
   *
   * @param src The path of the folder that contains the class files of the desired project, cannot
   *     be null
   * @param dest The path where the XML file that contains the results should be placed, cannot be
   *     null
   * @param properties The set of properties to evaluate against, cannot be null
   */
  public void analyze(@NotNull String src, @NotNull String dest, @NotNull PropertySet properties) {
    Iterator<Property> iterator = properties.iterator();
    Property p;

    while (iterator.hasNext()) {
      p = iterator.next();

      boolean result = checkIsTool(p);
      if (result) {
        analyze(src, dest);
        break;
      }
    }
  }

  protected boolean checkIsTool(Property p) {
    return p.getMeasure().getTool().equals(getToolName()) && p.getMeasure().isMetric();
  }

  protected abstract CommandLine constructCommandLine(@NonNull String src, @NonNull String dest);

  protected abstract String getToolName();
}
