package com.empirilytics.qatch.projectimport;

public record Submission(String id,
                         String name,
                         int score,
                         String lang,
                         String source,
                         String role,
                         String rejectionReason,
                         String stage,
                         boolean goodCode) {
}
