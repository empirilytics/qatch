package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.analyzers.MetricsImporter;
import com.empirilytics.qatch.core.eval.MethodLevelAttributes;
import com.empirilytics.qatch.core.eval.MetricSet;
import com.empirilytics.qatch.core.eval.Metrics;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class CKPMResultsImporter implements MetricsImporter {

  public MetricSet parseMetrics(@NotNull String path) {
    log.info("Parsing Metrics Results!");

    MetricSet metricSet = new MetricSet();
    ObjectMapper mapper = new ObjectMapper();
    try {
      Map<String, Object> result = mapper.readValue(Paths.get(path).toAbsolutePath().normalize().toFile(), HashMap.class);

      result.get("name");
      List<Map<String, Object>> modules = (List<Map<String, Object>>) result.get("modules");
      modules.forEach(mod -> {
        Metrics modMetrics = processMetrics(mod);
        modMetrics.setMethods(processMethods(mod));
        metricSet.addMetrics(modMetrics);
        double loc = modMetrics.get("loc");
        List<Map<String, Object>> classes = (List<Map<String, Object>>) mod.get("classes");
        classes.forEach(cls -> {
          Metrics clsMetrics = processMetrics(cls);
          List<MethodLevelAttributes> attrs = processMethods(cls);
          clsMetrics.setMethods(attrs);
          metricSet.addMetrics(clsMetrics);
        });
      });
    } catch (Exception ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
    }

    return metricSet;
  }

  public List<MethodLevelAttributes> processMethods(Map<String, Object> node) {
    List<MethodLevelAttributes> methods = Lists.newArrayList();
    List<Map<String, Object>> list = (List<Map<String, Object>>) node.get("cc");
    if (list != null) {
      list.forEach(meth -> {
        MethodLevelAttributes method = new MethodLevelAttributes(
                (String) meth.get("method_name"),
                (Double) meth.get("cc"),0
        );
        methods.add(method);
      });
    }
    return methods;
  }

  public Metrics processMetrics(Map<String, Object> node) {
    Metrics metrics = new Metrics((String) node.get("name"));
    metrics.set("loc", (Double) node.get("loc"));
    metrics.set("sloc", (Double) node.get("sloc"));
    metrics.set("lloc", (Double) node.get("lloc"));
    metrics.set("bloc", (Double) node.get("bloc"));
    metrics.set("mcloc", (Double) node.get("mcloc"));
    metrics.set("scloc", (Double) node.get("scloc"));
    metrics.set("cp", (Double) node.get("cp"));
    metrics.set("wmc", (Double) node.get("wmc"));
    metrics.set("cbo", (Double) node.get("cbo"));
    metrics.set("lcom", (Double) node.get("lcom"));
    metrics.set("npm", (Double) node.get("npm"));
    metrics.set("dam", (Double) node.get("dam"));
    //metrics.set("amc", (Double) node.get("amc"));
    return metrics;
  }

  public String getFileName() {
    return "ckpm_results.json";
  }

  public static void main(String args[]) {
    CKPMResultsImporter imp = new CKPMResultsImporter();
    imp.parseMetrics("/home/idg/bin/Qatch/qatch/Results/ckpm_results.json");
  }
}
