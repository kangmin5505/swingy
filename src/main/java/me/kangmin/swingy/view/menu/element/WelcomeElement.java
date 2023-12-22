package me.kangmin.swingy.view.menu.element;

import me.kangmin.swingy.enums.Step;

public enum WelcomeElement implements MenuElement {
    NEW_GAME("새로운 게임 시작", Step.CREATE_NEW_PLAYER),
    LOAD_GAME("이전 게임 불러오기", Step.LOAD_GAME),
    SETTING("설정", Step.SETTING),
    EXIT("게임 종료", Step.EXIT);

    private final String description;
    private final Step step;

    WelcomeElement(String description, Step step) {
        this.description = description;
        this.step = step;
    }
    @Override
    public String getDescription() {
        return this.description;
    }

    public Step getPage() {
        return step;
    }
}
