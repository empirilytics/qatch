package com.empirilytics.qatch.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MetricTest {

  private Metric metricUnderTest;

  @BeforeEach
  void setUp() {
    metricUnderTest = new Metric("name", "tool");
  }

  @Test
  void testIsMetric() {
    assertFalse(metricUnderTest.isMetric());
  }

  @Test
  void testIsFinding() {
    assertFalse(metricUnderTest.isFinding());
  }

  @Test
  void testGetterSetter() {
    metricUnderTest.setMetricName("otherName");
    metricUnderTest.setTool("otherTool");

    assertEquals("otherName", metricUnderTest.getMetricName());
    assertEquals("otherTool", metricUnderTest.getTool());
  }
}
