package com.empirilytics.qatch.analyzers.java;

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
public class PMDAnalyzer implements IssuesAnalyzer {

  public static final String TOOL_NAME = "PMD";
  private String toolPath;
  private String rulesetPath;
  private String resultsPath;

  /**
   * Constructs a new PMDAnalyzer
   *
   * @param path Path of the project, cannot be null
   * @param resultsPath Path to the results director, cannot be null
   * @param rulesetPath Path to the rulesets, cannot be null
   */
  public PMDAnalyzer(
      @NonNull String path, @NonNull String resultsPath, @NonNull String rulesetPath) {
    this.toolPath = path;
    this.resultsPath = resultsPath;
    this.rulesetPath = rulesetPath;
  }

  public PMDAnalyzer() {}

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
    // Create the command that should be executed
    if (!Files.exists(Paths.get(dest))) {
      try {
        Files.createDirectories(Paths.get(dest));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    CommandLine cmdLine = new CommandLine(toolPath);
    cmdLine.addArgument("pmd");
    cmdLine.addArgument("-d");
    cmdLine.addArgument(src);
    cmdLine.addArgument("-f");
    cmdLine.addArgument("xml");
    cmdLine.addArgument("-l");
    cmdLine.addArgument("java");
    cmdLine.addArgument("-R");
    cmdLine.addArgument(ruleset);
    cmdLine.addArgument("-r");
    cmdLine.addArgument(dest + File.separator + filename + ".xml");

    try {
      DefaultExecutor executor = new DefaultExecutor();
      executor.setExitValue(0);
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

    // Create an Iterator in order to iterate through the properties of the desired PropertySet
    // object
    Iterator<Property> iterator = properties.iterator();
    Property p;

    // For each property found in the PropertySet do...
    while (iterator.hasNext()) {

      // Get the current property
      p = iterator.next();

      // Check if it is a PMD Property
      // TODO: Check this outside this function
      if (p.getMeasure().getTool().equals(PMDAnalyzer.TOOL_NAME) && p.getMeasure().isFinding()) {
        // Analyze the project against this property
        String ruleSet =
            Paths.get(rulesetPath, ((Finding) p.getMeasure()).getRulesetPath())
                .toAbsolutePath()
                .toString();
        analyze(src, dest, String.join(",", ruleSet), p.getName());
      }
    }
  }
}
