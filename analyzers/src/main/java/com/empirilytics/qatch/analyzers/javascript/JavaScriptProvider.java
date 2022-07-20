package com.empirilytics.qatch.analyzers.javascript;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class JavaScriptProvider extends LanguageProvider {

    public static LanguageProvider instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final LanguageProvider INSTANCE = new JavaScriptProvider();
    }

    private JavaScriptProvider() {}

    @Override
    public void initialize(@NotNull String configPath, @NotNull String resultsPath) {

    }

    @Override
    public String getLanguage() {
        return "js";
    }
}
