package ru.nsu.zhao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 小组实体类 / Group entity
 */
public class Group {
    private String name;                    // 小组名称 / Group name
    private List<Student> students = new ArrayList<>();  // 学生列表 / Student list

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
