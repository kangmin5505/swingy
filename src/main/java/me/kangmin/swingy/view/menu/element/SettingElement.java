package me.kangmin.swingy.view.menu.element;

import me.kangmin.swingy.enums.Page;

public enum SettingElement implements MenuElement {
    CHANGE_VIEW_MODE("화면 모드 변경", Page.CHANGE_VIEW_MODE),
    RESET_DATA("데이터 초기화", Page.RESET_DATA),
    BACK("뒤로 가기", Page.WELCOME);
    ;
    private final String description;
    private final Page page;

    SettingElement(String description, Page page) {
        this.description = description;
        this.page = page;
    }

    @Override
    public String getDescription() {
        return this.description;
    }



    public Page getPage() {
        return this.page;
    }

}
