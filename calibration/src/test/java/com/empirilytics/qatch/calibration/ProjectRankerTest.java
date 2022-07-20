package com.empirilytics.qatch.calibration;

import org.junit.jupiter.api.Test;

class ProjectRankerTest {

  @Test
  void testRank() {
    // Setup
    final BenchmarkProjects projects = new BenchmarkProjects();

    // Run the test
    ProjectRanker.rank(projects);

    // Verify the results
  }
}
