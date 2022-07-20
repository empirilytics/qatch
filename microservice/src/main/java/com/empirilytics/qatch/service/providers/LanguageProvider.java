package com.empirilytics.qatch.service.providers;

import com.empirilytics.qatch.service.compilers.ExecCompiler;
import com.empirilytics.qatch.service.data.Project;
import com.empirilytics.qatch.service.io.FileBuilder;
import com.empirilytics.qatch.service.processors.CodeProcessor;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public interface LanguageProvider {

  void initComponents(Project project);

  ExecCompiler getCompiler();

  FileBuilder getFileBuilder();

  CodeProcessor getCodeProcessor();
}
