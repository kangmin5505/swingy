package me.kangmin.view;

import javax.swing.*;
import java.awt.*;

public class ConsoleGameView extends JFrame implements GameView {
    private final int TEXT_FIELD_HEIGHT = GameView.HEIGHT / 20;
    private final int TEXT_FIELD_HEIGHT_LOCATION = GameView.HEIGHT * 9 / 10;
    @Override
    public void showWindow() {
        setTitle("Console Game View");
        setSize(GameView.WIDTH, GameView.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT_LOCATION);
        mainPanel.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT_LOCATION));
        mainPanel.setLocation(0, 0);
        mainPanel.setBackground(Color.BLUE);

        JTextArea leftPanel = new JTextArea();
        leftPanel.setEditable(false);
        leftPanel.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH - 20, TEXT_FIELD_HEIGHT_LOCATION));
        leftPanel.setSize(new Dimension(GameView.MAIN_PANEL_WIDTH - 20, TEXT_FIELD_HEIGHT_LOCATION));
        leftPanel.setLocation(20, 0);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setForeground(Color.GREEN);
        leftPanel.setFont(new Font("Arial", Font.PLAIN, 20));

        mainPanel.add(leftPanel);
        add(mainPanel);

        JPanel textPanel = new JPanel();

        textPanel.setSize(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT);
        textPanel.setLocation(0,TEXT_FIELD_HEIGHT_LOCATION);
        textPanel.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT));
        textPanel.setBackground(Color.YELLOW);

        JTextField textField = new JTextField();
        textField.setSize(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT);
        textField.setPreferredSize(new Dimension(GameView.MAIN_PANEL_WIDTH, TEXT_FIELD_HEIGHT));
        textField.setLocation(0,TEXT_FIELD_HEIGHT_LOCATION);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setEditable(true);
        textField.setVisible(true);

        textPanel.add(textField);
        add(textPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setSize(GameView.SUB_PANEL_WIDTH, GameView.HEIGHT);
        rightPanel.setLocation(GameView.MAIN_PANEL_WIDTH, 0);
        rightPanel.setBackground(Color.WHITE);
        add(rightPanel);

        setVisible(true);
    }
}
