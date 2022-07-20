package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

import java.util.Iterator;

/**
 * This class is responsible for calculating the TQI of a set of projects.
 *
 * <p>Typically, it does the following:
 *
 * <ol>
 *   <li>It clones the Tqi object of the quality model into the tqi field of each project.
 *   <li>It calls the calculateTqi() method for each project of the desired set of projects, that
 *       calculates the Tqi of a single project.
 * </ol>
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class BenchmarkTqiEvaluator {

  /**
   * This method implements the whole functionality of this class. Its algorithm is pretty
   * straightforward if you read the inline comments.
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

      // Calculate the project's TQI
      project.calculateTQI();

      // Increment the progress counter by one...
      progress++;
    }
    System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");
  }
}
