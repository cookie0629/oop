package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学生成绩单类，包含学生可能需要的所有信息。
 */
public class FITbook {
    private final List<List<FITRecord>> semesters; // 存储每个学期的成绩记录
    private FIT qualificationFIT; // 资格考试的成绩
    private boolean qualificationCompleted; // 是否完成了资格考试
    private final boolean isOnBudget; // 学生是否为公费生

    /**
     * 构造一个成绩单，初始化公费状态。
     *
     * @param isOnBudget 学生是否为公费生
     */
    public FITbook(boolean isOnBudget) {
        this.semesters = new ArrayList<>();
        this.qualificationFIT = null;
        this.qualificationCompleted = false;
        this.isOnBudget = isOnBudget;
    }

    /**
     * 为成绩单添加一个学期的成绩。
     *
     * @param semesterGrades 学期的成绩记录列表
     */
    public void addSemesterGrades(List<FITRecord> semesterGrades) {
        semesters.add(semesterGrades);
    }

    /**
     * 设置学生的资格考试成绩。
     *
     * @param fit 资格考试成绩
     */
    public void setQualificationFIT(FIT fit) {
        this.qualificationFIT = fit;
        this.qualificationCompleted = true;
    }

    /**
     * 计算每门课程的最后一次成绩的平均值。
     *
     * @return 平均成绩，如果没有成绩则返回 0
     */
    public double calculateAverage() {
        int total = 0; // 成绩总和
        int count = 0; // 成绩数量
        Map<String, FIT> latest = getLastFITForEachSubject();
        for (FIT fit : latest.values()) {
            total += fit.getValue();
            ++count;
        }
        return count == 0 ? 0 : (double) total / count;
    }

    /**
     * 获取所有学期每门课程的最后一次成绩。
     *
     * @return 每门课程的最新成绩映射
     */
    private Map<String, FIT> getLastFITForEachSubject() {
        return semesters.stream() // 创建学期流
                .flatMap(List::stream) // 将所有学期的成绩记录合并到一个流
                .collect(Collectors.toMap( // 收集到 Map 中
                        FITRecord::getSubjectName, // 键：课程名
                        FITRecord::getFIT, // 值：课程的成绩
                        (existing, replacement) -> replacement // 如果有重复记录，保留最新记录
                ));
    }

    /**
     * 判断学生是否可以转为公费生。
     *
     * @return 如果可以转为公费生返回 true，否则返回 false
     */
    public boolean canBeTransferredToBudget() {
        if (isOnBudget || semesters.size() < 2) { // 如果已经是公费生或者学期不足两学期
            return false;
        }
        for (int i = semesters.size() - 2; i < semesters.size(); ++i) { // 检查最近两学期的成绩
            for (FITRecord record : semesters.get(i)) {
                if (record.isDifferentiated() && record.getFIT() == FIT.SATISFACTORY) { // 如果存在不及格的成绩
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断学生将来是否有资格获得红色文凭。
     *
     * @return 如果有资格返回 true，否则返回 false
     */
    public boolean canGetRedDiploma() {
        if (!qualificationCompleted || qualificationFIT != FIT.EXCELLENT) { // 资格考试必须完成且成绩为优秀
            return false;
        }

        Map<String, FIT> latest = getLastFITForEachSubject();
        long totalSubjects = latest.size(); // 总课程数

        boolean hasSatisfactoryFIT = latest.values().stream()
                .anyMatch(fit -> fit == FIT.SATISFACTORY); // 是否存在不及格的成绩
        if (hasSatisfactoryFIT) {
            return false;
        }

        long excellentMarks = latest.values().stream()
                .filter(fit -> fit == FIT.EXCELLENT) // 统计优秀成绩的数量
                .count();

        int totalMarks = latest.values().stream()
                .mapToInt(FIT::getValue) // 计算总分数
                .sum();

        double averageFIT = (double) totalMarks / totalSubjects; // 平均成绩

        return (double) excellentMarks / totalSubjects >= 0.75 && averageFIT >= 4.5; // 判断是否满足红色文凭的条件
    }

    /**
     * 判断学生是否有资格获得提高奖学金。
     *
     * @return 如果满足所有条件返回 true，否则返回 false
     */
    public boolean canGetIncreasedScholarship() {
        if (!isOnBudget || semesters.isEmpty()) { // 必须是公费生且至少有一个学期的成绩
            return false;
        }

        List<FITRecord> lastSemester = semesters.get(semesters.size() - 1); // 获取最近一个学期的成绩
        for (FITRecord record : lastSemester) {
            if (record.getFIT() != FIT.EXCELLENT) { // 如果有非优秀成绩
                return false;
            }
        }
        return true; // 满足所有条件
    }
}
