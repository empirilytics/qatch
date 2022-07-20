package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.model.PropertySet;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.openMocks;

class BenchmarkAnalyzerTest {

  @Mock private PropertySet mockProperties;

  private BenchmarkAnalyzer benchmarkAnalyzerUnderTest;

  private AutoCloseable mockitoCloseable;

  @BeforeEach
  void setUp() {
    mockitoCloseable = openMocks(this);
    benchmarkAnalyzerUnderTest =
        new BenchmarkAnalyzer("benchRepoPath", "resultsPath", mockProperties);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockitoCloseable.close();
  }

  @Test
  void testSetGUIObjects() {
    // Setup
    final ProgressBar prog = new ProgressBar(0.0);
    final ProgressIndicator progInd = new ProgressIndicator(0.0);

    // Run the test
    benchmarkAnalyzerUnderTest.setGUIObjects(prog, progInd);

    // Verify the results
  }

  @Test
  void testAnalyzeBenchmarkRepo() {
    // Setup
    // Run the test
    benchmarkAnalyzerUnderTest.analyzeBenchmarkRepo();

    // Verify the results
  }
}
