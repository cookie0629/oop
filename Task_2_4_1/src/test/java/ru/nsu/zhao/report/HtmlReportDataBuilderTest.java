package ru.nsu.zhao.report;

import org.junit.jupiter.api.Test;
import ru.nsu.zhao.config.CourseConfig;
import ru.nsu.zhao.model.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HtmlReportDataBuilderTest {

    @Test
    void testBuildDetailedReportData() {
        // Setup test data
        CourseConfig config = createTestConfig();

        List<String> compiledTasks = List.of("student1/task1", "student2/task1");
        List<String> docTasks = List.of("student1/task1");
        List<String> styleTasks = List.of("student2/task1");
        Map<String, List<Integer>> testResults = Map.of(
                "student1/task1", List.of(5, 1, 0),
                "student2/task1", List.of(3, 2, 1)
        );

        // Create builder
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                config, compiledTasks, docTasks, styleTasks, testResults
        );

        // Execute
        Map<String, Map<String, List<String>>> result = builder.buildDetailedReportData();

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size()); // Only one group in test data

        Map<String, List<String>> groupReport = result.get("Group1");
        assertNotNull(groupReport);
        assertEquals(1, groupReport.size()); // Only one task in test data

        List<String> taskRows = groupReport.get("task1 (Task 1)");
        assertNotNull(taskRows);
        assertEquals(2, taskRows.size()); // Two students

        // Verify first student's data
        String[] student1Data = taskRows.getFirst().split("\t");
        assertEquals("John Doe", student1Data[0]);
        assertEquals("+", student1Data[1]); // compiled
        assertEquals("+", student1Data[2]); // documented
        assertEquals("-", student1Data[3]); // styled
        assertEquals("5/1/0", student1Data[4]); // tests
        assertEquals("0", student1Data[5]); // bonus
        assertEquals("1", student1Data[6]); // total score

        // Verify second student's data
        String[] student2Data = taskRows.get(1).split("\t");
        assertEquals("Jane Smith", student2Data[0]);
        assertEquals("+", student2Data[1]); // compiled
        assertEquals("-", student2Data[2]); // documented
        assertEquals("+", student2Data[3]); // styled
        assertEquals("3/2/1", student2Data[4]); // tests
        assertEquals("0", student2Data[5]); // bonus
        assertEquals("1", student2Data[6]); // total score
    }

    @Test
    void testBuildGroupSummaryData() {
        // Setup test data
        CourseConfig config = createTestConfig();

        List<String> compiledTasks = List.of("student1/task1", "student2/task1");
        List<String> docTasks = List.of("student1/task1");
        List<String> styleTasks = List.of("student2/task1");
        Map<String, List<Integer>> testResults = Map.of(
                "student1/task1", List.of(5, 1, 0),
                "student2/task1", List.of(3, 2, 1)
        );

        // Create builder and detailed data
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                config, compiledTasks, docTasks, styleTasks, testResults
        );
        Map<String, Map<String, List<String>>> detailedData = builder.buildDetailedReportData();

        // Execute
        Map<String, List<String>> result = builder.buildGroupSummaryData(detailedData);

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size()); // Only one group

        List<String> summaryRows = result.get("Group1");
        assertNotNull(summaryRows);
        assertEquals(3, summaryRows.size()); // Header + 2 students

        // Verify header
        String[] header = summaryRows.getFirst().split("\t");
        assertEquals("Студент", header[0]);
        assertEquals("task1", header[1]);
        assertEquals("Сумма", header[2]);
        assertEquals("Оценка", header[3]);

        // Verify first student summary
        String[] student1Summary = summaryRows.get(1).split("\t");
        assertEquals("John Doe", student1Summary[0]);
        assertEquals("1", student1Summary[1]); // task1 score
        assertEquals("1", student1Summary[2]); // total
        assertEquals("неуд.", student1Summary[3]); // grade

        // Verify second student summary
        String[] student2Summary = summaryRows.get(2).split("\t");
        assertEquals("Jane Smith", student2Summary[0]);
        assertEquals("1", student2Summary[1]); // task1 score
        assertEquals("1", student2Summary[2]); // total
        assertEquals("неуд.", student2Summary[3]); // grade
    }

    @Test
    void testDetermineGrade() {
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                new CourseConfig(), List.of(), List.of(), List.of(), Map.of()
        );

        assertEquals("отлично", builder.determineGrade(5.0));
        assertEquals("отлично", builder.determineGrade(6.0));
        assertEquals("хорошо", builder.determineGrade(4.0));
        assertEquals("хорошо", builder.determineGrade(4.9));
        assertEquals("удовл.", builder.determineGrade(3.0));
        assertEquals("удовл.", builder.determineGrade(3.9));
        assertEquals("неуд.", builder.determineGrade(2.9));
    }

    @Test
    void testFormatScore() {
        HtmlReportDataBuilder builder = new HtmlReportDataBuilder(
                new CourseConfig(), List.of(), List.of(), List.of(), Map.of()
        );

        assertEquals("5", builder.formatScore(5.0));
        assertEquals("5", builder.formatScore(5.0));
        assertEquals("4.5", builder.formatScore(4.5));
        assertEquals("3.1", builder.formatScore(3.1));
    }

    private CourseConfig createTestConfig() {
        CourseConfig config = new CourseConfig();

        // Add task
        Task task = new Task();
        task.setId("task1");
        task.setTitle("Task 1");
        task.setMaxScore(10);
        config.getTasks().add(task);

        // Add group with students
        Group group = new Group();
        group.setName("Group1");

        Student student1 = new Student();
        student1.setGithub("student1");
        student1.setFullName("John Doe");
        group.getStudents().add(student1);

        Student student2 = new Student();
        student2.setGithub("student2");
        student2.setFullName("Jane Smith");
        group.getStudents().add(student2);

        config.getGroups().add(group);

        // Add assignments
        config.getAssignments().add(new Assignment("task1", "student1"));
        config.getAssignments().add(new Assignment("task1", "student2"));

        return config;
    }
}
