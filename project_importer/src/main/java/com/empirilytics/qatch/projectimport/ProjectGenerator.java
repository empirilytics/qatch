package com.empirilytics.qatch.projectimport;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
public abstract class ProjectGenerator {

  public abstract void generate(String workspace, List<Submission> submissions);

  public abstract void createDirectoryStructure(String workspace, Submission submission);

  public abstract void createFiles(String base, Submission submission);

  public String getStandardImports(String language) {
    String value = "";
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(ProjectGenerator.class.getResourceAsStream("/standard_imports/" + language + ".txt")))) {
      String line = "";
      List<String> lines = Lists.newArrayList();
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      value = String.join("\n", lines);
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

    return value;
  }

  public String getDefaultDependencies(String language) {
    String value = "";
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(ProjectGenerator.class.getResourceAsStream("/standard_deps/" + language + ".txt")))) {
      String line = "";
      List<String> lines = Lists.newArrayList();
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      value = String.join("\n", lines);
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

    return value;
  }
}
