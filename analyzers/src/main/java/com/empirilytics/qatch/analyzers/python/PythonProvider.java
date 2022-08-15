package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
    public void initialize(@NotNull Map<String, String> config) {
        super.initialize(config);

        issuesAnalyzer = new PylintAnalyzer(config.get("pylintPath"), resultsPath, config.get("ruleSetPath"));
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
