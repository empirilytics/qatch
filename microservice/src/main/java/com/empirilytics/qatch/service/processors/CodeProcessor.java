package com.empirilytics.qatch.service.processors;

import java.nio.file.Path;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public interface CodeProcessor {

  String processSingleFileProject(String pathString, String content);

  String getStandardImports();

  String getPackageOrDefault(Path path);
}
