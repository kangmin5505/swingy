package me.kangmin.swingy.view.menu.element;

public enum SaveGameElement implements MenuElement {
    SAVE("저장 후 진행"),
    EXIT("저장 후 게임 종료")
    ;

    private final String description;

    SaveGameElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
