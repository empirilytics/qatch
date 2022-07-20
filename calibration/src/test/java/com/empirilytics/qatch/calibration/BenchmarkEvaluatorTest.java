package com.empirilytics.qatch.calibration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenchmarkEvaluatorTest {

  private BenchmarkEvaluator benchmarkEvaluatorUnderTest;

  @BeforeEach
  void setUp() {
    benchmarkEvaluatorUnderTest = new BenchmarkEvaluator();
  }

  @Test
  void testEvaluateProjects() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();

    // Run the test
    benchmarkEvaluatorUnderTest.evaluateProjects(projects);

    // Verify the results
  }
}
