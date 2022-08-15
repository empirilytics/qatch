package com.empirilytics.qatch.analyzers.js;

import com.empirilytics.qatch.analyzers.IssuesImporter;
import com.empirilytics.qatch.core.eval.IssueSet;
import lombok.extern.log4j.Log4j2;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class ESLintResultsImporter implements IssuesImporter {
  @Override
  public IssueSet parseIssues(String path) {
    return null;
  }
}
