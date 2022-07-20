package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import com.empirilytics.qatch.core.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BenchmarkProjectsTest {

  private BenchmarkProjects benchmarkProjectsUnderTest;

  @BeforeEach
  void setUp() {
    benchmarkProjectsUnderTest = new BenchmarkProjects();
  }

  @Test
  void testAddProject() {
    // Setup
    final CharacteristicSet characteristicSet = new CharacteristicSet();
    final Weights weights = new Weights();
    characteristicSet.setCharacteristics(
        List.of(new Characteristic("name", "standard", "description", weights)));
    final Weights weights1 = new Weights();
    final Project project =
        new Project(
            "name",
            new QualityModel("name", new PropertySet(), characteristicSet, new Tqi(weights1)));

    // Run the test
    benchmarkProjectsUnderTest.addProject(project);

    // Verify the results
  }

  @Test
  void testGetProject() {
    // Setup
    // Run the test
    final Project result = benchmarkProjectsUnderTest.getProject(0);

    // Verify the results
  }

  @Test
  void testClearProjects() {
    // Setup
    // Run the test
    benchmarkProjectsUnderTest.clearProjects();

    // Verify the results
  }

  @Test
  void testContainsProject() {
    // Setup
    final CharacteristicSet characteristicSet = new CharacteristicSet();
    final Weights weights = new Weights();
    characteristicSet.setCharacteristics(
        List.of(new Characteristic("name", "standard", "description", weights)));
    final Weights weights1 = new Weights();
    final Project project =
        new Project(
            "name",
            new QualityModel("name", new PropertySet(), characteristicSet, new Tqi(weights1)));

    // Run the test
    final boolean result = benchmarkProjectsUnderTest.containsProject(project);

    // Verify the results
    assertFalse(result);
  }

  @Test
  void testIsEmpty() {
    // Setup
    // Run the test
    final boolean result = benchmarkProjectsUnderTest.isEmpty();

    // Verify the results
    assertFalse(result);
  }

  @Test
  void testIterator() {
    // Setup
    // Run the test
    final Iterator<Project> result = benchmarkProjectsUnderTest.iterator();

    // Verify the results
  }

  @Test
  void testIndexOfProject() {
    // Setup
    final CharacteristicSet characteristicSet = new CharacteristicSet();
    final Weights weights = new Weights();
    characteristicSet.setCharacteristics(
        List.of(new Characteristic("name", "standard", "description", weights)));
    final Weights weights1 = new Weights();
    final Project project =
        new Project(
            "name",
            new QualityModel("name", new PropertySet(), characteristicSet, new Tqi(weights1)));

    // Run the test
    final int result = benchmarkProjectsUnderTest.indexOfProject(project);

    // Verify the results
    assertEquals(0, result);
  }

  @Test
  void testRemoveProject1() {
    // Setup
    // Run the test
    benchmarkProjectsUnderTest.removeProject(0);

    // Verify the results
  }

  @Test
  void testRemoveProject2() {
    // Setup
    final CharacteristicSet characteristicSet = new CharacteristicSet();
    final Weights weights = new Weights();
    characteristicSet.setCharacteristics(
        List.of(new Characteristic("name", "standard", "description", weights)));
    final Weights weights1 = new Weights();
    final Project project =
        new Project(
            "name",
            new QualityModel("name", new PropertySet(), characteristicSet, new Tqi(weights1)));

    // Run the test
    benchmarkProjectsUnderTest.removeProject(project);

    // Verify the results
  }

  @Test
  void testSize() {
    // Setup
    // Run the test
    final int result = benchmarkProjectsUnderTest.size();

    // Verify the results
    assertEquals(0, result);
  }

  @Test
  void testToArray() {
    // Setup
    // Run the test
    final Project[] result = benchmarkProjectsUnderTest.toArray();

    // Verify the results
  }

  @Test
  void testToString() {
    // Setup
    // Run the test
    final String result = benchmarkProjectsUnderTest.toString();

    // Verify the results
    assertEquals("projects", result);
  }

  @Test
  void testClearIssuesAndMetrics() {
    // Setup
    // Run the test
    benchmarkProjectsUnderTest.clearIssuesAndMetrics();

    // Verify the results
  }

  @Test
  void testSortProjects() {
    // Setup
    // Run the test
    benchmarkProjectsUnderTest.sortProjects();

    // Verify the results
  }
}
