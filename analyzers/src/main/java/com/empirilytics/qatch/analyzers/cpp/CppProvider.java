package com.empirilytics.qatch.analyzers.cpp;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class CppProvider extends LanguageProvider {

    public static LanguageProvider instance() {
        return InstanceHolder.INSTANCE;
    }

    private static class InstanceHolder {
        private static final LanguageProvider INSTANCE = new CppProvider();
    }

    private CppProvider() {}

    @Override
    public void initialize(@NotNull String configPath, @NotNull String resultsPath) {

    }

    @Override
    public String getLanguage() {
        return "c++";
    }
}
