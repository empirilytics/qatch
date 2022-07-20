package com.empirilytics.qatch.analyzers;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Base class for LanguageProviders. A LanguageProvicer is a frontend class which provides the
 * access to the analyzers, aggregators, and importes for the tools associated with a language.
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
public abstract class LanguageProvider {

  /** The configuration path for the language provider */
  protected String configPath;
  /** The results path for the language provider */
  protected String resultsPath;
  /** The language specific configuration information */
  protected Map<String, String> config = Maps.newHashMap();

  /** The issues aggregator */
  @Getter protected Aggregator issuesAggregator;
  /** The issues analyzer */
  @Getter protected IssuesAnalyzer issuesAnalyzer;
  /** The issues importer */
  @Getter protected IssuesImporter issuesImporter;

  /** The metrics aggregator */
  @Getter protected Aggregator metricsAggregator;
  /** The metrics analyzer */
  @Getter protected MetricsAnalyzer metricsAnalyzer;
  /** The metrics importer */
  @Getter protected MetricsImporter metricsImporter;

  /** Constructs a new Language Provider */
  protected LanguageProvider() {}

  /**
   * Initializes this LanguageProvider with the given configPath and resultsPath
   *
   * @param configPath The configuration path for the language, cannot be null or empty
   * @param resultsPath The results path for the langauge, cannot be null or empty
   */
  public abstract void initialize(@NonNull String configPath, @NonNull String resultsPath);

  /** Returns the language moniker associated with this language provider */
  public abstract String getLanguage();

  /** Loads the language specific config for this provider */
  public void loadConfig() {
    Yaml yaml = new Yaml();
    try (InputStream inputStream =
        Files.newInputStream(Paths.get(configPath, "qatch." + getLanguage() + ".yml"))) {
      config = yaml.load(inputStream);
      String qHome = System.getenv("QATCH_HOME");
      config.forEach((key, val) -> {
        if (config.get(key).contains("$QATCH_HOME")) {
          config.put(key, config.get(key).replace("$QATCH_HOME", qHome));
        }
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
