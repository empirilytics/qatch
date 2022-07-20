package com.empirilytics.qatch.service.compilers;

import com.empirilytics.qatch.service.data.Project;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
public interface ExecCompiler {

  void selectAndExecuteCompiler(Project proj);
}
