package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 表示 Markdown 任务列表。
 */
public class Task extends Element {
    private final List<TaskItem> tasks = new ArrayList<>();

    /**
     * 添加任务。
     *
     * @param content 任务内容
     * @param done    是否已完成
     * @return 当前 Task 实例（链式调用）
     */
    public Task addTask(String content, boolean done) {
        tasks.add(new TaskItem(content, done));
        return this;
    }

    @Override
    public String toMarkdown() {
        StringBuilder markdown = new StringBuilder();
        for (TaskItem task : tasks) {
            markdown.append(task.done ? "- [x] " : "- [ ] ").append(task.content).append("\n");
        }
        return markdown.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;
        Task task = (Task) obj;
        return Objects.equals(tasks, task.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tasks);
    }

    /**
     * 表示单个任务项。
     */
    private static class TaskItem {
        private final String content;
        private final boolean done;

        TaskItem(String content, boolean done) {
            this.content = content;
            this.done = done;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof TaskItem)) return false;
            TaskItem item = (TaskItem) obj;
            return done == item.done && Objects.equals(content, item.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(content, done);
        }
    }
}
