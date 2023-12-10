package me.kangmin.swingy.view;

import me.kangmin.swingy.enums.Move;
import me.kangmin.swingy.enums.StudyType;

import java.util.List;
import java.util.stream.IntStream;

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
