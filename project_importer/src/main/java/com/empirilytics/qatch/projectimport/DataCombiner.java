package com.empirilytics.qatch.projectimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class DataCombiner {

  public void combineData(String inputFile, String resultsDir, String outputFile) {
    Map<String, Results> results = readResults(resultsDir);
    Map<String, Submission> submissions = readSubmissions(inputFile, results.keySet());
    writeCombinedData(outputFile, submissions, results);
  }

  public void writeCombinedData(String path, Map<String, Submission> submissions, Map<String, Results> results) {
    try (CSVPrinter printer = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT)) {
      printer.printRecord(
              "Submission ID",
              "Candidate",
              "Language",
              "Score",
              "Maintainability",
              "Reliability",
              "Security",
              "Performance Efficiency",
              "Total Quality",
              "Good Code?"
      );

      for (String key : submissions.keySet()) {
        printer.printRecord(
                key,
                submissions.get(key).name(),
                submissions.get(key).lang(),
                submissions.get(key).score(),
                results.get(key).maintainability(),
                results.get(key).reliability(),
                results.get(key).security(),
                results.get(key).efficiency(),
                results.get(key).tqi(),
                submissions.get(key).goodCode()
        );
      }
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }

  public Map<String, Results> readResults(String path) {
    Map<String, Results> map = Maps.newHashMap();
    ObjectMapper mapper = new ObjectMapper();

    Path base = Paths.get(path);
    try(var stream = Files.newDirectoryStream(base)) {
      stream.forEach(file -> {
        if (Files.isRegularFile(file) && file.getFileName().toString().endsWith(".json")) {
          try {
            Map<String, Object> data = (Map<String, Object>) mapper.readValue(file.toFile(), Map.class);

            Results res = new Results(
                    (String) data.get("submissionID"),
                    Double.parseDouble((String) data.get("Maintainability")),
                    Double.parseDouble((String) data.get("Reliability")),
                    Double.parseDouble((String) data.get("Security")),
                    Double.parseDouble((String) data.get("Performance_Efficiency")),
                    Double.parseDouble((String) data.get("TQI"))
            );

            map.put(res.submissionId(), res);
          } catch (IOException e) {
            log.error(e.getMessage());
          }
        }
      });
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

    return map;
  }

  public Map<String, Submission> readSubmissions(String path, Set<String> submissionIds) {
    Map<String, Submission> data = Maps.newHashMap();
    ObjectMapper mapper = new ObjectMapper();

    try {
      Path file = Paths.get(path).toAbsolutePath().normalize();
      List<Map<String, Object>> submissions = (List<Map<String, Object>>) mapper.readValue(file.toFile(), ArrayList.class);

      submissions.forEach(sub -> {
        if (submissionIds.contains((String) sub.get("submissionId"))) {
          Submission submission = new Submission((String) sub.get("submissionId"),
                (String) sub.get("candidateFullName"),
                (Integer) sub.get("score"),
                (String) sub.get("language"),
                (String) sub.get("source"),
                (String) sub.get("role"),
                (String) sub.get("rejectionReason"),
                (String) sub.get("stage"),
                (Boolean) sub.get("goodCode"));
          data.put(submission.id(), submission);
        }
      });
    } catch (Exception ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
    }

    return data;
  }
}
