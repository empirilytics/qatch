package com.empirilytics.qatch.service.data;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author Isaac Griffith
 * @version 2.0.0
 */
@ToString(onlyExplicitlyIncluded = true)
public class Content {
  @ToString.Include
  @Getter
  String submission_id;
  @ToString.Include
  @Getter
  String project;
  @ToString.Include
  @Getter
  String language;
  @Getter
  List<String> dependencies;
  @Getter
  List<Map<String, String>> files;

  public Content() {

  }

  public static void main(String[] args) {
    Gson gson = new Gson();
    Content c = gson.fromJson("""
            {
              "submission_id": "Small_Single_File",
              "project": "small_single_file",
              "language": "java",
              "dependencies": [],
              "files": [
                {
                  "name": "test/App.java",
                  "contents": "package test;\npublic class App {\n}"
                }
              ]
            }""", Content.class);
    System.out.println(c);

    DbManager.instance().loadCredentials();
    DbManager.instance().open();
    Project proj = Project.builder()
            .id(c.submission_id)
            .name(c.getProject())
            .path(c.getProject())
            .resultsPath(c.getProject())
            .language(c.getLanguage())
            .build();
    DbManager.instance().close();

    System.out.println(proj);
  }
}
