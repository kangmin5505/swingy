package me.kangmin.swingy.dto;

import me.kangmin.swingy.enums.SubjectType;

public class SubjectResultDto {
    private final boolean isSuccess;
    private final SubjectType subjectType;
    private final int stage;

    public SubjectResultDto(boolean isSuccess, SubjectType subjectType, int stage) {
        this.isSuccess = isSuccess;
        this.subjectType = subjectType;
        this.stage = stage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public int getStage() {
        return stage;
    }
}
