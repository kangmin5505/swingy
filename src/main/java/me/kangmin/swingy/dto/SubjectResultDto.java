package me.kangmin.swingy.dto;

import me.kangmin.swingy.enums.SubjectType;

public class SubjectResultDto {
    private final boolean isSuccess;
    private final SubjectType subjectType;

    public SubjectResultDto(boolean isSuccess, SubjectType subjectType) {
        this.isSuccess = isSuccess;
        this.subjectType = subjectType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }
}
