package com.empirilytics.qatch.service.lang.providers;

import com.empirilytics.qatch.service.ServerContext;
import com.empirilytics.qatch.service.lang.builder.JavaBuilder;
import com.empirilytics.qatch.service.data.Project;
import com.empirilytics.qatch.service.lang.files.JavaFileBuilder;
import com.empirilytics.qatch.service.lang.processors.JavaCodeProcessor;
import lombok.NonNull;

/**
 * Language provider for the Java language
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class JavaProvider extends AbstractLanguageProvider {

  /** Constructs a new JavaProvider */
  public JavaProvider() {
    super();
  }

  /** {@inheritDoc} */
  public void initComponents(@NonNull Project project) {
    ServerContext context = ServerContext.instance();
    codeProcessor = new JavaCodeProcessor(this, project.name());
    projectBuilder =
        new JavaBuilder(
            this,
            project.path(),
            context.getConfig().getMvnHome(),
            context.getConfig().getGradleHome());
    fileBuilder = new JavaFileBuilder(this);
  }
}
