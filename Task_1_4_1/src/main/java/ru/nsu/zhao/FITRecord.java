package ru.nsu.zhao;

/**
 * 学生成绩记录类，记录某学期某门课程的成绩。
 */
public class FITRecord {
    private final String subjectName; // 课程名称
    private final FIT fit; // 课程成绩
    private final boolean isDifferentiated; // 是否为区分性评价

    /**
     * 构造一个 FITRecord 对象。
     *
     * @param subjectName 课程名称
     * @param fit 课程的成绩
     * @param isDifferentiated 是否为区分性评价
     */
    public FITRecord(String subjectName, FIT fit, boolean isDifferentiated) {
        this.subjectName = subjectName;
        this.fit = fit;
        this.isDifferentiated = isDifferentiated;
    }

    /**
     * 获取课程的成绩。
     *
     * @return 课程成绩
     */
    public FIT getFIT() {
        return fit;
    }

    /**
     * 检查课程是否采用区分性评价。
     *
     * @return 如果是区分性评价返回 true，否则返回 false
     */
    public boolean isDifferentiated() {
        return isDifferentiated;
    }

    /**
     * 获取课程的名称。
     *
     * @return 课程名称
     */
    public String getSubjectName() {
        return subjectName;
    }
}

