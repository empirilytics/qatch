package com.empirilytics.qatch.analyzers.java;

import com.empirilytics.qatch.analyzers.MetricsAnalyzer;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
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
public class CKJMAnalyzer implements MetricsAnalyzer {

  public static final String TOOL_NAME = "CKJM";
  private String toolPath;
  private String resultPath;

  public CKJMAnalyzer(String path, String resultPath) {
    this.toolPath = path;
    this.resultPath = resultPath;
  }

  public CKJMAnalyzer() {}

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
    // Check the OS type
    if (System.getProperty("os.name").contains("Windows")) {
      src = "\"" + src + "\"";
      dest = "\"" + dest + "\"";
    }

    // Configure the command that should be executed
    src = Paths.get(src).toAbsolutePath().toString();
    CommandLine cmdLine;
    cmdLine =
        new CommandLine(toolPath)
            .addArgument("-x")
            .addArgument("-n")
            .addArgument("-d")
            .addArgument(src)
            .addArgument("-o")
            .addArgument(dest);

    try {
      DefaultExecutor executor = new DefaultExecutor();
      executor.setExitValue(0);
      int exitValue = executor.execute(cmdLine);
    } catch (IOException ex) {
      ex.printStackTrace();
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
  @Override
  public void analyze(@NotNull String src, @NotNull String dest, @NotNull PropertySet properties) {
    // Iterate through the properties of the desired PropertySet object
    Iterator<Property> iterator = properties.iterator();
    Property p;

    // For each property found in the desired PropertySet do...
    while (iterator.hasNext()) {
      // Get the current property
      p = iterator.next();

      // Check if it is a ckjm property
      // TODO: Check this outside this function
      boolean result = checkIsCKJM(p);
      if (result) {
        analyze(src, dest);
        break;
      }
    }
  }

  private boolean checkIsCKJM(Property p) {
    return p.getMeasure().getTool().equals(CKJMAnalyzer.TOOL_NAME) && p.getMeasure().isMetric();
  }
}
