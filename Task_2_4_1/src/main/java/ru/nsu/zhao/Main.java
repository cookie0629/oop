package ru.nsu.zhao;

import ru.nsu.zhao.config.CourseConfig;
import ru.nsu.zhao.model.Task;
import ru.nsu.zhao.model.Group;
import ru.nsu.zhao.model.Student;
import ru.nsu.zhao.model.Assignment;
import ru.nsu.zhao.report.HtmlReportDataBuilder;
import ru.nsu.zhao.report.HtmlReportGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化配置
        CourseConfig config = new CourseConfig();

        // 2. 直接使用Java方式添加任务
        Task task1 = new Task();
        task1.setId("task1");
        task1.setTitle("Task 1");
        task1.setMaxScore(10);
        task1.setSoftDeadline("2023-10-01");
        task1.setHardDeadline("2023-10-15");
        config.getTasks().add(task1);

        // 3. 添加小组和学生
        Group group1 = new Group();
        group1.setName("Group1");

        Student student1 = new Student();
        student1.setGithub("student1");
        student1.setFullName("John Doe");
        student1.setRepo("https://github.com/student1/repo");
        group1.getStudents().add(student1);

        config.getGroups().add(group1);

        // 4. 添加作业分配
        config.getAssignments().add(new Assignment("task1", "student1"));

        // 5. 模拟测试数据
        List<String> compiledTasks = List.of("student1/task1");
        List<String> docTasks = List.of("student1/task1");
        List<String> styleTasks = List.of("student1/task1");
        Map<String, List<Integer>> testResults = Map.of(
                "student1/task1", List.of(5, 1, 0) // passed, failed, skipped
        );

        // 6. 构建报告数据
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                config, compiledTasks, docTasks, styleTasks, testResults
        );
        Map<String, Map<String, List<String>>> detailedData = builder.buildDetailedReportData();
        Map<String, List<String>> summaryData = builder.buildGroupSummaryData(detailedData);

        // 7. 生成HTML报告
        HtmlReportGenerator.generateReport(
                "report.html",
                detailedData,
                summaryData
        );

        System.out.println("报告生成完成！");
    }
}
