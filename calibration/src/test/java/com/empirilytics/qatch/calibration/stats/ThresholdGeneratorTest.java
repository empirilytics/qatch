package com.empirilytics.qatch.calibration.stats;

import com.empirilytics.qatch.calibration.BenchmarkProjects;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ThresholdGeneratorTest {

  private ThresholdGenerator thresholdGeneratorUnderTest;

  @BeforeEach
  void setUp() {
    thresholdGeneratorUnderTest = new ThresholdGenerator();
  }

  @Test
  void testGetPropertyNames() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();

    // Run the test
    final String[] result = thresholdGeneratorUnderTest.getPropertyNames(projects);

    // Verify the results
    assertArrayEquals(new String[] {"result"}, result);
  }

  @Test
  void testGetProjectMeasures() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();
    final double[][] expectedResult = new double[][] {{0.0}};

    // Run the test
    final double[][] result = thresholdGeneratorUnderTest.getProjectMeasures(projects);

    // Verify the results
    assertArrayEquals(expectedResult, result);
  }

  @Test
  void testGenerateThresholds() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();
    final Map<String, Triple<Double, Double, Double>> expectedResult =
        Map.ofEntries(Map.entry("value", Triple.of(0.0, 0.0, 0.0)));

    // Run the test
    final Map<String, Triple<Double, Double, Double>> result =
        thresholdGeneratorUnderTest.generateThresholds(projects);

    // Verify the results
    assertEquals(expectedResult, result);
  }
}
