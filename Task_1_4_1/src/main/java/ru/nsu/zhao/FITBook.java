package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 学生成绩记录簿类，包含学期成绩、考试成绩以及是否为公费等信息。
 */
public class FITBook {
    private final List<List<FITRecord>> semesters; // 所有学期的成绩记录
    private FIT examScore; // 最终考试成绩
    private boolean examFlag; // 是否完成最终考试的标志
    private final boolean budget; // 是否为公费学生

    /**
     * 构造函数，初始化成绩簿。
     *
     * @param budget 是否为公费学生
     */
    public FITBook(boolean budget) {
        this.semesters = new ArrayList<>();
        this.examScore = null;
        this.examFlag = false;
        this.budget = budget;
    }

    /**
     * 添加某学期的成绩记录。
     *
     * @param semesterScores 学期成绩记录的列表
     */
    public void addGrades(List<FITRecord> semesterScores) {
        semesters.add(semesterScores);
    }

    /**
     * 设置最终考试成绩。
     *
     * @param score 考试成绩
     */
    public void setExamScore(FIT score) {
        this.examScore = score;
        this.examFlag = true;
    }

    /**
     * 计算所有学科的平均分数（使用每个科目的最新成绩）。
     *
     * @return 平均分数，如果没有成绩则返回 0
     */
    public double calculateAverage() {
        int total = 0; // 总分数
        int count = 0; // 总科目数量
        Map<String, FIT> latest = getLastGradeForEachSubject(); // 获取每个科目的最新成绩
        for (FIT score : latest.values()) {
            total += score.getValue();
            ++count;
        }
        return count == 0 ? 0 : (double) total / count; // 计算平均分数
    }

    /**
     * 获取每个学科的最新成绩。
     *
     * @return 每个学科的最新成绩的映射
     */
    private Map<String, FIT> getLastGradeForEachSubject() {
        return semesters.stream() // 创建学期的流
                .flatMap(List::stream) // 将所有学期的记录展平为单个流
                .collect(Collectors.toMap( // 收集流中的元素到 Map
                        FITRecord::getSubjectName, // 键为科目名称
                        FITRecord::getScore, // 值为对应的成绩
                        (existing, replacement) -> replacement // 如果有重复科目，保留最后一次的成绩
                ));
    }

    /**
     * 判断学生是否可以从自费转为公费。
     *
     * @return 如果可以转为公费，返回 true；否则返回 false
     */
    public boolean canBeTransferredToBudget() {
        if (budget || semesters.size() < 2) { // 如果已经是公费或者学期数不足两学期
            return false;
        }
        // 检查最近两个学期是否有不及格科目
        for (int i = semesters.size() - 2; i < semesters.size(); ++i) {
            for (FITRecord record : semesters.get(i)) {
                if (record.isDifferentiated() && record.getScore() == FIT.SATISFACTORY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断学生是否有资格获得红色文凭（优秀毕业生）。
     *
     * @return 如果有资格，返回 true；否则返回 false
     */
    public boolean canGetRedDiploma() {
        if (!examFlag || examScore != FIT.EXCELLENT) { // 必须完成考试且成绩为优秀
            return false;
        }

        Map<String, FIT> latest = getLastGradeForEachSubject(); // 获取每个科目的最新成绩
        long totalSubjects = latest.size(); // 总科目数

        // 检查是否存在不及格的科目
        boolean hasSatisfactoryGrade = latest.values().stream()
                .anyMatch(grade -> grade == FIT.SATISFACTORY);
        if (hasSatisfactoryGrade) {
            return false;
        }

        // 统计优秀成绩的科目数量
        long excellentMarks = latest.values().stream()
                .filter(grade -> grade == FIT.EXCELLENT)
                .count();

        // 计算总分数和平均分数
        int totalMarks = latest.values().stream()
                .mapToInt(FIT::getValue)
                .sum();
        double averageGrade = (double) totalMarks / totalSubjects;

        // 判断优秀科目比例是否大于等于 75%，且平均分是否大于等于 4.5
        return (double) excellentMarks / totalSubjects >= 0.75 && averageGrade >= 4.5;
    }

    /**
     * 判断学生是否可以获得提高的奖学金。
     *
     * @return 如果有资格，返回 true；否则返回 false
     */
    public boolean canGetIncreasedScholarship() {
        if (!budget || semesters.isEmpty()) { // 必须是公费生，且必须有成绩记录
            return false;
        }

        // 获取最近一个学期的成绩
        List<FITRecord> lastSemester = semesters.get(semesters.size() - 1);
        // 检查最近学期是否所有科目都是优秀
        for (FITRecord record : lastSemester) {
            if (record.getScore() != FIT.EXCELLENT) {
                return false;
            }
        }
        return true;
    }
}
