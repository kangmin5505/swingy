package me.kangmin.swingy.view.menu.element;

import me.kangmin.swingy.enums.Step;

public enum SettingElement implements MenuElement {
    CHANGE_VIEW_MODE("화면 모드 변경", Step.CHANGE_VIEW_MODE),
    RESET_DATA("데이터 초기화", Step.RESET_DATA),
    BACK("뒤로 가기", Step.WELCOME);
    ;
    private final String description;
    private final Step step;

    SettingElement(String description, Step step) {
        this.description = description;
        this.step = step;
    }

    @Override
    public String getDescription() {
        return this.description;
    }



    public Step getPage() {
        return this.step;
    }

}
