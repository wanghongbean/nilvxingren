package com.nlxr.juc.concurrent.bo;

import java.util.Objects;

public class StudentBo {
    private int aScore;
    private int BScore;
    private String name;

    public StudentBo() {
    }

    public StudentBo(int aScore, int BScore, String name) {
        this.aScore = aScore;
        this.BScore = BScore;
        this.name = name;
    }

    public int getAScore() {
        return aScore;
    }

    public void setAScore(int aScore) {
        this.aScore = aScore;
    }

    public int getBScore() {
        return BScore;
    }

    public void setBScore(int BScore) {
        this.BScore = BScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentBo studentBo = (StudentBo) o;
        return aScore == studentBo.aScore &&
                BScore == studentBo.BScore &&
                Objects.equals(name, studentBo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aScore, BScore, name);
    }
}
