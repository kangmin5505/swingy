package me.kangmin.swingy.dto;

import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.enums.SubjectType;

import java.util.Optional;

public class SubjectDto {
    private final SubjectType subjectType;
    private final boolean isSuccess;
    private final int stage;
    private final Artifact artifact;

    public SubjectDto(SubjectResultDto subjectResultDto, Artifact artifact) {
        this.subjectType = subjectResultDto.getSubjectType();
        this.isSuccess = subjectResultDto.isSuccess();
        this.stage = subjectResultDto.getStageLevel();
        this.artifact = artifact;
    }
    public SubjectType getSubjectType() {
        return subjectType;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getStage() {
        return stage;
    }

    public Optional<Artifact> getArtifact() {
        return Optional.ofNullable(artifact);
    }
}
