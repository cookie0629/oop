package ru.nsu.zhao.model;

/**
 * 学生实体类 / Student entity
 */
public class Student {
    private String github;    // GitHub用户名 / GitHub username
    private String fullName; // 学生姓名 / Student full name
    private String repo;      // 代码库链接 / Repository link

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "github='" + github + '\'' +
                ", fullName='" + fullName + '\'' +
                ", repo='" + repo + '\'' +
                '}';
    }
}
