package me.kangmin.swingy.enums.menu;

public enum SettingMenu implements Menu<SettingMenu> {
    CHANGE_VIEW_MODE("화면 모드 변경"),
    RESET_DATA("데이터 초기화"),
    BACK("뒤로 가기");
    ;

    private final String description;

    SettingMenu(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public SettingMenu[] getValues() {
        return SettingMenu.values();
    }
}
