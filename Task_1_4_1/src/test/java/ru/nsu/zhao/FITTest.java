package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FITTest {

    @Test
    void testGetValue() {
        // 验证每个枚举实例的值
        assertEquals(5, FIT.EXCELLENT.getValue(), "EXCELLENT 应返回 5");
        assertEquals(4, FIT.GOOD.getValue(), "GOOD 应返回 4");
        assertEquals(3, FIT.SATISFACTORY.getValue(), "SATISFACTORY 应返回 3");
        assertEquals(2, FIT.UNSATISFACTORY.getValue(), "UNSATISFACTORY 应返回 2");
    }

    @Test
    void testEnumNames() {
        // 验证枚举实例的名称是否正确
        assertEquals("EXCELLENT", FIT.EXCELLENT.name(), "枚举名称应为 EXCELLENT");
        assertEquals("GOOD", FIT.GOOD.name(), "枚举名称应为 GOOD");
        assertEquals("SATISFACTORY", FIT.SATISFACTORY.name(), "枚举名称应为 SATISFACTORY");
        assertEquals("UNSATISFACTORY", FIT.UNSATISFACTORY.name(), "枚举名称应为 UNSATISFACTORY");
    }

    @Test
    void testEnumOrder() {
        // 验证枚举实例的顺序
        FIT[] fits = FIT.values();
        assertArrayEquals(
                new FIT[]{FIT.EXCELLENT, FIT.GOOD, FIT.SATISFACTORY, FIT.UNSATISFACTORY},
                fits,
                "枚举顺序应按声明的顺序排列"
        );
    }
}
