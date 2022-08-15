package com.empirilytics.qatch.analyzers.python;

import com.empirilytics.qatch.core.eval.Issue;
import com.empirilytics.qatch.core.eval.IssueSet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class IssueDeserializer extends StdDeserializer<IssueSet> {

  private String ruleSetName;

  public IssueDeserializer(String ruleSetName) {
    this(ruleSetName, null);
  }

  public IssueDeserializer(String ruleSetName, Class<?> vc) {
    super(vc);
    this.ruleSetName = ruleSetName;
  }

  @Override
  public IssueSet deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    IssueSet issues = new IssueSet();
    ObjectCodec codec = parser.getCodec();
    JsonNode root = codec.readTree(parser);

    // json format:
    //        [{
    //            "type": "convention"|"refactor"|"warning"|"error"|"fatal"
    //            "module": "module string",
    //            "obj": "obj string",
    //            "line": <num>,
    //            "column": <num>,
    //            "endLine": <num>,
    //            "endColumn": <num>,
    //            "path": "path string",
    //            "symbol": "symbol string",
    //            "message": "message string",
    //            "message-id": "CXXXX"
    //        }]

    root.elements()
        .forEachRemaining(
            node -> {
              JsonNode typeNode = node.get("type"); // Map to priority
              JsonNode moduleNode = node.get("module"); // package name
              JsonNode objNode = node.get("obj"); // package name
              JsonNode lineNode = node.get("line"); // beginLine
              JsonNode colNode = node.get("column"); // beginCol
              JsonNode endLineNode = node.get("endLine"); // endLine
              JsonNode endColNode = node.get("endColumn"); // endCol
              JsonNode pathNode = node.get("path");
              JsonNode symbolNode = node.get("symbol"); // rulename
              JsonNode msgNode = node.get("message"); // description
              JsonNode msgIdNode = node.get("message-id");

              Issue issue =
                  new Issue(
                      msgIdNode.asText(),
                      ruleSetName,
                      fullyQualified(moduleNode.asText(), objNode.asText()),
                      msgNode.asText(),
                      "",
                      mapPriority(typeNode.asText()),
                      lineNode.asInt(),
                      endLineNode.asInt(),
                      colNode.asInt(),
                      endColNode.asInt(),
                      "");
              issues.addIssue(issue);
            });

    return null;
  }

  private String fullyQualified(String module, String obj) {
    return "";
  }

  private int mapPriority(String type) {
    return switch(type) {
      case "convention" -> 1;
      case "refactor" -> 2;
      case "warning" -> 3;
      case "error" -> 4;
      case "fatal" -> 5;
      default -> 0;
    };
  }
}
