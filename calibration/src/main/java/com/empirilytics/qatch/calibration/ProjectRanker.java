package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

import java.util.Collections;
import java.util.Comparator;

/**
 * This class is responsible for sorting the a set of projects in a descending order, based on the
 * field "eval".
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class ProjectRanker {

  /** Specify the comparator for the sorting process... */
  static final Comparator<Project> TQI_ORDER =
      new Comparator<Project>() {
        public int compare(Project p1, Project p2) {
          return Double.compare(p2.getTqiEval(), p1.getTqiEval());
        }
      };

  /**
   * A method for implementing the desired sorting...
   *
   * @param projects The projects to be ranked, cannot be null
   */
  public static void rank(@NonNull BenchmarkProjects projects) {
    Collections.sort(projects.getProjects(), TQI_ORDER);
  }
}
