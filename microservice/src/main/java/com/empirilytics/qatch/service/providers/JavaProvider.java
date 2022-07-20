package com.empirilytics.qatch.service.providers;

import com.empirilytics.qatch.service.ServerContext;
import com.empirilytics.qatch.service.compilers.ExecCompiler;
import com.empirilytics.qatch.service.compilers.ExecJavaCompiler;
import com.empirilytics.qatch.service.data.Project;
import com.empirilytics.qatch.service.io.FileBuilder;
import com.empirilytics.qatch.service.io.JavaFileBuilder;
import com.empirilytics.qatch.service.processors.CodeProcessor;
import com.empirilytics.qatch.service.processors.JavaCodeProcessor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public class JavaProvider implements LanguageProvider {

  @Getter private FileBuilder fileBuilder;
  @Getter private ExecCompiler compiler;
  @Getter private CodeProcessor codeProcessor;

  public JavaProvider() {}

  public void initComponents(@NonNull Project project) {
    ServerContext context = ServerContext.instance();
    codeProcessor = new JavaCodeProcessor(this, project.name());
    compiler = new ExecJavaCompiler(this, project.path(), context.getConfig().getMvnHome(), context.getConfig().getGradleHome());
    fileBuilder = new JavaFileBuilder(this);
  }
}
