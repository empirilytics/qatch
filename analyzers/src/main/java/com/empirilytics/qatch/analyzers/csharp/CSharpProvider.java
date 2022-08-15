package com.empirilytics.qatch.analyzers.csharp;

import com.empirilytics.qatch.analyzers.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CSharpProvider extends LanguageProvider {

  public static LanguageProvider instance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {
    private static final LanguageProvider INSTANCE = new CSharpProvider();
  }

  private CSharpProvider() {}

  /** {@inheritDoc} */
  @Override
  public void initialize(@NotNull Map<String, String> config) {
    super.initialize(config);
  }

  /** {@inheritDoc} */
  @Override
  public String getLanguage() {
    return "csharp";
  }
}
