package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.IssuesAnalyzer;
import com.empirilytics.qatch.core.model.PropertySet;
import org.jetbrains.annotations.NotNull;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class PylintAnalyzer implements IssuesAnalyzer {

    public PylintAnalyzer(String pylintPath, String resultsPath, String ruleSetPath) {

    }

    public void analyze(@NotNull String src, @NotNull String dest, @NotNull String ruleset, @NotNull String filename) {

  }

	//Alternative
	//public abstract void analyze(String src, String dest, String ruleset, String filename);

	public void analyze(@NotNull String src, @NotNull String dest, @NotNull PropertySet properties) {

  }
}
