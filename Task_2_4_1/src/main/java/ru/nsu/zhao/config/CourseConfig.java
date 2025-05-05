package ru.nsu.zhao.config;

import ru.nsu.zhao.model.*;
import groovy.lang.Closure;

import java.util.*;

/**
 * 课程配置类 / Course configuration class
 */
public class CourseConfig {
    private final List<Task> tasks = new ArrayList<>();          // 任务列表 / Task list
    private final List<Group> groups = new ArrayList<>();         // 小组列表 / Group list
    private final List<Assignment> assignments = new ArrayList<>(); // 作业分配 / Assignments
    private final List<Milestone> milestones = new ArrayList<>().reversed(); // 里程碑 / Milestones
    private final Map<String, Object> settings = new HashMap<>(); // 系统设置 / System settings

    // 配置任务DSL方法 / DSL method for tasks configuration
    public void tasks(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void task(String id, Closure<?> inner) {
                Task task = new Task();
                task.setId(id);
                inner.setDelegate(task);
                inner.setResolveStrategy(Closure.DELEGATE_ONLY);
                inner.call();
                tasks.add(task);
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    // 配置小组DSL方法 / DSL method for groups configuration
    public void groups(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void group(String name, Closure<?> inner) {
                Group group = new Group();
                group.setName(name);

                inner.setDelegate(new Object() {
                    public void student(Closure<?> studentClosure) {
                        Student student = new Student();
                        studentClosure.setDelegate(student);
                        studentClosure.setResolveStrategy(Closure.DELEGATE_ONLY);
                        studentClosure.call();
                        group.getStudents().add(student);
                    }
                });

                inner.setResolveStrategy(Closure.DELEGATE_ONLY);
                inner.call();
                groups.add(group);
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    // 配置作业分配DSL方法 / DSL method for assignments configuration
    public void assignments(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void assign(String taskId, String github) {
                assignments.add(new Assignment(taskId, github));
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    // 配置里程碑DSL方法 / DSL method for milestones configuration
    public void milestones(Closure<?> closure) {
        closure.setDelegate(new Object() {
            public void milestone(String name, String date) {
                milestones.add(new Milestone(name, date));
            }
        });
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    // 配置系统设置DSL方法 / DSL method for system settings
    public void settings(Closure<?> closure) {
        closure.setDelegate(settings);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
    }

    // 获取学生特定任务的加分 / Get bonus points for specific student task
    public double getBonusPoints(String github, String taskId) {
        Object raw = settings.get("bonusPoints");

        if (!(raw instanceof Map)) return 0.0;

        Map<String, Object> bonusMap = (Map<String, Object>) raw;
        Object userBonus = bonusMap.get(github);
        if (!(userBonus instanceof Map)) return 0.0;

        Map<String, Object> userTaskBonuses = (Map<String, Object>) userBonus;
        Object bonusValue = userTaskBonuses.get(taskId);
        if (bonusValue instanceof Number) {
            return ((Number) bonusValue).doubleValue();
        }

        return 0.0;
    }

    // Getter方法 / Getters
    public List<Task> getTasks() { return tasks; }
    public List<Group> getGroups() { return groups; }
    public List<Assignment> getAssignments() { return assignments; }
    public List<Milestone> getMilestones() { return milestones; }
    public Map<String, Object> getSettings() { return settings; }
}
