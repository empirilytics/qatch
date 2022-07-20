package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class PythonProvider extends LanguageProvider {

    private PythonProvider() {
    }

    private static final class ProviderHolder {
        private static final LanguageProvider INSTANCE = new PythonProvider();
    }

    public static LanguageProvider instance() {
        return ProviderHolder.INSTANCE;
    }

    @Override
    public void initialize(@NotNull String configPath, @NotNull String resultsPath) {
        this.configPath = configPath;
        this.resultsPath = resultsPath;
        loadConfig();

        issuesAnalyzer = new PylintAnalyzer(config.get("prospectorPath"), resultsPath, config.get("ruleSetPath"));
        metricsAnalyzer = new CKPMAnalyzer(config.get("ckpmPath"), resultsPath);
        issuesImporter = new PylintResultsImporter();
        metricsImporter = new CKPMResultsImporter();
        issuesAggregator = new PylintAggregator();
        metricsAggregator = new CKPMAggregator();
    }

    @Override
    public String getLanguage() {
        return "python";
    }
}
