package com.empirilytics.qatch.analyzers;

import com.empirilytics.qatch.analyzers.java.PMDAnalyzer;
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
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public abstract class AbstractIssueAnalyzer implements IssuesAnalyzer {
  protected String toolPath;
  protected String rulesetPath;
  protected String resultsPath;

  public AbstractIssueAnalyzer(@NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    this.toolPath = path;
    this.resultsPath = resultsPath;
    this.rulesetPath = rulesetPath;
  }

  /**
   * This method is used in order to analyze a single project against a certain ruleset (property)
   * by calling the PMD tool through the command line with the appropriate configuration.
   *
   * <p>ATTENTION: - The appropriate build.xml ant file should be placed inside the base directory.
   *
   * @param src The path to the project to be analyzed, cannot be null
   * @param dest The path to the destination folder where the results will be placed, cannot be null
   * @param ruleset The ruleset for use by the tool for analysis, cannot be null
   * @param filename Name of the file to which analysis results are to be stored, cannot be null
   */
  @Override
  public void analyze(
          @NotNull String src,
          @NotNull String dest,
          @NotNull String ruleset,
          @NotNull String filename) {

    Path path = Paths.get(dest);
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (Exception ex) {
        log.error(ex.getMessage());
      }
    }

    CommandLine cmdLine = constructCommandLine(src, dest, ruleset, filename);

    try {
      DefaultExecutor executor = new DefaultExecutor();
      executor.setExitValues(getExitValues());
      executor.setWorkingDirectory(new File(src));
      int exitValue = executor.execute(cmdLine);
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }

  /**
   * This method is responsible for analyzing a single project against a set of properties (i.e. PMD
   * rulesets) by using the PMD Tool.
   *
   * <p>Typically this method does the following:
   *
   * <ol>
   *   <li>Iterates through the PropertySet
   *   <li>For each Property object the method calls the analyze() method in order to analyze the
   *       project against this single property.
   * </ol>
   *
   * @param src : The path of the folder that contains the sources of the project.
   * @param dest : The path where the XML files with the results will be placed.
   * @param properties : The set of properties against which the project will be analyzed.
   */
  @Override
  public void analyze(@NotNull String src, @NotNull String dest, @NotNull PropertySet properties) {
    properties.stream().forEach(p -> {
      if (checkIsTool(p)) {
        String ruleSet =
                Paths.get(rulesetPath, ((Finding) p.getMeasure()).getRulesetPath())
                        .toAbsolutePath()
                        .toString();
        analyze(src, dest, ruleSet, p.getName());
      }
    });
  }

  protected boolean checkIsTool(Property p) {
    return p.getMeasure().getTool().equalsIgnoreCase(getToolName()) && p.getMeasure().isFinding();
  }

  protected abstract CommandLine constructCommandLine(@NonNull String src, @NonNull String dest, @NonNull String ruleset, @NonNull String filename);

  protected abstract int[] getExitValues();
}
