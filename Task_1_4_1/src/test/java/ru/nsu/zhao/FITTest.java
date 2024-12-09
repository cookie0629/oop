package ru.nsu.zhao;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FITBookTest {

    // 测试计算平均成绩的功能
    @Test
    void testAverageGrade() {
        FITBook fitBook = new FITBook(false);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.SATISFACTORY, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.GOOD, false),
                new FITRecord("面向对象编程", FIT.EXCELLENT, false),
                new FITRecord("概率论", FIT.EXCELLENT, false)
        );
        fitBook.addGrades(sem2);

        // 断言计算出的平均成绩是否正确
        Assertions.assertEquals(4.75, fitBook.calculateAverage());
    }

    // 测试学生是否可以转为公费
    @Test
    void testTransferToBudget() {
        FITBook fitBook = new FITBook(false);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.GOOD, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.GOOD, false),
                new FITRecord("面向对象编程", FIT.EXCELLENT, false),
                new FITRecord("概率论", FIT.EXCELLENT, false)
        );
        fitBook.addGrades(sem2);

        // 断言学生是否可以转为公费
        Assertions.assertTrue(fitBook.canBeTransferredToBudget());
    }

    // 测试不符合条件的学生不能转为公费
    @Test
    void testNoTransferToBudget() {
        FITBook fitBook = new FITBook(false);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.SATISFACTORY, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.GOOD, false),
                new FITRecord("面向对象编程", FIT.GOOD, false),
                new FITRecord("概率论", FIT.EXCELLENT, false)
        );
        fitBook.addGrades(sem2);

        // 断言学生是否不能转为公费
        Assertions.assertFalse(fitBook.canBeTransferredToBudget());
    }

    // 测试未达到要求的学生不能获得红色文凭
    @Test
    void testNoQualificationNoDiploma() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.EXCELLENT, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.EXCELLENT, false),
                new FITRecord("面向对象编程", FIT.GOOD, false),
                new FITRecord("概率论", FIT.EXCELLENT, false)
        );
        fitBook.addGrades(sem2);

        // 断言学生不能获得红色文凭
        Assertions.assertFalse(fitBook.canGetRedDiploma());
    }

    // 测试虽然考试成绩很好，但其他成绩不合格导致无法获得红色文凭
    @Test
    void testNoLuckNoDiploma() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.EXCELLENT, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.EXCELLENT, false),
                new FITRecord("面向对象编程", FIT.SATISFACTORY, false),
                new FITRecord("概率论", FIT.EXCELLENT, false)
        );
        fitBook.addGrades(sem2);
        fitBook.setExamScore(FIT.EXCELLENT);

        // 断言学生不能获得红色文凭
        Assertions.assertFalse(fitBook.canGetRedDiploma());
    }

    // 测试学生无法获得奖学金的条件
    @Test
    void testNoBrainNoMoney() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.UNSATISFACTORY, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 断言学生不能获得奖学金
        Assertions.assertFalse(fitBook.canGetIncreasedScholarship());
    }

    // 测试学生符合奖学金条件
    @Test
    void testNoBrainMuchLuck() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("微分方程", FIT.EXCELLENT, false),
                new FITRecord("操作系统导论", FIT.EXCELLENT, true),
                new FITRecord("面向对象编程", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem1);

        // 断言学生可以获得奖学金
        Assertions.assertTrue(fitBook.canGetIncreasedScholarship());
    }

    // 测试未来有机会获得红色文凭的情况
    @Test
    void testCanGetRedDiplomaFuture() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("操作研究", FIT.EXCELLENT, true),
                new FITRecord("开发PACS", FIT.GOOD, true)
        );
        fitBook.addGrades(sem1);

        // 创建并添加第二个学期的成绩记录
        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.EXCELLENT, true),
                new FITRecord("Prolog", FIT.EXCELLENT, true)
        );
        fitBook.addGrades(sem2);
        fitBook.setExamScore(FIT.EXCELLENT);

        // 断言学生可以获得红色文凭
        Assertions.assertTrue(fitBook.canGetRedDiploma());
    }

    // 测试由于有不及格成绩，学生无法获得红色文凭
    @Test
    void testCannotGetRedDiplomaDueToSatisfactory() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加第一个学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("体育", FIT.EXCELLENT, true),
                new FITRecord("俄罗斯历史", FIT.SATISFACTORY, true)
        );
        fitBook.addGrades(sem1);
        fitBook.setExamScore(FIT.EXCELLENT);

        // 断言学生无法获得红色文凭
        Assertions.assertFalse(fitBook.canGetRedDiploma());
    }

    // 测试由于平均成绩不合格，学生无法获得文凭
    @Test
    void testCannotGetDiplomaDueToAverageGrade() {
        FITBook fitBook = new FITBook(true);

        // 创建并添加多学期的成绩记录
        List<FITRecord> sem1 = List.of(
                new FITRecord("操作研究", FIT.GOOD, true),
                new FITRecord("开发PACS", FIT.GOOD, true)
        );
        fitBook.addGrades(sem1);

        List<FITRecord> sem2 = List.of(
                new FITRecord("操作系统导论", FIT.GOOD, true),
                new FITRecord("Prolog", FIT.GOOD, true)
        );
        fitBook.addGrades(sem2);

        List<FITRecord> sem3 = List.of(
                new FITRecord("Haskell", FIT.GOOD, true),
                new FITRecord("体育", FIT.GOOD, true)
        );
        fitBook.addGrades(sem3);

        List<FITRecord> sem4 = List.of(
                new FITRecord("英语", FIT.GOOD, true),
                new FITRecord("TFCSP", FIT.GOOD, true)
        );
        fitBook.addGrades(sem4);

        List<FITRecord> sem5 = List.of(
                new FITRecord("操作研究", FIT.GOOD, true),
                new FITRecord("开发PACS", FIT.GOOD, true)
        );
        fitBook.addGrades(sem5);

        List<FITRecord> sem6 = List.of(
                new FITRecord("命令式编程", FIT.GOOD, true),
                new FITRecord("俄罗斯历史", FIT.GOOD, true)
        );
        fitBook.addGrades(sem6);
        fitBook.setExamScore(FIT.EXCELLENT);

        // 断言学生不能获得红色文凭
        Assertions.assertFalse(fitBook.canGetRedDiploma());
    }
}
