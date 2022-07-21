package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.IssuesImporter;
import com.empirilytics.qatch.core.eval.IssueSet;

/**
 * Imports the results of a pylint execution
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class PylintResultsImporter implements IssuesImporter {

    /** {@inheritDoc} */
    @Override
    public IssueSet parseIssues(String path) {
        // Apparently messages come in the following

        return null;
    }
}
