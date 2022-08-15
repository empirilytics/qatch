package com.empirilytics.qatch.analyzers.js;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author IsaacGriffith
 * @version 2.0.0
 */
public class JavaScriptProvider extends LanguageProvider {

    public static LanguageProvider instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final LanguageProvider INSTANCE = new JavaScriptProvider();
    }

    private JavaScriptProvider() {}

    @Override
    public void initialize(@NotNull Map<String, String> config) {
        super.initialize(config);

        issuesAnalyzer = new ESLintAnalyzer(config.get("eslintPath"), resultsPath, config.get("ruleSetPath"));
        metricsAnalyzer = new CK4JSAnalyzer(config.get("ck4jsPath"), resultsPath);
        issuesImporter = new ESLintResultsImporter();
        metricsImporter = new CK4JSResultsImporter();
        issuesAggregator = new ESLintAggregator();
        metricsAggregator = new CK4JSAggregator();
    }

    @Override
    public String getLanguage() {
        return "js";
    }
}
