package com.empirilytics.qatch.calibration.stats;

import com.empirilytics.qatch.calibration.BenchmarkProjects;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.Property;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

/**
 * This class generates the thresholds for each measure in the model (it is a replacement of an
 * original R file)
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class ThresholdGenerator {

  /**
   * Retrieves the names of the properties within the Benchmark Projects set
   *
   * @param projects Set of benchmark projects, cannot be null
   * @return Array of property names of the benchmark projects
   */
  public String[] getPropertyNames(@NonNull BenchmarkProjects projects) {
    Project proj = projects.getProject(0);
    List<String> names = Lists.newArrayList();
    proj.getProperties().iterator().forEachRemaining(x -> names.add(x.getName()));
    return names.toArray(new String[0]);
  }

  /**
   * Retrieves the measures for each of the benchmark projects
   *
   * @param projects The BenchmarkProjects, cannot be null
   * @return 2D array of values of the measures of each project
   */
  public double[][] getProjectMeasures(@NonNull BenchmarkProjects projects) {
    double[][] matrix =
        new double[projects.getProjects().size()][projects.getProject(0).getProperties().size()];
    for (int i = 0; i < projects.getProjects().size(); i++) {
      Project project = projects.getProjects().get(i);
      for (int j = 0; j < projects.getProject(i).getProperties().size(); j++) {
        Property property = project.getProperties().get(j);
        double normValue = project.normalizeMeasure(property);
        matrix[i][j] = normValue;
      }
    }
    return matrix;
  }

  /**
   * Generates the thresholds for the measures
   *
   * @param projects BenchmarkProjects, cannot be null
   * @return A Map (keyed by the property names) of the triples representing the threshold
   *     distributions of each measure
   */
  public Map<String, Triple<Double, Double, Double>> generateThresholds(
      @NonNull BenchmarkProjects projects) {
    double[][] measures = getProjectMeasures(projects);
    String[] propertyNames = getPropertyNames(projects);

    if (propertyNames.length != measures.length) throw new RuntimeException();

    Map<String, Triple<Double, Double, Double>> data = Maps.newHashMap();

    for (int i = 0; i < propertyNames.length; i++) {
      DescriptiveStatistics stats = new DescriptiveStatistics();
      double[] property = measures[i];

      Arrays.stream(property).forEach(stats::addValue);

      double iqr = stats.getPercentile(0.25) - stats.getPercentile(0.25);

      double t1 = 0.0;
      double t3 = 0.0;
      Arrays.sort(property);
      double t2 = stats.getPercentile(50);

      // calculate the lower threshold (minimum non-outlier observation)
      double lowerThreshold = stats.getPercentile(0.25) - 1.5 * iqr;
      double[] part = Arrays.stream(property).filter((x) -> x >= lowerThreshold).toArray();
      OptionalDouble opt = Arrays.stream(part).min();
      if (opt.isPresent()) t1 = opt.getAsDouble();

      // calculate the upper threshold (maximum non-outlier observation)
      double upperThreshold = stats.getPercentile(0.75) + 1.5 * iqr;
      part = Arrays.stream(property).filter((x) -> x <= upperThreshold).toArray();
      opt = Arrays.stream(part).max();
      if (opt.isPresent()) t3 = opt.getAsDouble();

      data.put(propertyNames[i], Triple.of(t1, t2, t3));
    }

    return data;
  }
}
