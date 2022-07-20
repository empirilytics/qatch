package com.empirilytics.qatch.calibration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenchmarkTqiEvaluatorTest {

  private BenchmarkTqiEvaluator benchmarkTqiEvaluatorUnderTest;

  @BeforeEach
  void setUp() {
    benchmarkTqiEvaluatorUnderTest = new BenchmarkTqiEvaluator();
  }

  @Test
  void testEvaluateProjects() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();

    // Run the test
    benchmarkTqiEvaluatorUnderTest.evaluateProjects(projects);

    // Verify the results
  }
}
