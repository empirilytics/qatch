package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.PropertySet;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BenchmarkAggregatorTest {

  private BenchmarkAggregator benchmarkAggregatorUnderTest;
  private LanguageProvider provider;

  @BeforeEach
  void setUp() {
    benchmarkAggregatorUnderTest = new BenchmarkAggregator();
  }

  @Test
  void testAggregateProjects() {
    // Setup
    final List<Project> projects = Lists.newArrayList();
    final PropertySet properties = new PropertySet();

    // Run the test
    final List<Project> result =
        benchmarkAggregatorUnderTest.aggregateProjects(projects, properties, provider);

    // Verify the results
  }

  @Test
  void testNormalizeProperties() {
    // Setup
    final List<Project> projects = Lists.newArrayList();

    // Run the test
    benchmarkAggregatorUnderTest.normalizeProperties(projects);

    // Verify the results
  }
}
