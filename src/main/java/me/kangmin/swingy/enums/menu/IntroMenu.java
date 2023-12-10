package me.kangmin.swingy.enums.menu;

public enum IntroMenu implements Menu<IntroMenu> {
    NEW_GAME("새로운 게임 시작"),
    LOAD_GAME("이전 게임 불러오기"),
    SETTINGS("설정"),
    EXIT("게임 종료");

    private final String description;

    IntroMenu(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public IntroMenu[] getValues() {
        return IntroMenu.values();
    }
}
