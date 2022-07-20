package com.empirilytics.qatch.analyzers;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

/**
 * Base interface for tool results aggregation
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
public interface Aggregator {

  /**
   * Aggregates data from a tool for the provided project
   *
   * @param project Project to shich the aggregated data belongs, cannot be null
   */
  void aggregate(@NonNull Project project);
}
