package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

/**
 * 测试 TaskList 类的功能.
 */
public class TaskListTest {
    @Test
    void testTaskListSerialization() {
        TaskList.Task task1 = new TaskList.Task(true, "Complete homework");
        TaskList.Task task2 = new TaskList.Task(false, "Buy groceries");

        TaskList taskList = new TaskList(Arrays.asList(task1, task2));
        String expected = "- [x] Complete homework\n- [ ] Buy groceries";
        assertEquals(expected, taskList.toMarkdown());
    }

    @Test
    void testTaskListEmpty() {
        TaskList taskList = new TaskList(Arrays.asList());
        String expected = "";
        assertEquals(expected, taskList.toMarkdown());
    }

    @Test
    void testTaskListWithCompletedTasks() {
        TaskList.Task task1 = new TaskList.Task(true, "Write code");
        TaskList.Task task2 = new TaskList.Task(true, "Review pull request");

        TaskList taskList = new TaskList(Arrays.asList(task1, task2));
        String expected = "- [x] Write code\n- [x] Review pull request";
        assertEquals(expected, taskList.toMarkdown());
    }

    @Test
    void testTaskListWithIncompleteTasks() {
        TaskList.Task task1 = new TaskList.Task(false, "Prepare for meeting");
        TaskList.Task task2 = new TaskList.Task(false, "Call the plumber");

        TaskList taskList = new TaskList(Arrays.asList(task1, task2));
        String expected = "- [ ] Prepare for meeting\n- [ ] Call the plumber";
        assertEquals(expected, taskList.toMarkdown());
    }
}
