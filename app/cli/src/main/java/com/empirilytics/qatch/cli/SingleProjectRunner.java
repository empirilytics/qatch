package com.empirilytics.qatch.cli;

import com.empirilytics.qatch.analyzers.*;
import com.empirilytics.qatch.calibration.io.EvaluationResultsExporter;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.Characteristic;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Tool to execute the analysis of a single project
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class SingleProjectRunner extends Runner {

  private Project project;

  /**
   * Constructs a new SingleProjectRunner with the given QatchContext
   *
   * @param context The QatchContext
   */
  public SingleProjectRunner(QatchContext context) {
    super(context);
  }

  /** {@inheritDoc} */
  @Override
  protected void createAndLoadProject() {
    log.info("");
    log.info("**** STEP 1: Project Loader ****");
    log.info("*");
    log.info("* Loading the desired project...");
    log.info("* Please wait...");
    log.info("*");

    // Get the directory of the project
    File projectDir = new File(context.getWorkspacePath());

    // Create a Project object to store the results of the static analysis and the evaluation of
    // this project...
    project = new Project(qualityModel);

    // Set the absolute path and the name of the project
    project.setPath(context.getWorkspacePath());
    project.setName(projectDir.getName());

    log.info("* Project Name : " + project.getName());
    log.info("* Project Path : " + project.getPath());
    log.info("*");
    log.info("* Project successfully loaded..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void analyzeWorkspace() {
    // Check if the results directory exists and if not create it. Clear its contents as well.
    checkCreateClearDirectory(context.getResultsPath());

    // Print some messages...
    log.info("");
    log.info("**** STEP 2: Project Analyzer ****");
    log.info("*");
    log.info("* Analyzing the desired project");
    log.info("* Please wait...");
    log.info("*");

    // Instantiate the available single project analyzers of the system ...
    IssuesAnalyzer issues = context.getCurrentProvider().getIssuesAnalyzer();
    MetricsAnalyzer metrics = context.getCurrentProvider().getMetricsAnalyzer();

    // Analyze the project against the desired properties of each tool supported by the system...
    log.info("* Analyzing Issues");
    issues.analyze(
        context.getWorkspacePath(),
        context.getResultsPath() + "/" + project.getName(),
        project.getProperties());

    log.info("* Analyzing Metrics");
    metrics.analyze(
        context.getWorkspacePath(),
        context.getResultsPath()
            + "/"
            + project.getName()
            + "/"
            + context.getCurrentProvider().getMetricsImporter().getFileName(),
        project.getProperties());

    // Print some messages to the user
    log.info("* The analysis is finished");
    log.info("* You can find the results at : " + context.getResultsPath());
    log.info("");
  }

  /** {@inheritDoc} */
  @Override
  protected void importAnalysisResults() {
    log.info("");
    log.info("**** STEP 3: Results Importer ****");
    log.info("*");
    log.info("* Importing the results of the analysis...");
    log.info("* Please wait...");
    log.info("*");

    // Create a simple PMD and CKJM Result Importers
    IssuesImporter issueImporter = context.getCurrentProvider().getIssuesImporter();
    MetricsImporter metricImporter = context.getCurrentProvider().getMetricsImporter();

    // Get the directory with the results of the analysis
    File resultsDir = new File(context.getResultsPath() + "/" + project.getName());
    if (resultsDir.exists() && resultsDir.isDirectory()) {
      File[] results = resultsDir.listFiles();

      // For each result file found in the directory do...
      if (results != null) {
        for (File resultFile : results) {
          // Check if it is a ckjm result file
          if (!resultFile.getName().contains(metricImporter.getFileName())) {
            // Parse the issues and add them to the IssueSet Vector of the Project object
            project.addIssueSet(issueImporter.parseIssues(resultFile.getAbsolutePath()));
          } else {
            // Parse the metrics of the project and add them to the MetricSet field of the Project
            // object
            project.setMetrics(metricImporter.parseMetrics(resultFile.getAbsolutePath()));
          }
        }
      }
    }

    // Print some informative messages to the console
    log.info("*");
    log.info("* The results of the static analysis are successfully imported ");
  }

  /** {@inheritDoc} */
  @Override
  protected void aggregateAnalysisResults() {
    log.info("");
    log.info("**** STEP 4: Aggregation Process ****");

    // Print some messages
    log.info("*");
    log.info("* Aggregating the results of the project...");
    log.info("* I.e. Calculating the normalized values of its properties...");
    log.info("* Please wait...");
    log.info("*");

    // Clone the properties of the quality model to the properties of the certain project
    // for(int i = 0; i < qualityModel.getProperties().size(); i++){
    // Clone the property and add it to the PropertySet of the current project
    // Property p = (Property) qualityModel.getProperties().get(i).clone();
    // project.addProperty(p);
    // }

    // Create an empty PMDAggregator and CKJMAggregator
    Aggregator issuesAggregator = context.getCurrentProvider().getIssuesAggregator();
    Aggregator metricsAggregator = context.getCurrentProvider().getMetricsAggregator();

    // Aggregate all the analysis results
    issuesAggregator.aggregate(project);
    metricsAggregator.aggregate(project);

    // Normalize their values
    project.normalizeMeasures();

    log.info("*");
    log.info("* Aggregation process finished..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateAgainstThresholds() {
    log.info("");
    log.info("**** STEP 5: Properties Evaluation ****");
    log.info("*");
    log.info("* Evaluating the project's properties against the calculated thresholds...");
    log.info("* This will take a while...");
    log.info("*");

    // Evaluate all its properties
    project.evaluateProjectProperties();

    log.info("*");
    log.info("* The project's properties successfully evaluated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateCharacteristics() {
    log.info("");
    log.info("**** STEP 6: Characteristics Evaluation ****");
    log.info("*");
    log.info(
        "* Evaluating the project's characteristics based on the eval values of its properties...");
    log.info("* This will take a while...");
    log.info("*");

    // Clone the quality model characteristics inside the project
    // For each quality model's characteristic do...
    // for(int i = 0; i < qualityModel.getCharacteristics().size(); i++){
    // Clone the characteristic and add it to the CharacteristicSet of the current project
    // Characteristic c = (Characteristic) qualityModel.getCharacteristics().get(i).clone();
    // project.getCharacteristics().addCharacteristic(c);
    // }

    // Evaluate the project's characteristics
    project.evaluateProjectCharacteristics();

    log.info("*");
    log.info("* The project's characteristics successfully evaluated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void evaluateTqi() {
    log.info("");
    log.info("**** STEP 7: TQI Calculation ****");
    log.info("*");
    log.info("* Calculating the TQI of the project ...");
    log.info("* This will take a while...");
    log.info("*");

    // Copy the TQI object of the QM to the tqi field of this project
    // project.setTqi((Tqi)qualityModel.getTqi().clone());

    // Calculate the project's TQI
    project.calculateTQI();

    log.info("*");
    log.info("* The TQI of the project successfully evaluated..!");
  }

  /** {@inheritDoc} */
  @Override
  protected void exportEvaluationResults() {
    log.info("");
    log.info("**** STEP 8: Exporting Evaluation Results ****");
    log.info("*");
    log.info("* Exporting the results of the project evaluation...");
    log.info("* This will take a while...");
    log.info("*");

    // Clear Issues and metrics for more lightweight solution
    // TODO: Remove this ... For debugging purposes only
    if (!context.isIncludeInspectRes()) {
      project.clearIssuesAndMetrics();
    }

    EvaluationResultsExporter exporter = new EvaluationResultsExporter();

    Map<String, String> results = Maps.newHashMap();
    results.put("submissionID", project.getName());
    results.put("TQI", Double.toString(project.getTqiEval()));
    for (Map.Entry<Characteristic, Double> entry : project.getCharacteristicEvals().entrySet()) {
      results.put(entry.getKey().getName(), Double.toString(entry.getValue()));
    }

    exporter.exportToJSON(
        results,
        Paths.get(context.getResPath(), project.getName() + "_evalResults.json")
            .toAbsolutePath()
            .toString());

    log.info("* Results successfully exported..!");
    log.info("* You can find the results at : " + new File(context.getResPath()).getAbsolutePath());

//    checkCreateClearDirectory(context.getResPath());

    // Export the results
//    exporter.exportToJSON(
//        project,
//        new File(context.getResPath() + "/" + project.getName() + "_evalResults.json")
//            .getAbsolutePath());
//    log.info(
//        "* You can find the results at : "
//            + new File(context.getResPath()).getAbsolutePath()
//            + " as well..!");
  }
}
