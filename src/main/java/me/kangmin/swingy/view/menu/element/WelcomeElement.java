package me.kangmin.swingy.view.menu.element;

import me.kangmin.swingy.enums.Page;

public enum WelcomeElement implements MenuElement {
    NEW_GAME("새로운 게임 시작", Page.CREATE_NEW_PLAYER),
    LOAD_GAME("이전 게임 불러오기", Page.LOAD_GAME),
    SETTING("설정", Page.SETTING),
    EXIT("게임 종료", Page.EXIT);

    private final String description;
    private final Page page;

    WelcomeElement(String description, Page page) {
        this.description = description;
        this.page = page;
    }
    @Override
    public String getDescription() {
        return this.description;
    }

    public Page getPage() {
        return page;
    }
}
