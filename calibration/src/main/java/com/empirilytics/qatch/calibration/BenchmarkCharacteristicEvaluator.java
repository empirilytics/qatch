package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

import java.util.Iterator;

/**
 * This class is responsible for the evaluation of the characteristics (i.e. calculation of the eval
 * field) of a set of projects.
 *
 * <p>Typically, this class:
 *
 * <p>1. Clones the quality model's CharacteristicSet into the CharacteristicSet of each project. 2.
 * Invokes a single project evaluator for the evaluation of the characteristics of each project
 * found in the desired set of projects.
 *
 * <p>TODO: Remove the clone method - Add a Cloner class!!!
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class BenchmarkCharacteristicEvaluator {

  /**
   * This method implements the whole functionality of this class. Its algorithm is pretty
   * straightforward if you read the comments.
   *
   * @param projects The set of projects to evaluate, cannot be null
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
      // Evaluate all its characteristics
      project.evaluateProjectCharacteristics();
      // Increment the progress counter
      progress++;
    }
    System.out.print("* Progress : " + (int) (progress / projects.size() * 100) + " %\r");
  }
}
