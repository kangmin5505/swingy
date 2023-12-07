package me.kangmin.swingy.dto;

import me.kangmin.swingy.enums.SubjectType;

public class SubjectDto {
    private final SubjectType subjectType;
    private final boolean isSuccess;

    public SubjectDto(SubjectType subjectType, boolean isSuccess) {
        this.subjectType = subjectType;
        this.isSuccess = isSuccess;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
