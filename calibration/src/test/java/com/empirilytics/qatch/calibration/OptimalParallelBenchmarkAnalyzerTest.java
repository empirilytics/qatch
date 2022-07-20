package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.model.PropertySet;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Vector;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class OptimalParallelBenchmarkAnalyzerTest {

  @Mock private PropertySet mockProperties;

  private OptimalParallelBenchmarkAnalyzer optimalParallelBenchmarkAnalyzerUnderTest;

  private AutoCloseable mockitoCloseable;

  @BeforeEach
  void setUp() {
    mockitoCloseable = openMocks(this);
    optimalParallelBenchmarkAnalyzerUnderTest =
        new OptimalParallelBenchmarkAnalyzer("benchRepoPath", "resultsPath", mockProperties);
  }

  @AfterEach
  void tearDown() throws Exception {
    mockitoCloseable.close();
  }

  @Test
  void testSetGUIObjects() {
    // Setup
    final Vector<ProgressBar> progBars = new Vector(List.of(new ProgressBar(0.0)));
    final Vector<ProgressIndicator> progIndicators =
        new Vector(List.of(new ProgressIndicator(0.0)));
    final TextArea console = new TextArea("text");

    // Run the test
    optimalParallelBenchmarkAnalyzerUnderTest.setGUIObjects(progBars, progIndicators, console);

    // Verify the results
  }

  @Test
  void testAnalyzeBenchmarkRepo() {
    // Setup
    when(mockProperties.iterator()).thenReturn(null);
    when(mockProperties.size()).thenReturn(0);

    // Run the test
    optimalParallelBenchmarkAnalyzerUnderTest.analyzeBenchmarkRepo();

    // Verify the results
  }

  @Test
  void testPmdThreadCreator() {
    // Setup
    final PropertySet pmdPropSet = new PropertySet();

    // Run the test
    optimalParallelBenchmarkAnalyzerUnderTest.pmdThreadCreator(pmdPropSet, 0, 0);

    // Verify the results
  }

  @Test
  void testPrintBenchmarkRepoContents() {
    // Setup
    // Run the test
    optimalParallelBenchmarkAnalyzerUnderTest.printBenchmarkRepoContents();

    // Verify the results
  }
}
