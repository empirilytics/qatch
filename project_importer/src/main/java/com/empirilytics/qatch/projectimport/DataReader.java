package com.empirilytics.qatch.projectimport;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class DataReader {

  public List<Submission> readData(String path, String language) {
    List<Submission> data = Lists.newArrayList();
    ObjectMapper mapper = new ObjectMapper();

    try {
      Path file = Paths.get(path).toAbsolutePath().normalize();
      List<Map<String, Object>> submissions = (List<Map<String, Object>>) mapper.readValue(file.toFile(), ArrayList.class);

      submissions.forEach(sub -> {
        if (Objects.equals(sub.get("language"), language)) {
          data.add(new Submission((String) sub.get("submissionId"),
                (String) sub.get("candidateFullName"),
                (Integer) sub.get("score"),
                (String) sub.get("language"),
                (String) sub.get("source"),
                (String) sub.get("role"),
                (String) sub.get("rejectionReason"),
                (String) sub.get("stage"),
                (Boolean) sub.get("goodCode")));
        }
      });
    } catch (Exception ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
    }

    return data;
  }
}
