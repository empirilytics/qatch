package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.model.PropertySet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BenchmarkAggregatorTest {

  private BenchmarkAggregator benchmarkAggregatorUnderTest;

  @BeforeEach
  void setUp() {
    benchmarkAggregatorUnderTest = new BenchmarkAggregator();
  }

  @Test
  void testAggregateProjects() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();
    final PropertySet properties = new PropertySet();

    // Run the test
    final BenchmarkProjects result =
        benchmarkAggregatorUnderTest.aggregateProjects(projects, properties);

    // Verify the results
  }

  @Test
  void testNormalizeProperties() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();

    // Run the test
    benchmarkAggregatorUnderTest.normalizeProperties(projects);

    // Verify the results
  }
}
