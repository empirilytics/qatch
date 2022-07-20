package com.empirilytics.qatch.service.processors

import com.empirilytics.qatch.service.providers.LanguageProvider
import groovy.transform.CompileStatic
import groovy.transform.NullCheck

import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@CompileStatic
@NullCheck
class JavaCodeProcessor implements CodeProcessor {

    final LanguageProvider provider
    String projectName

    JavaCodeProcessor(LanguageProvider provider, String projectName) {
        this.provider = provider
        this.projectName = projectName
    }

    String processSingleFileProject(String pathString, String content) {
        Path path = Paths.get(pathString)
        String className = path.getFileName().toString().replace(".java", "")

        content = """\
                  package ${getPackageOrDefault(path)};
                  
                  ${getStandardImports()}
                  
                  public class ${className} {
                  ${content.indent(2)};
                  }
                  """

        return content
    }

    String getStandardImports() {
        String[] lines = JavaCodeProcessor.class.getResourceAsStream("/standard_imports/java.txt").readLines();
        return lines.join("\n");
    }

    String getPackageOrDefault(Path path) {
        if (path.isEmpty())
            return projectName
        else
            return path.getParent().toString().replace("/", ".")
    }
}
