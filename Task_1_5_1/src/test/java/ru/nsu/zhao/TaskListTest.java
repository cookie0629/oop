package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    @Test
    void testTaskListSerialization() {
        TaskList taskList = new TaskList(List.of(
                new TaskList.Task(false, "Task 1"),
                new TaskList.Task(true, "Task 2")
        ));
        String expected = """
                - [ ] Task 1
                - [x] Task 2
                """;
        assertEquals(expected.trim(), taskList.toMarkdown());
    }
}
