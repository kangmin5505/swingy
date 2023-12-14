package me.kangmin.swingy.view;

public interface GameView {
    int WIDTH = 1280;
    double RATIO = (double) 9 / 16;
    int HEIGHT =  (int) (WIDTH * RATIO);
    int MAIN_PANEL_WIDTH = WIDTH * 3 / 4;
    int SUB_PANEL_WIDTH = WIDTH - MAIN_PANEL_WIDTH;

    void run();

    default String getIntro() {
        StringBuffer sb = new StringBuffer();
        sb.append("'Knights of 42: The Code Crusade'는 다양한 분야에서 온 플레이어가 프로그래밍의 세계를 모험하며 진정한 개발자로 성장하는 여정을 그립니다.\n");
        sb.append("각자의 능력치를 발전시키고 도전적인 프로그래밍 과제를 해결함으로써, 진정한 개발자로 거듭나게 됩니다.\n");
        sb.append("지금 바로 이 모험에 참여하여 42의 진정한 개발자로 거듭나보세요!\n");
        return sb.toString();
    }

    default String getEnding() {
        StringBuffer sb = new StringBuffer();
        sb.append("축하합니다! 'Knights of 42: The Code Crusade'의 모든 프로그래밍 과제를 완료하셨습니다.\n");
        sb.append("여러분은 진정한 개발자로 성장하였고, 이제는 프로그래밍의 세계에서 탁월한 역할을 수행할 수 있습니다.\n");
        sb.append("여러분의 모험을 응원하며, 이제 여러분이 만든 코드를 더 큰 세상에서 빛낼 수 있기를 기대합니다!\n");
        sb.append("게임을 즐겨주셔서 감사합니다. 새로운 모험에서 만나요!\n");
        return sb.toString();
    }

    default String getBanner() {
        StringBuffer sb = new StringBuffer();

        sb.append("\n\n");
        sb.append("██╗  ██╗███╗   ██╗██╗ ██████╗ ██╗  ██╗████████╗     ██████╗ ███████╗    ██╗  ██╗██████╗\n");
        sb.append("██║ ██╔╝████╗  ██║██║██╔════╝ ██║  ██║╚══██╔══╝    ██╔═══██╗██╔════╝    ██║  ██║╚════██╗\n");
        sb.append("█████╔╝ ██╔██╗ ██║██║██║  ███╗███████║   ██║       ██║   ██║█████╗      ███████║ █████╔╝\n");
        sb.append("██╔═██╗ ██║╚██╗██║██║██║   ██║██╔══██║   ██║       ██║   ██║██╔══╝      ╚════██║██╔═══╝ \n");
        sb.append("██║  ██╗██║ ╚████║██║╚██████╔╝██║  ██║   ██║       ╚██████╔╝██║              ██║███████╗\n");
        sb.append("╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝ ╚═════╝ ╚═╝  ╚═╝   ╚═╝        ╚═════╝ ╚═╝              ╚═╝╚══════╝\n");
        sb.append("    ████████╗██╗  ██╗███████╗     ██████╗ ██████╗ ██████╗ ███████╗     ██████╗██████╗ ██╗   ██╗███████╗ █████╗ ██████╗ ███████╗\n");
        sb.append("██╗ ╚══██╔══╝██║  ██║██╔════╝    ██╔════╝██╔═══██╗██╔══██╗██╔════╝    ██╔════╝██╔══██╗██║   ██║██╔════╝██╔══██╗██╔══██╗██╔════╝\n");
        sb.append("╚═╝    ██║   ███████║█████╗      ██║     ██║   ██║██║  ██║█████╗      ██║     ██████╔╝██║   ██║███████╗███████║██║  ██║█████╗\n");
        sb.append("██╗    ██║   ██╔══██║██╔══╝      ██║     ██║   ██║██║  ██║██╔══╝      ██║     ██╔══██╗██║   ██║╚════██║██╔══██║██║  ██║██╔══╝\n");
        sb.append("╚═╝    ██║   ██║  ██║███████╗    ╚██████╗╚██████╔╝██████╔╝███████╗    ╚██████╗██║  ██║╚██████╔╝███████║██║  ██║██████╔╝███████╗\n");
        sb.append("       ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝     ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═════╝ ╚══════╝\n");
        return sb.toString();
    }

}
