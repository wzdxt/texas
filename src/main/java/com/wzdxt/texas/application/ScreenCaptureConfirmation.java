package com.wzdxt.texas.application;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.util.RobotUtil;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-13.
 */
public class ScreenCaptureConfirmation {
    private JFrame frame;
    private JPanel panel;
    private JButton confirmButton;
    private JLabel imageLabel;

    public ScreenCaptureConfirmation(@NonNull ScreenParam sp) {
        init();
        showScreenCapture(sp);
    }

    public ScreenCaptureConfirmation() {
        init();
        showScreenCapture();
    }

    private void init() {
        frame = new JFrame();
        frame.add(panel);
        frame.setTitle("Minesweeper");
        bindEvent();
    }

    private void repaint() {
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        EventQueue.invokeLater(() -> frame.setLocationRelativeTo(null));
    }

    private void bindEvent() {
        confirmButton.addActionListener(e -> frame.dispose());
    }

    private void showScreenCapture(ScreenParam sp) {
        BufferedImage bi = RobotUtil.screenCapture(sp.getGameX1(), sp.getGameY1(), sp.getGameX2(), sp.getGameY2());
        imageLabel.setText("");
        imageLabel.setIcon(new ImageIcon(bi));
        repaint();
    }

    private void showScreenCapture() {
        BufferedImage bi = RobotUtil.screenCapture(100, 100, 700, 500);
        imageLabel.setText("");
        imageLabel.setIcon(new ImageIcon(bi));
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(ScreenCaptureConfirmation::new);
    }
}
