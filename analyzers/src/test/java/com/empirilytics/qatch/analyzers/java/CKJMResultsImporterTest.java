package com.empirilytics.qatch.analyzers.java;

import com.empirilytics.qatch.core.eval.MetricSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CKJMResultsImporterTest {

  private CKJMResultsImporter ckjmResultsImporterUnderTest;

  @BeforeEach
  void setUp() {
    ckjmResultsImporterUnderTest = new CKJMResultsImporter();
  }

  @Test
  void testParseMetrics() {
    // Setup
    // Run the test
    final MetricSet result = ckjmResultsImporterUnderTest.parseMetrics("path");

    // Verify the results
  }

  @Test
  void testGetFileName() {
    assertEquals("ckjm.xml", ckjmResultsImporterUnderTest.getFileName());
  }
}
