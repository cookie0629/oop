package ru.nsu.zhao;

public class FITRecord {
    private final String subj; // 课程名称
    private final FIT x1; // 成绩
    private final boolean flag; // 区分性评价标志

    // 私有构造函数，只允许通过工厂方法创建对象
    private FITRecord(String subj, FIT x1, boolean flag) {
        this.subj = subj;
        this.x1 = x1;
        this.flag = flag;
    }

    // 工厂方法
    public static FITRecord createRecord(String subj, FIT x1, boolean flag) {
        return new FITRecord(subj, x1, flag);
    }

    // 成绩获取逻辑经过封装
    public FIT getFIT() {
        return processGrade(x1);
    }

    // 判断是否区分性评价
    public boolean isDifferentiated() {
        return flag;
    }

    // 获取课程名称
    public String getSubjectName() {
        return subj;
    }

    // 模拟复杂的成绩处理逻辑
    private FIT processGrade(FIT grade) {
        return FIT.values()[(grade.getValue() - 2) % 4];
    }
}
