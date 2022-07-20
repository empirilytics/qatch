package com.empirilytics.qatch.service.io;

import com.empirilytics.qatch.service.compilers.ExecCompiler;
import com.empirilytics.qatch.service.compilers.GradleProjectGenerator;
import com.empirilytics.qatch.service.data.DbManager;
import com.empirilytics.qatch.service.data.Project;
import com.empirilytics.qatch.service.processors.CodeProcessor;
import com.empirilytics.qatch.service.providers.LanguageProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Slf4j
public class JavaFileBuilder extends FileBuilder {

  LanguageProvider provider;

  public JavaFileBuilder(LanguageProvider provider) {
    this.provider = provider;
  }

  /**
   * Reconstructs a given file with the given name and content
   *
   * @param fileName The name of the file to be reconstructed
   * @param content The content of the file to be reconstructed
   * @throws IllegalArgumentException if the file name empty is null
   */
  public void createFile(
      @NonNull Project project, @NonNull String fileName, @NonNull String content)
      throws IllegalArgumentException {
    // if (fileName.isEmpty()) throw new IllegalArgumentException("file name cannot be null or
    // empty");

    DbManager.instance().open();
    String basePath = project.path();
    DbManager.instance().close();
    Path path = Paths.get(basePath, "src/main/java", fileName);

    try {
      Files.deleteIfExists(path);
      Path parent = path.getParent();
      Files.createDirectories(parent);
      Files.createFile(path);

      CodeProcessor processor = provider.getCodeProcessor();
      String fileContent = processor.processSingleFileProject(fileName, content);

      Files.write(path, List.of(fileContent.split("\n")));
    } catch (Exception ex) {
      log.error("Could not write file: " + path.toAbsolutePath());
    }
  }

  /**
   * Sets up and constructs the files associated with the given project
   *
   * @param proj Project for which the files are to be created, cannot be null
   * @param files List of maps defining the files, where each map contains two keys "name" and
   *     "content", which combined fully specify the file to be created, cannot be null
   * @param dependencies an array of strings representing the dependencies of the project, cannot be
   *     null
   */
  public void createFiles(
      @NonNull Project proj,
      @NonNull List<Map<String, String>> files,
      @NonNull String[] dependencies) {

    DbManager.instance().open();
    if (proj.language().equalsIgnoreCase("java")) {
      setupJavaBuild(proj, files, dependencies);
    }
    DbManager.instance().close();

    for (Map<String, String> pair : files) {
      String name = pair.get("name");
      String contents = pair.get("contents");

      createFile(proj, name, contents);
    }

    buildJavaProject(proj);
  }

  private void buildJavaProject(@NotNull Project proj) {
    ExecCompiler comp = provider.getCompiler();
    comp.selectAndExecuteCompiler(proj);
  }

  void setupJavaBuild(Project proj, List<Map<String, String>> files, String[] dependencies) {
    log.info("Creating Java Build Components");
    //    Optional<Map<String, String>> opt =
    //        files.stream()
    //            .findFirst()
    //            .filter(
    //                map ->
    //                    map.get("name").equalsIgnoreCase("build.gradle")
    //                        || map.get("name").equalsIgnoreCase("pom.xml"));
    //    if (opt.isEmpty()) {
    GradleProjectGenerator gen = new GradleProjectGenerator();
    gen.setupDirectoryStructure(proj.path(), proj, dependencies);
    //    }
  }
}
