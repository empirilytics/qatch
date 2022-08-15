package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.analyzers.Aggregator;
import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.PropertySet;

import java.util.List;

/**
 * This class is responsible for calculating the fields: normalizer, value and normValue of the
 * properties of a set of projects (i.e. BenchmarkProjects). It takes advantage of the data that are
 * stored in the objects: - IssueSet - MetricSet of each project, in order to calculate those
 * fields.
 *
 * <p>Typically, it iterates through the list of projects and for each project it calls the
 * aggregate() method of the single project aggregators: - CKJMAggregator - PMDAggregator which are
 * responsible for the "aggregation" of a single project.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
// TODO: Check for parallel alternative - if it is needed and only if it is possible
public class BenchmarkAggregator {

  /**
   * This method is responsible for the aggregation of the properties of a set of projects. The
   * whole functionality of the aggregator is summarized in three steps:
   *
   * <ol>
   *   <li>Clone the PropertySet of the model to the PropertySet of each project
   *   <li>Call single project aggregators for each project of the project set
   *   <li>Normalize the values of the properties of the set of projects
   * </ol>
   *
   * @param projects The projects to aggregate
   * @param properties The properties to be aggregated
   */
  public List<Project> aggregateProjects(List<Project> projects, PropertySet properties, LanguageProvider provider) {

    Aggregator issuesAggregator = provider.getIssuesAggregator();
    Aggregator metricsAggregator = provider.getMetricsAggregator();

    projects.forEach(project -> {
      issuesAggregator.aggregate(project);
      metricsAggregator.aggregate(project);
    });

    normalizeProperties(projects);

    return projects;
  }

  /**
   * This method is responsible for calculating the normalized value (normValue) of the properties
   * of each project found inside a set of projects.
   *
   * @param projects The projects for which properties should be normalized
   */
  public void normalizeProperties(List<Project> projects) {
    projects.forEach(Project::normalizeMeasures);
  }
}
