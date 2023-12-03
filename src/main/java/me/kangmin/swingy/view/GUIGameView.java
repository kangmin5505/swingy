package me.kangmin.swingy.view;

import javax.swing.*;
import java.awt.*;

public class GUIGameView extends JFrame implements GameView {
    @Override
    public void run() {
        setTitle("Console Game View");
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(GameView.MAIN_PANEL_WIDTH, GameView.HEIGHT);
        mainPanel.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH, GameView.HEIGHT));
        mainPanel.setLocation(0, 0);
        mainPanel.setBackground(Color.BLUE);

        JTextArea leftPanel = new JTextArea();
        leftPanel.setEditable(false);
        leftPanel.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH - 20, GameView.HEIGHT));
        leftPanel.setSize(new Dimension(GameView.MAIN_PANEL_WIDTH - 20, GameView.HEIGHT));
        leftPanel.setLocation(20, 0);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setForeground(Color.GREEN);
        leftPanel.setFont(new Font("Arial", Font.PLAIN, 20));

        mainPanel.add(leftPanel);
        add(mainPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setSize(GameView.SUB_PANEL_WIDTH, GameView.HEIGHT);
        rightPanel.setLocation(GameView.MAIN_PANEL_WIDTH, 0);
        rightPanel.setBackground(Color.WHITE);
        add(rightPanel);

        setVisible(true);
    }
}
