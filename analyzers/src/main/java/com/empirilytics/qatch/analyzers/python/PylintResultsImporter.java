package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.IssuesImporter;
import com.empirilytics.qatch.core.eval.Issue;
import com.empirilytics.qatch.core.eval.IssueSet;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Imports the results of a pylint execution
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class PylintResultsImporter implements IssuesImporter {

  @Setter @Getter private String ruleSetName;

  /** {@inheritDoc} */
  @Override
  public IssueSet parseIssues(String path) {
    log.info("Importing pylint results from: " + path);

    IssueSet violations = new IssueSet();
    ObjectMapper mapper = new ObjectMapper();

    try {
      Path file = Paths.get(path).toAbsolutePath().normalize();
      violations.setPropertyName(file.getFileName().toString().split("\\.")[0]);
      List<Map<String, Object>> violationList = (List<Map<String, Object>>) mapper.readValue(file.toFile(), ArrayList.class);
      violationList.forEach(node -> {
//        JsonNode pathNode = node.get("path");
//        JsonNode symbolNode = node.get("symbol"); // rulename

        int line = (Integer) node.get("line");
        int endLine = node.get("endLine") == null ? line : (Integer) node.get("endLine");
        int col = node.get("col")== null ? 0 : (Integer) node.get("col");
        int endCol = node.get("endCol") == null ? col : (Integer) node.get("endCol");
        Issue issue =
                new Issue(
                        (String) node.get("message-id"),
                        ruleSetName,
                        fullyQualified((String) node.get("module"), (String) node.get("obj")),
                        (String) node.get("message"),
                        "",
                        mapPriority((String) node.get("type")),
                        line,
                        endLine,
                        col,
                        endCol,
                        "");
        violations.addIssue(issue);
      });
    } catch (Exception ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
    }
    return violations;
  }

  private String fullyQualified(String module, String obj) {
    return module + "." + obj;
  }

  private int mapPriority(String type) {
    return switch(type) {
      case "convention" -> 1;
      case "refactor" -> 2;
      case "warning" -> 3;
      case "error" -> 4;
      case "fatal" -> 5;
      default -> 1;
    };
  }

  public static void main(String args[]) {
    PylintResultsImporter imp = new PylintResultsImporter();
    imp.parseIssues("/home/git/empirilytics/ckpm-mood/results/pylint_results.json");
  }
}
