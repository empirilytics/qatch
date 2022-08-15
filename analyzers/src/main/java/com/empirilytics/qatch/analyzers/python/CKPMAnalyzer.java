package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.AbstractMetricsAnalyzer;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.exec.CommandLine;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.Iterator;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class CKPMAnalyzer extends AbstractMetricsAnalyzer {

    public static final String TOOL_NAME = "CKPM";

    public CKPMAnalyzer(String ckpmPath, String resultsPath) {
        super(ckpmPath, resultsPath);
    }

    /** {@inheritDoc} */
    @Override
    protected CommandLine constructCommandLine(@NotNull String src, @NotNull String dest) {
        return new CommandLine(Paths.get(toolPath, "ck4py").toAbsolutePath().normalize().toString())
                .addArgument("-d")
                .addArgument(src)
                .addArgument("-j")
                .addArgument("-n")
                .addArgument("-o")
                .addArgument(dest);
    }

    /** {@inheritDoc} */
    @Override
    protected String getToolName() {
        return TOOL_NAME;
    }
}
