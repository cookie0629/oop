package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 FIT 枚举类的功能。
 */
class FITTest {

    /**
     * 测试 getValue() 方法是否正确返回成绩数值。
     */
    @Test
    void testGetValue() {
        assertEquals(5, FIT.EXCELLENT.getValue());
        assertEquals(4, FIT.GOOD.getValue());
        assertEquals(3, FIT.SATISFACTORY.getValue());
        assertEquals(2, FIT.UNSATISFACTORY.getValue());
    }

    /**
     * 测试枚举值的数量和顺序是否正确。
     */
    @Test
    void testEnumValues() {
        FIT[] values = FIT.values();
        assertEquals(4, values.length);
        assertEquals(FIT.EXCELLENT, values[0]);
        assertEquals(FIT.GOOD, values[1]);
        assertEquals(FIT.SATISFACTORY, values[2]);
        assertEquals(FIT.UNSATISFACTORY, values[3]);
    }

    /**
     * 测试枚举值的名称是否正确。
     */
    @Test
    void testEnumNames() {
        assertEquals("EXCELLENT", FIT.EXCELLENT.name());
        assertEquals("GOOD", FIT.GOOD.name());
        assertEquals("SATISFACTORY", FIT.SATISFACTORY.name());
        assertEquals("UNSATISFACTORY", FIT.UNSATISFACTORY.name());
    }
}
