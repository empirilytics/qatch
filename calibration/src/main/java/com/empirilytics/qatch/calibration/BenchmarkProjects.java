package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.eval.Project;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Vector;

/**
 * This class represent a set of projects that will be evaluated. It is just a Vector of Project
 * objects.
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class BenchmarkProjects {

  private Vector<Project> projects;

  /** The constructor method of the class. */
  public BenchmarkProjects() {
    projects = new Vector<>();
  }

  /**
   * Setters and getters.
   *
   * @return the set of projects
   */
  public Vector<Project> getProjects() {
    return projects;
  }

  /**
   * Sets the set of benchmark projects
   *
   * @param projects The set of benchmark projects, cannot be null
   */
  public void setProjects(@NonNull Vector<Project> projects) {
    this.projects = projects;
  }

  /**
   * Adds a Project object in the project vector.
   *
   * @param project a new project to the benchmark set, cannot be null
   */
  public void addProject(@NonNull Project project) {
    this.projects.add(project);
  }

  /**
   * Returns the Project object placed in the index position of project vector.
   *
   * @param index the index of the project to get
   * @return Project at the provided index, or null if the index is out of bounds
   */
  public Project getProject(int index) {
    if (index < 0 || index >= projects.size()) return null;
    return projects.get(index);
  }

  /** Clears the vector that contains the Projects of the Benchmark. */
  public void clearProjects() {
    projects.clear();
  }

  /**
   * Searches for an project and returns the index of the first occurrence.
   *
   * @param project The project to check if is contained, cannot be null
   * @return boolean true if the project is contained, false if the project is not
   */
  public boolean containsProject(@NonNull Project project) {
    return projects.contains(project);
  }

  /**
   * Checks if the project vector is empty.
   *
   * @return true if the set of projects is empty, false otherwise
   */
  public boolean isEmpty() {
    return projects.isEmpty();
  }

  /**
   * Creates an iterator for the projects Vector.
   *
   * @return an intertor of the projects
   */
  public Iterator<Project> iterator() {
    return projects.iterator();
  }

  /**
   * Returns the index that a project has inside the projects vector.
   *
   * @param project The project to find the index of, cannot be null
   * @return index of the project, or -1 if it is not contained
   */
  public int indexOfProject(@NonNull Project project) {
    return projects.indexOf(project);
  }

  /**
   * Removes a certain project from the projects vector.
   *
   * @param index the index of the project to be removed, must be in range (0..size] where size is
   *     the number of projects
   */
  public void removeProject(int index) {
    projects.remove(index);
  }

  /**
   * Removes the first occurrence of a desired project from the projects vector.
   *
   * @param project The project to be removed, cannot be null
   */
  public void removeProject(@NonNull Project project) {
    projects.remove(project);
  }

  /**
   * Returns the size of the projects vector.
   *
   * @return The number of projects contained
   */
  public int size() {
    return projects.size();
  }

  /**
   * Returns the Array representation of the projects vector.
   *
   * @return an array of the contained projects
   */
  public Project[] toArray() {
    return (Project[]) projects.toArray();
  }

  /**
   * Returns the String representation of the projects vector.
   *
   * @return A string representation of the projects
   */
  public String toString() {
    return projects.toString();
  }

  /**
   * Method for freeing memory. Deletes all the analysis results that were imported previously in
   * the system for aggregation and evaluation.
   */
  public void clearIssuesAndMetrics() {
    // For each project of the Benchmark Repository do ...
    for (int i = 0; i < projects.size(); i++) {
      // Clear the issues and the metrics of the current project
      projects.get(i).clearIssuesAndMetrics();
    }
  }

  /**
   * This method is responsible for sorting the projects of the projects vector in a descending
   * order based on their "eval" field.
   */
  public void sortProjects() {
    Project.sort("eval", projects);
  }
}
