package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.analyzers.java.CKJMAggregator;
import com.empirilytics.qatch.analyzers.java.PMDAggregator;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.PropertySet;

import java.util.Iterator;

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
  public BenchmarkProjects aggregateProjects(BenchmarkProjects projects, PropertySet properties) {

    // Clone the properties of the Quality Model on each project
    // cloneProperties(projects, properties); TODO Remove this as we shifted to Model Instances

    // Create an empty PMDAggregator and CKJMAggregator
    PMDAggregator pmd = new PMDAggregator();
    CKJMAggregator ckjm = new CKJMAggregator();

    // Aggregate all the projects
    double progress = 0;
    for (int i = 0; i < projects.size(); i++) {
      System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");
      pmd.aggregate(projects.getProject(i));
      ckjm.aggregate(projects.getProject(i));
      progress++;
    }
    System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");

    // Normalize all the values
    normalizeProperties(projects);

    // Optional
    return projects;
  }

  /**
   * This method is responsible for calculating the normalized value (normValue) of the properties
   * of each project found inside a set of projects.
   *
   * @param projects The projects for which properties should be normalized
   */
  public void normalizeProperties(BenchmarkProjects projects) {

    // Iterate through all the projects
    Iterator<Project> iterator = projects.iterator();

    while (iterator.hasNext()) {

      // Get the current project
      Project project = iterator.next();

      // For each property do...
      project.normalizeMeasures();
    }
  }
}
