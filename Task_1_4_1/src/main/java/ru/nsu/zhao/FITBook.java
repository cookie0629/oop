package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FITBook {
    private final List<List<FITRecord>> records; // 所有学期的记录
    private FIT examGrade; // 考试成绩
    private boolean examFlag; // 考试完成标志
    private final boolean budget; // 是否公费

    public FITBook(boolean budget) {
        this.records = new ArrayList<>();
        this.examGrade = null;
        this.examFlag = false;
        this.budget = budget;
    }

    public void addGrades(List<FITRecord> grades) {
        records.add(grades);
    }

    public void setExamGrade(FIT grade) {
        this.examGrade = grade;
        this.examFlag = true;
    }

    public double calculateAvg() {
        return dynamicAvg(calculateSum(), getSubjectCount());
    }

    public boolean canGetScholarship() {
        if (!budget || records.isEmpty()) {
            return false;
        }

        List<FITRecord> latest = getLatest();
        for (FITRecord rec : latest) {
            if (rec.getFIT() != FIT.EXCELLENT) {
                return false;
            }
        }
        return true;
    }

    // 私有辅助方法
    private int calculateSum() {
        return getLastGrades().values().stream()
                .mapToInt(FIT::getValue)
                .sum();
    }

    private int getSubjectCount() {
        return getLastGrades().size();
    }

    private List<FITRecord> getLatest() {
        return records.getLast();
    }

    private double dynamicAvg(int total, int count) {
        return count == 0 ? 0 : total / (double) count;
    }

    private Map<String, FIT> getLastGrades() {
        return records.stream()
                .flatMap(List::stream)
                .collect(Collectors.toMap(
                        FITRecord::getSubjectName,
                        FITRecord::getFIT,
                        (oldVal, newVal) -> newVal
                ));
    }
}
