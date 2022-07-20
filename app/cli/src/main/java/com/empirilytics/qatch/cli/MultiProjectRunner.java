package com.empirilytics.qatch.cli;

import com.empirilytics.qatch.calibration.*;
import com.empirilytics.qatch.calibration.io.BenchmarkResultImporter;
import com.empirilytics.qatch.calibration.io.EvaluationResultsExporter;
import com.empirilytics.qatch.core.eval.Project;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * This class contains the main script for evaluating all the projects of a workspace based on a
 * desired Quality Model.
 *
 * <p>It gives you the option to analyze the project or to use the results of the previous analysis.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class MultiProjectRunner extends Runner {

  // User defined fields
  private BenchmarkProjects projects;

  /**
   * Constructs a new MultiProjectRunner to evaluate multiple projects in a base workspece
   *
   * @param context The QatchContext for this runner
   */
  public MultiProjectRunner(QatchContext context) {
    super(context);
  }

  /** {@inheritDoc} */
  @Override
  protected void exportEvaluationResults() {
    log.info("\n**** STEP 7: Exporting Evaluation Results ****");
    log.info("*");
    log.info("* Exporting the results of the project evaluation...");
    log.info("* This will take a while...");
    log.info("*");

    // Clear Issues and metrics for more lightweight solution
    if (!context.isIncludeInspectRes()) {
      Iterator<Project> iterator = projects.iterator();
      while (iterator.hasNext()) {
        Project project = iterator.next();
        project.clearIssuesAndMetrics();
      }
    }

    // Export the evaluation results
    EvaluationResultsExporter exporter = new EvaluationResultsExporter();
    exporter.exportToJSON(
        projects, new File(context.getResPath() + "/evaluationResults.json").getAbsolutePath());
    exporter.exportPropValuesAndTqiToXls(
        projects,
        new File(context.getResPath() + "/evaluationResults.xls").getAbsolutePath(),
        true,
        true,
        true,
        true,
        true);

    // Export the results to the predefined directory as well
    checkCreateClearDirectory(context.getResultsPath());
    exporter.exportToJSON(
        projects,
        new File(
                Paths.get(context.getResultsPath(), "evaluationResults.json")
                    .toAbsolutePath()
                    .toString())
            .getAbsolutePath());
    exporter.exportPropValuesAndTqiToXls(
        projects,
        new File(
                Paths.get(context.getResultsPath(), "evaluationResults.xls")
                    .toAbsolutePath()
                    .toString())
            .getAbsolutePath(),
        true,
        true,
        true,
        true,
        true);

    // Export a project ranking...
    ProjectRanker.rank(projects);
    exporter.exportToJSON(
        projects,
        new File(
                Paths.get(context.getResultsPath(), "rankedEvaluationResults.json")
                    .toAbsolutePath()
                    .toString())
            .getAbsolutePath());
    exporter.exportToJSON(
        projects,
        new File(
                Paths.get(context.getResultsPath(), "rankedEvaluationResults.json")
                    .toAbsolutePath()
                    .toString())
            .getAbsolutePath());

    log.info("* Results successfully exported..!");
    log.info(
        "* You can find the results at        : "
            + new File(context.getResPath()).getAbsolutePath());
    log.info("* You can find the ranked results at : " + new File(context.getResultsPath()));
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateTqi() {
    log.info("");
    log.info("**** STEP 6: TQI Calculation ****");
    log.info("*");
    log.info("* Calculating the TQI of each project ...");
    log.info("* This will take a while...");
    log.info("*");

    BenchmarkTqiEvaluator tqiEvaluator = new BenchmarkTqiEvaluator();
    tqiEvaluator.evaluateProjects(projects);

    log.info("*");
    log.info("* The TQI of the projects successfully calculated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateCharacteristics() {
    log.info("");
    log.info("**** STEP 5: Characteristics Evaluation ****");
    log.info("*");
    log.info(
        "* Evaluating the projects' characteristics based on the eval values of their properties...");
    log.info("* This will take a while...");
    log.info("*");

    BenchmarkCharacteristicEvaluator benchCharEval = new BenchmarkCharacteristicEvaluator();
    benchCharEval.evaluateProjects(projects);

    log.info("*");
    log.info("* The projects' characteristics are successfully evaluated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateAgainstThresholds() {
    log.info("");
    log.info("**** STEP 4: Properties Evaluation ****");
    log.info("*");
    log.info("* Evaluating the projects' properties against the calculated thresholds...");
    log.info("* This will take a while...");
    log.info("*");

    // Create an empty Benchmark Evaluator
    BenchmarkEvaluator benchEval = new BenchmarkEvaluator();
    benchEval.evaluateProjects(projects);

    log.info("*");
    log.info("* The projects' properties are successfully evaluated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void aggregateAnalysisResults() {
    log.info("");
    log.info("**** STEP 3: Aggregation Process ****");
    log.info("*");
    log.info("* Aggregating the results of each project...");
    log.info("* I.e. Calculating the normalized values of their properties...");
    log.info("* Please wait...");
    log.info("*");

    // Create an empty BenchmarkAggregator and aggregate the metrics of the project
    BenchmarkAggregator benchAggregator = new BenchmarkAggregator();
    benchAggregator.aggregateProjects(projects, qualityModel.getProperties());

    log.info("*");
    log.info("* Aggregation process finished..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void importAnalysisResults() {
    log.info("");
    log.info("**** STEP 2 : Benchmark Importer ****");
    log.info("*");
    log.info("* Importing the results of the analysis...");
    log.info("* Please wait...");
    log.info("*");

    // Create an empty BenchmarkImporter
    BenchmarkResultImporter benchmarkImporter = new BenchmarkResultImporter();

    // Start importing all the results of the static analysis found in the results folder
    projects = benchmarkImporter.importResults(getCorrectDirectory());

    // Print some informative messages to the console
    log.info("*");
    log.info("* The results are successfully imported..! ");
  }

  /** {@inheritDoc} */
  @Override
  protected void createAndLoadProject() {}

  /** {@inheritDoc} */
  @Override
  protected void analyzeWorkspace() {
    String analysisResPath = getCorrectDirectory();

    // Check if the results directory exists and if not create it. Clear it's contents as well.
    checkCreateClearDirectory(getCorrectDirectory());

    // Snippet copied from the generic script of the Model Designer
    log.info("");
    log.info("**** STEP 1 : Workspace Analyzer ****");
    log.info("*");
    log.info("* Analyzing the projects of your workspace");
    log.info("* Please wait...");
    log.info("*");

    // TODO: Create a parent Benchmark Analyzer so that you can use it to move the common commands
    // outside the if statement
    if (!context.isParallelAnalysis()) {
      // Instantiate the serial benchmark analyzer
      BenchmarkAnalyzer benchmarkAnal = new BenchmarkAnalyzer();

      // Set the repository and the desired properties to the benchmark analyzer
      benchmarkAnal.setBenchRepoPath(context.getWorkspacePath());
      benchmarkAnal.setProperties(qualityModel.getProperties());
      benchmarkAnal.setResultsPath(analysisResPath);

      // Start the analysis of the workspace
      benchmarkAnal.analyzeBenchmarkRepo();
    } else {
      // Instantiate the parallel benchmark analyzer
      OptimalParallelBenchmarkAnalyzer benchmarkAnal = new OptimalParallelBenchmarkAnalyzer();

      // Set the repository and the desired properties to the benchmark analyzer
      benchmarkAnal.setBenchRepoPath(context.getWorkspacePath());
      benchmarkAnal.setProperties(qualityModel.getProperties());
      benchmarkAnal.setResultsPath(analysisResPath);

      // Start the analysis of the workspace
      benchmarkAnal.analyzeBenchmarkRepo();
    }

    // Print some messages to the user
    log.info("*");
    log.info("* The analysis is finished..!");
    log.info("* You can find the results at : " + new File(analysisResPath).getAbsolutePath());
    log.info("");
  }

  /**
   * Selects the correct directory (either the benchmark results path or the workspace path)
   * depending on the runner configuration
   *
   * @return The path to the selected directory
   */
  private String getCorrectDirectory() {
    if (context.isUseBenchmarksResultDir()) {
      return context.getBenchmarkResPath();
    } else {
      return context.getWorkspacePath();
    }
  }
}
