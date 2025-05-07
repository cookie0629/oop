package ru.nsu.zhao.model;

import java.util.Objects;

/**
 * 学生作业任务分配实体 / Assignment entity for student tasks
 */
public class Assignment {
    private final String taskId;  // 任务ID / Task ID
    private final String github;  // GitHub用户名 / GitHub username

    public Assignment(String taskId, String github) {
        this.taskId = taskId;
        this.github = github;
    }

    public String getTaskId() { return taskId; }
    public String getGithub() { return github; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(github, that.github);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "taskId='" + taskId + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
