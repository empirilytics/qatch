package com.empirilytics.qatch.projectimport;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 *
 * @param submissionId
 * @param maintainability
 * @param reliability
 * @param security
 * @param efficiency
 * @param tqi
 */
public record Results(String submissionId, double maintainability, double reliability, double security, double efficiency, double tqi) {}
