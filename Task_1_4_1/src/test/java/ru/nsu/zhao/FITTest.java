package ru.nsu.zhao;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FITTest {
    @Test
    void testCanGetScholarshipFailsWithNonExcellentFIT() {
        // 创建测试记录（包含非 EXCELLENT）
        FITRecord record1 = FITRecord.createRecord("Math", FIT.EXCELLENT, true);
        FITRecord record2 = FITRecord.createRecord("Physics", FIT.GOOD, true);

        // 创建 FITbook 实例并设置为公费生
        FITbook fitbook = new FITbook(true);
        fitbook.addGrades(List.of(record1, record2));

        // 验证不符合奖学金条件
        assertFalse(fitbook.canGetScholarship(), "存在非 EXCELLENT 成绩，不应符合奖学金条件");
    }
}
