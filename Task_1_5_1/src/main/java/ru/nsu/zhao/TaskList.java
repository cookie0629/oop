package ru.nsu.zhao;

import java.util.List;

/**
 * 表示 Markdown 任务列表.
 */
public class TaskList implements Element {
    private final List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (Task task : tasks) {
            markdown.append("- [").append(task.isCompleted ? "x" : " ").append("] ")
                    .append(task.description).append("\n");
        }
        return markdown.toString().trim();
    }

    /**
     * 表示任务列表中的单个任务.
     */
    public static class Task {
        private final boolean isCompleted;
        private final String description;

        public Task(boolean isCompleted, String description) {
            this.isCompleted = isCompleted;
            this.description = description;
        }
    }
}
