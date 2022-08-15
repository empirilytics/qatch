package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.analyzers.IssuesAnalyzer;
import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.MetricsAnalyzer;
import com.empirilytics.qatch.analyzers.java.CKJMAnalyzer;
import com.empirilytics.qatch.analyzers.java.PMDAnalyzer;
import com.empirilytics.qatch.core.model.PropertySet;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Paths;

/**
 * This class is responsible for analyzing all the projects that are stored in the desired folder
 * (e.g. Benchmark Repository) against:
 *
 * <p>1. all the supported tools of the system (e.g. PMD, CKJM etc.) and 2. all the Properties of
 * the Quality Model (e.g. Comprehensibility etc.)
 *
 * <p>The results are stored in a fixed results directory of known structure. This directory is
 * automatically created by the BenchmarkAnalyzer based on the structure of the benchmark directory.
 *
 * <p>Typically, it creates a different folder for each project found in the benchmark repository
 * and places all the result files concerning this project in this folder.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class BenchmarkAnalyzer {

  @Setter private String benchRepoPath;
  @Setter @Getter private PropertySet properties;
  @Setter private String resultsPath;
  protected LanguageProvider languageProvider;

  /** The basic constructors of the class. */
  public BenchmarkAnalyzer() {
    this(null, null);
  }

  /** The second constructor of the class. */
  public BenchmarkAnalyzer(String benchRepoPath, LanguageProvider provider) {
    this(benchRepoPath, null, provider);
  }

  /** The third constructor of the class. */
  public BenchmarkAnalyzer(String benchRepoPath, PropertySet properties, LanguageProvider provider) {
    this(benchRepoPath, null, properties, provider);
  }

  public BenchmarkAnalyzer(String benchRepoPath, String resultsPath, PropertySet properties, LanguageProvider provider) {
    this.benchRepoPath = benchRepoPath;
    this.properties = properties;
    this.resultsPath = resultsPath;
    this.languageProvider = provider;
  }

  /**
   * This method is responsible for analyzing the desired benchmark repository according to the user
   * defined properties.
   *
   * <p>Its algorithm is pretty straightforward if you read the comments.
   */
  public void analyzeBenchmarkRepo() {

    // Instantiate the available single project analyzers of the system
    IssuesAnalyzer issuesAnalyzer = languageProvider.getIssuesAnalyzer();
    MetricsAnalyzer metricsAnalyzer = languageProvider.getMetricsAnalyzer();

    // List the projects of the repository
    File baseDir = new File(benchRepoPath);
    File[] projects = baseDir.listFiles();

    double progress = 0;

    for (File project : projects) {

      if (project.isDirectory()) {
        issuesAnalyzer.analyze(project.getAbsolutePath(), Paths.get(resultsPath, project.getName()).toAbsolutePath().normalize().toString(), properties);
        metricsAnalyzer.analyze(project.getAbsolutePath(), Paths.get(resultsPath, project.getName(), languageProvider.getMetricsImporter().getFileName()).toAbsolutePath().normalize().toString(), properties);
      }
      progress++;
    }
  }
}
