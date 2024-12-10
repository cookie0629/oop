package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TaskTest 单元测试
 */
class TaskTest {

    @Test
    void testTaskToMarkdown() {
        Task taskList = new Task()
                .addTask("Complete homework", true)
                .addTask("Buy groceries", false);

        String expectedMarkdown = "- [x] Complete homework\n- [ ] Buy groceries\n";
        assertEquals(expectedMarkdown, taskList.toMarkdown());
    }

    @Test
    void testTaskEquality() {
        Task taskList1 = new Task()
                .addTask("Complete homework", true)
                .addTask("Buy groceries", false);

        Task taskList2 = new Task()
                .addTask("Complete homework", true)
                .addTask("Buy groceries", false);

        assertEquals(taskList1, taskList2);
    }
}
