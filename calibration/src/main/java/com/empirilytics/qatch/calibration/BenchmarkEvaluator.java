package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

import java.util.Iterator;

/**
 * This class is responsible for evaluating the properties of a set of projects (e.g. Benchmark
 * Projects).
 *
 * <p>It creates a single project evaluator and applies it on each project found in a
 * BenchmarkProjects object in order to calculate the eval field of each Property.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class BenchmarkEvaluator {

  /**
   * Evaluate the provided set of benchmark projects
   *
   * @param projects The projects to be evaluated, cannot be null
   */
  public void evaluateProjects(@NonNull BenchmarkProjects projects) {
    // Iterate through the set of projects
    Iterator<Project> iterator = projects.iterator();
    double progress = 0;
    while (iterator.hasNext()) {
      // TODO: Remove this print...
      System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");
      // Get the current project
      Project project = iterator.next();
      // Evaluate all its properties
      project.evaluateProjectProperties();
      progress++;
    }
    System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");
  }
}
