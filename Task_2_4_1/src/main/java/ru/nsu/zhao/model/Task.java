package ru.nsu.zhao.model;

/**
 * 任务实体类 / Task entity
 */
public class Task {
    private String id;            // 任务ID / Task ID
    private String title;         // 任务标题 / Task title
    private int maxScore;         // 最高得分 / Maximum score
    private String softDeadline;  // 软截止日期 / Soft deadline
    private String hardDeadline;  // 硬截止日期 / Hard deadline

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public String getSoftDeadline() {
        return softDeadline;
    }

    public void setSoftDeadline(String softDeadline) {
        this.softDeadline = softDeadline;
    }

    public String getHardDeadline() {
        return hardDeadline;
    }

    public void setHardDeadline(String hardDeadline) {
        this.hardDeadline = hardDeadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", maxScore=" + maxScore +
                ", softDeadline='" + softDeadline + '\'' +
                ", hardDeadline='" + hardDeadline + '\'' +
                '}';
    }
}
