package ru.nsu.zhao;

public class FITRecord {
    private final String subj;
    private final FIT score; // 成绩
    private final boolean flag; // 区分性评价标志

    // 私有构造函数，只允许通过工厂方法创建对象
    FITRecord(String subj, FIT score, boolean flag) {
        this.subj = subj;
        this.score = score;
        this.flag = flag;
    }

    // 获取科目的分数
    public FIT getScore() {
        return score;
    }

    // 判断是否区分性评价
    public boolean isDifferentiated() {
        return flag;
    }

    // 获取课程名称
    public String getSubjectName() {
        return subj;
    }

}
