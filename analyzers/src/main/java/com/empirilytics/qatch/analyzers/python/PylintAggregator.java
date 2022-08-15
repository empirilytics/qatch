package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.Aggregator;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.Property;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class PylintAggregator implements Aggregator {

  // The weights representing the relative importance of each PMD rule category
  // TODO: Define the values of the weights
  public static final int[] WEIGHT = {1, 2, 3, 4, 5};

  /** {@inheritDoc} */
  @Override
  public void aggregate(@NotNull Project project) {
    Range<Integer> range = Range.between(0, 4);
    double[] num = new double[5];

    project
        .issuesSetStream()
        .forEach(
            issueSet -> {
              Arrays.fill(num, 0);

              issueSet.stream()
                  .forEach(
                      issue -> {
                        int priority = issue.priority();
                        if (range.contains(priority)) num[priority]++;
                      });

              int value = 0;
              for (int i = 0; i < num.length; i++) value += WEIGHT[i] * num[i];

              for (Property property : project.getProperties().getProperties()) {
                if (issueSet.getPropertyName().equals(property.getName())) {
                  project.setPropertyMeasureValue(property, value);
                  break;
                }
              }
            });
  }
}
