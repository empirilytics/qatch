package com.empirilytics.qatch.calibration.io;

import com.empirilytics.qatch.analyzers.IssuesImporter;
import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.analyzers.MetricsImporter;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.QualityModel;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is responsible for importing all the results that the BenchmarkAnalyzer exported in
 * the fixed results directory.
 *
 * <p>Typically it does the following: 1. It searches the results directory for project results 2.
 * For each folder it creates a Project object 3. It calls the pmd and ckjm importers in order to
 * import the results of each project and stores them in the corresponding fields of the Project
 * object 4. It returns an object of type BenchmarkProjects. This object is simply a Vector of
 * Project objects containing the imported project results.
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class BenchmarkResultImporter {

  /**
   * The method that implements the whole functionality of this class.
   *
   * @param path The path from which to import results, cannot be null
   */
  public void importResults(@NonNull List<Project> projects, @NonNull String path, LanguageProvider provider, QualityModel model) {
    IssuesImporter issuesImporter = provider.getIssuesImporter();
    MetricsImporter metricsImporter = provider.getMetricsImporter();

    log.info("Analysis Path : " + path);
    Path resultsDir = Paths.get(path).toAbsolutePath().normalize();

    try (Stream<Path> stream = Files.list(resultsDir).filter(Files::isDirectory)) {
      stream.forEach(projectDir -> {
        Project project = new Project(projectDir.getFileName().toString(), model);
        project.setPath(projectDir.toAbsolutePath().normalize().toString());

        try (Stream<Path> contents = Files.list(projectDir)) {
          contents.forEach(resultFile -> {
            if (!resultFile.getFileName().toString().contains(provider.getMetricsImporter().getFileName())) {
              project.addIssueSet(issuesImporter.parseIssues(resultFile.toAbsolutePath().normalize().toString()));
            } else {
              project.setMetrics(metricsImporter.parseMetrics(resultFile.toAbsolutePath().normalize().toString()));
            }
          });
        } catch (IOException ex) {
          log.error(ex.getMessage());
        }

        projects.add(project);
      });
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }
}
